package com.cyberpeace.scene.sceneimport.convert;

import com.cyberpeace.scene.base.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import com.cyberpeace.scene.constant.CommonConstant;
import com.cyberpeace.scene.json.JsonCommon;
import com.cyberpeace.scene.sceneimport.parse.result.NetworkElementParseResult;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
//        sceneConfigElementBO.setIcon2(DefaultImgUtil.pathToFdfsUrl(CommonConstant.DefaultImageUrl.SWITCHER));
        List<BaseSceneElementPortBO> ports = new ArrayList<>();
        sceneConfigElementBO.setPorts(ports);
        sceneConfigElementBO.setDisplayContext(network.getDisplayContext());

        if (StringUtils.isNotEmpty(network.getPosition())) {
            String[] xy = network.getPosition().split(",");
            Origin origin = new Origin(formatDouble(xy[0]), formatDouble(xy[1]));
            sceneConfigElementBO.setPosition2(JsonCommon.stringify(origin));
        }



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
