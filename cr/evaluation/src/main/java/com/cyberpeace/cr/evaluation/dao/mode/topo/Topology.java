package com.cyberpeace.cr.evaluation.dao.mode.topo;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.net.InetAddress;
import java.util.*;

@Data
public class Topology {
    private List<Element> elelemts = new ArrayList<>();
    private List<Port> ports = new ArrayList<>();
    private List<Connection> connections = new ArrayList<>();

    @Expose(serialize = false, deserialize = false)
    private Map<String, Element> elementMap = new HashMap<>();

    @Expose(serialize = false, deserialize = false)
    private Map<String, Port> portMap = new HashMap<>();

    @Expose(serialize = false, deserialize = false)
    private Set<String> connSet = new HashSet<>();


    public void addElement(Element element) {
        if (elementMap.containsKey(element.getId())) {
            return;
        }
        elelemts.add(element);
        elementMap.put(element.getId(), element);
    }

    public void addPort(Port port) {
        if (portMap.containsKey(port.getId())) {
            return;
        }
        ports.add(port);
        portMap.put(port.getId(), port);
    }

    public void updateElementImage(String element, String image) {
        if (!elementMap.containsKey(element)) {
            return;
        }
        elementMap.get(element).setImage(image);
    }

    public boolean containIp(String ip) {
        if("timeout".equals(ip)){
            return false;
        }
        // 暂时使用ip来判断节点
        return elementMap.containsKey(ip);
    }

    public Port getPort(String ip) {
        return portMap.get(ip);
    }

    // 使用路由跳转信息添加的主机信息（同时添加网口）
    public Port addSimpleHost(String ip, String name) {
        String id = ip;
        // FIXME 目前还没有想到如何处理路由超时节点的方案，先生成临时ID
        if("timeout".equals(ip)){
            id="timeout_"+UUID.randomUUID();
        }
        Element element = Element.builder().id(id).name(name).build();
        Port port = Port.builder().element(element.getId()).ip(ip).id(id).build();

        elelemts.add(element);
        elementMap.put(element.getId(), element);
        ports.add(port);
        portMap.put(port.getId(), port);

        return port;
    }

    // 使用路由信息给根节点添加0跳网口
    public Port addSimpleRootPort(String id, String nextHop){
        String ip = getCidr4Ip(nextHop);
        Port port = Port.builder().element(id).ip(getCidr4Ip(nextHop)).id(ip).build();
        ports.add(port);
        portMap.put(port.getId(), port);
        return port;
    }

    public void addConnection(String from, String to){
        if(from.equals(to)){
            return;
        }
        if(connSet.contains(from+"-"+to)){
            return;
        }
        connections.add(Connection.builder().from(from).to(to).build());
        connSet.add(from+"-"+to);
    }

    private String getCidr4Ip(String ipAddress){
        if("timeout".equals(ipAddress)){
            return "0.0.0.0/24";
        }
        // 计算固定24位掩码的cidr
        int subnetMaskBits = 24; // 固定为24位子网掩码

        try {
            InetAddress ip = InetAddress.getByName(ipAddress);
            byte[] addressBytes = ip.getAddress();

            // 将IP地址的最后一个字节设置为0
            addressBytes[3] = 0;

            // 转换为CIDR表示法
            return InetAddress.getByAddress(addressBytes).getHostAddress() + "/" + subnetMaskBits;
        } catch (Exception e) {
            e.printStackTrace();
            return "0.0.0.0/24";
        }
    }
}
