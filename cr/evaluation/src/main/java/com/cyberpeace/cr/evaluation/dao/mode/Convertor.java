package com.cyberpeace.cr.evaluation.dao.mode;

import com.cyberpeace.cr.evaluation.dao.mode.probe.Hop;
import com.cyberpeace.cr.evaluation.dao.mode.probe.NetworkProbeData;
import com.cyberpeace.cr.evaluation.dao.mode.probe.data.ReportData;
import com.cyberpeace.cr.evaluation.dao.mode.topo.Element;
import com.cyberpeace.cr.evaluation.dao.mode.topo.Port;
import com.cyberpeace.cr.evaluation.dao.mode.topo.Topology;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.net.util.SubnetUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@Data
public class Convertor {
    private NetworkProbeData probeData;
    private List<ReportData> dataList;
    private ServiceImageMap imageMap;

    public void addData(ReportData data) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.add(data);
    }

    public Topology convert() {
        Topology topo = new Topology();
        // 创建入口节点
        Element root = Element.builder()
                .id("255.255.255.255")
                .name("root")
                .build();
        topo.addElement(root);
        List<Port> rootPorts = new ArrayList<>();
        probeData.getEntryPointInterfaces().forEach(inter -> {
            Port port = Port.builder()
                    .id(genPortId(inter.getIp()))
                    .element(root.getId())
                    // FIXME 目前这里没有具体IP
                    //.ip(ip)
                    .cidr(inter.getIp())
                    .mac(inter.getMac())
                    .name(inter.getName())
                    .build();
            topo.addPort(port);
            rootPorts.add(port);
        });

        // 添加主机节点
        probeData.getIntranetIPs().forEach(network -> {
            String cidr = network.getCidr();
            if(network.getOnlineIPs() == null){
                return;
            }
            network.getOnlineIPs().forEach(host -> {
                String ip = host.getIp();
                String hostname = host.getHostname();

                Element element = Element.builder()
                        .id(genHostId(ip))
                        .name(hostname)
                        .build();
                topo.addElement(element);

                // 给默认IP分配网口
                Port port = Port.builder()
                        .id(genPortId(ip))
                        .element(element.getId())
                        .ip(ip)
                        .cidr(cidr)
                        .build();
                topo.addPort(port);
            });
        });

        // 补充镜像信息
        dataList.stream().filter(data -> probeData.getInstanceId().equals(data.getInstanceId())).forEach(data -> {
            // FIXME 这块逻辑目前可能存在问题，如果一个HOST上扫描出多个有效的端口服务，无法判断使用什么镜像
            String serviceName = data.getService().getName();
            if ("unknown".equals(serviceName)) {
                return;
            }

            // FIXME 通过服务名映射镜像的方法可能不准确，可以再考虑其他方案
            String image = imageMap.getImageByService(serviceName);
            // 更新节点镜像
            topo.updateElementImage(genHostId(data.getIp()), image);
        });

        // 根据路由添加端口和连线
        probeData.getTraceData().forEach(trace -> {
            if (!trace.notAllTimeout()) {
                return;
            }
            List<Hop> hops = trace.getHops();
            // 不处理非内网节点（路由会追踪到多层外网）
            if(!topo.containIp(hops.get(0).getIp())){
                return;
            }

            Map<Integer, Port> tmpPortMap = new HashMap<>();
            // 处理0跳的地址信息
            // FIXME root节点下多个网口，如何判断当前路由信息从哪个网口出？目前的规则时遍历接口，匹配第一跳目标地址，匹配上则使用此网口
            // 均未匹配上，则新增网口，如果为timeout，则新增网口
            Hop nextHop = hops.get(0);
            Port rootPort = findPort(rootPorts, nextHop.getIp());
            if(rootPort == null){
                rootPort = topo.addSimpleRootPort(root.getId(), nextHop.getIp());
            }
            Port nextPort = topo.getPort(nextHop.getIp());
            if(nextPort == null){
                nextPort = topo.addSimpleHost(nextHop.getIp(), nextHop.getHost());
                // 需要临时缓存下一跳网口信息，避免重复创建
                tmpPortMap.put(0, nextPort);
            }
            // 添加连线
            topo.addConnection(rootPort.getId(), nextPort.getId());


            for (int i = 0; i < hops.size()-1; i++) {
                Hop hop = hops.get(i);
                Hop next = hops.get(i+1);

                // 不处理非内网节点（路由会追踪到多层外网）
                if(!topo.containIp(next.getIp())){
                    continue;
                }

                // FIXME timeout如何处理？？？
                Port fromPort = topo.getPort(hop.getIp());
                Port toPort = topo.getPort(next.getIp());
                if(fromPort == null){
                    fromPort = tmpPortMap.get(i);
                    if (fromPort == null){
                        fromPort = topo.addSimpleHost(hop.getIp(), hop.getHost());
                    }
                }
                if(toPort == null){
                    toPort = topo.addSimpleHost(next.getIp(), next.getHost());
                    // 需要临时缓存下一跳网口信息，避免重复创建
                    tmpPortMap.put(i+1, toPort);
                }

                // 添加连线
                topo.addConnection(fromPort.getId(), toPort.getId());
            }
        });
        return topo;
    }

    // 因为场景内IP唯一，所以使用IP标识节点
    // FIXME 目前无法解决一个节点多个IP的情况（扫描信息没有给出节点指纹信息）
    private String genHostId(String ip) {
        return ip;
    }

    // 因为场景内IP唯一，所以使用IP标识网口
    // FIXME 目前无法解决一个节点多个IP的情况（扫描信息没有给出节点指纹信息）
    private String genPortId(String ip) {
        return ip;
    }

    // 查找下一条从哪个网口出
    private Port findPort(List<Port>ports, String nextHop){
        if("timeout".equals(nextHop)){
            return null;
        }
        for(Port port:ports){
            String cidr = port.getIp();
            if(cidr == null){
                continue;
            }
            try {
                InetAddress ip = InetAddress.getByName(nextHop);
                SubnetUtils subnet = new SubnetUtils(cidr);
                boolean isInSubnet = subnet.getInfo().isInRange(ip.getHostAddress());

                if (isInSubnet) {
                    return port;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
