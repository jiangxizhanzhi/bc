package com.cyberpeace.scene.sceneimport.convert;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.scene.base.BaseSceneElementModel;
import org.apache.commons.collections4.CollectionUtils;
import com.cyberpeace.scene.base.BaseSceneTopologyBO;
import com.cyberpeace.scene.sceneimport.parse.ResourceCenterYmlParser;
import com.cyberpeace.scene.sceneimport.parse.result.YmlParseResult;
import com.cyberpeace.scene.sceneimport.parse.result.image.ImageParseResult;

import java.util.ArrayList;
import java.util.List;

public class TopoTest {
    public static void main(String[] args) {
        String ymlFilePath = "E:\\cpms\\topo";
        String imageFilePath = "E:\\cpms\\topo";
        YmlParseResult ymlParseResult = ResourceCenterYmlParser.parseTopoYml(ymlFilePath);
        List<ImageParseResult> imageInfos =ResourceCenterYmlParser.parseImageServicesYml(ymlFilePath, imageFilePath);
        System.out.println(JSON.toJSONString(convert(ymlParseResult,imageInfos, ymlFilePath)));

    }

    public static BaseSceneTopologyBO convert(YmlParseResult parseResult, List<ImageParseResult> imageInfos, String sceneFilePath) {
        // 构建用于存储数据的容器
        ConvertDataContainer container = new ConvertDataContainer();
        container.setSceneFilePath(sceneFilePath);
        if (CollectionUtils.isNotEmpty(imageInfos)) {
            imageInfos.stream().forEach(imageInfo -> {
                if (imageInfo.getServers() != null) {
                    imageInfo.getServers().forEach((id, server) -> {
                        container.putImageInfo(id, server);
                    });
                }
            });
        }

        // 构建转换的责任链路
        List<IConverter> converters = new ArrayList<>();
        // 解析scene -> 解析node,创建端口 -> 解析network,创建端口,创建连线 -> 加载其余靶标信息 -> 加载来yml解析出的镜像信息
        converters.add(new PrepareConverter(parseResult));//处理networks中的Id如果有router-前缀的情况去除前缀
        converters.add(new SceneElementConverter(parseResult.getScene()));//设置SceneConfigBO场景基础信息
        converters.add(new NodeElementConverter(parseResult.getNodes()));//elements
        converters.add(new NetworkElementConverter(parseResult.getNetworks()));//elements connections
        converters.add(new Node2NodeConnectConverter(parseResult.getNodes()));//connections
//        converters.add(new DeviceInfoConverter(this.targetConverter));
//        converters.add(new ImageInfoConverter());
//        converters.add(new SceneFilePathConverter());
        converters.add(new PortIdAndNameConvert());
//        converters.add(new DefaultValueConverter());
        converters.add(new SceneElementShapeConverter(parseResult.getShape()));//areas
//        converters.add(new NetConfigElementConvert());
        converters.add(new ConnectionDisplayElementConvert());
        // 依次执行链路
        converters.forEach(converter -> converter.convert2BO(container));

        BaseSceneTopologyBO topologyBO = container.getSceneConfigTopologyBO();
        if (CollectionUtils.isNotEmpty(topologyBO.getElements())) {
            topologyBO.getElements().forEach(e -> {
                if (CollectionUtils.isNotEmpty(e.getPorts())) {
                    String prefix = (e.getType() != null && (e.getType()  == BaseSceneElementModel.Type.TERMINAL||e.getType()  == BaseSceneElementModel.Type.DOCKER)) ? "eth0" : "E0";
                    for (int i = 0; i < e.getPorts().size(); i++) {
//                        if (container.getEntityPortId2Name().containsKey(e.getPorts().get(i).getId())) {
//                            continue;
//                        }
                        e.getPorts().get(i).setName(prefix + "/" + (i + 1));
                    }
                }
            });
        }

        return topologyBO;
    }
}
