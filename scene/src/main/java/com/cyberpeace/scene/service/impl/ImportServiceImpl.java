package com.cyberpeace.scene.service.impl;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.scene.base.BaseSceneElementModel;
import com.cyberpeace.scene.base.BaseSceneTopologyBO;
import com.cyberpeace.scene.dao.entity.TopologyEntity;
import com.cyberpeace.scene.sceneimport.convert.*;
import com.cyberpeace.scene.sceneimport.parse.ResourceCenterYmlParser;
import com.cyberpeace.scene.sceneimport.parse.result.YmlParseResult;
import com.cyberpeace.scene.sceneimport.parse.result.image.ImageParseResult;
import com.cyberpeace.scene.service.ImportService;
import com.cyberpeace.scene.service.TopologyService;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

    @Autowired
    private TopologyService topologyService;
    @Override
    public String importByYmlConfig(String ymlFilePath, String imageFilePath, String sceneFilePath) {
        YmlParseResult ymlParseResult = ResourceCenterYmlParser.parseTopoYml(ymlFilePath);
        List<ImageParseResult> imageInfos =ResourceCenterYmlParser.parseImageServicesYml(ymlFilePath, imageFilePath);
        BaseSceneTopologyBO convert = ResourceCenterConverter.convert(ymlParseResult, imageInfos, ymlFilePath);
        TopologyEntity topologyEntity = new TopologyEntity();
        topologyEntity.setTopology(JSON.toJSONString(convert));
        String topoId = IDGeneratorUtil.getID();
        topologyEntity.setTopologyId(topoId);
        topologyService.save(topologyEntity);
        return topoId;
    }


}
