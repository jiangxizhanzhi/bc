package com.cyberpeace.scene.sceneimport.convert;

import com.cyberpeace.scene.base.*;
import com.cyberpeace.scene.constant.DefaultImageUrl;
import com.cyberpeace.scene.constant.TargetConstant;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.cyberpeace.scene.json.JsonCommon;
import com.cyberpeace.scene.sceneimport.parse.result.NetworkElementParseResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * @Description
 * @Author maojun
 * @Date 2021/10/12
 */
class NetworkElementConverter implements IConverter {

    private List<NetworkElementParseResult> results;

    public NetworkElementConverter(List<NetworkElementParseResult> results) {
        this.results = results == null ? new ArrayList<>() : results;
    }

    public void convert2BO(ConvertDataContainer container) {
        // 处理下外部网络
        if (CollectionUtils.isNotEmpty(results)) {
            results.stream().forEach(network -> this.createElementAndPort(network, container));
        }
    }


    private void createElementAndPort(NetworkElementParseResult network, ConvertDataContainer container) {
        BaseSceneElementBO sceneConfigElementBO = new BaseSceneElementBO();
        container.addNetworkElement(sceneConfigElementBO);
        sceneConfigElementBO.setName(network.getName());
        sceneConfigElementBO.setId(network.getId());
        sceneConfigElementBO.setType(BaseSceneElementModel.Type.SWITCH);
        //不一致 element icon2
        sceneConfigElementBO.setIcon2(DefaultImageUrl.SWITCHER);
        List<BaseSceneElementPortBO> ports = new ArrayList<>();
        sceneConfigElementBO.setPorts(ports);
        sceneConfigElementBO.setDisplayContext(network.getDisplayContext());

        if (StringUtils.isNotEmpty(network.getPosition())) {
            String[] xy = network.getPosition().split(",");
            Origin origin = new Origin(formatDouble(xy[0]), formatDouble(xy[1]));
            sceneConfigElementBO.setPosition2(JsonCommon.stringify(origin));
        }
        BaseCloudPluginSceneElementBO pluginElement = new BaseCloudPluginSceneElementBO();
        pluginElement.setCidr(network.getCidr());
        pluginElement.setGateway(network.getGateway());
        if (network.getDhcp() == null) {
            // dhcp 默认开启
            pluginElement.setDhcp(true);
        } else {
            pluginElement.setDhcp("on".equals(network.getDhcp()) || "true".equals(network.getDhcp()));
        }
        if (StringUtils.isNotEmpty(network.getDhcpPool())) {
            List<AllocationPool> allocationList = new ArrayList<>();
            String[] allocationStrs = StringUtils.split(network.getDhcpPool(), ",");
            if (allocationStrs != null && allocationStrs.length > 0) {
                for (String dhcpStr : allocationStrs) {
                    String[] dhcpStartAndEnd = StringUtils.split(dhcpStr, "-");
                    if (dhcpStartAndEnd != null && dhcpStartAndEnd.length == 2) {
                        AllocationPool allocationPool = new AllocationPool();
                        allocationPool.setStart(dhcpStartAndEnd[0]);
                        allocationPool.setEnd(dhcpStartAndEnd[1]);
                        allocationList.add(allocationPool);
                    }
                }
                if (CollectionUtils.isNotEmpty(allocationList)) {
                    pluginElement.setAllocationPools(allocationList);
                }
            }
        }
        if (!StringUtils.isEmpty(network.getDns())) {
            if (network.getDns().contains(",")) {
                String[] dnsArray = StringUtils.split(network.getDns(), ",");
                pluginElement.setDnsNameservers(Arrays.asList(dnsArray));
            } else {
                pluginElement.setDnsNameservers(Arrays.asList(network.getDns()));
            }
        }
        pluginElement.setGateway(network.getGateway());
        if (network.getId().toLowerCase().startsWith("internet")) {
            // id以internet开头,则是外部网络
            pluginElement.setRole(BaseCloudPluginSceneElementModel.Role.VIRTUAL_EXTERNAL_NETWORK);
            pluginElement.setImage(TargetConstant.DefaultTargetName.NETWORK_EXTERNAL);
        } else {
            if (container.getSimpleLayer3Switch().contains(network.getId())) {
                pluginElement.setRole(BaseCloudPluginSceneElementModel.Role.VIRTUAL_LAYER_3_SIMPLE_SWITCH);
            } else {
                pluginElement.setRole(BaseCloudPluginSceneElementModel.Role.VIRTUAL_LAYER_2_GATEWAY);
            }
            pluginElement.setImageType(BaseCloudPluginSceneElementModel.ImageType.SIMPLE);
            pluginElement.setImage(TargetConstant.DefaultTargetName.DEFAULT_NETWORK);
        }
        container.putPluginElement(sceneConfigElementBO.getId(), pluginElement);


        // 判断和其他节点是否有连线
        for (Map<String, BaseSceneElementPortBO> portsMap : container.getNode2NetworkPorts().values()) {
            BaseSceneElementPortBO connectPort = portsMap.get(network.getId());
            // 不存在连线
            if (connectPort == null) {
                continue;
            }

            // 存在连线 先创建端口
            BaseSceneElementPortBO newPort = new BaseSceneElementPortBO();
            newPort.setId(IConverter.getPortId(sceneConfigElementBO.getId(), sceneConfigElementBO.getType(), ports.size()));
            newPort.setGateway(network.getGateway());
            newPort.setDhcp("on".equals(network.getDhcp()) || "true".equals(network.getDhcp()));
            newPort.setName(network.getName());
            newPort.setDns(network.getDns());
            ports.add(newPort);

            BaseCloudPluginSceneElementPortBO pluginPort = new BaseCloudPluginSceneElementPortBO();
            container.putPluginElementPort(newPort.getId(), pluginPort);

            // 创建连线
            BaseSceneElementPortConnectionBO connection = new BaseSceneElementPortConnectionBO();
            connection.setId(uuid());
            connection.setSource(newPort.getId());
            connection.setTarget(connectPort.getId());
            // todo
            connection.setName(newPort.getName() + "_" + connectPort.getName());
            container.addConnection(connection);
        }

    }
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", StringUtils.EMPTY).toLowerCase();
    }
    private double formatDouble(String number) {
        return new BigDecimal(number).setScale(6, RoundingMode.HALF_DOWN).doubleValue();
    }

    @Getter
    @Setter
    private static class Origin {
        private double x;
        private double y;

        public Origin(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}
