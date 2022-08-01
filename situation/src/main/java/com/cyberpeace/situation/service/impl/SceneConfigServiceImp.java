package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.dao.entity.SceneConfig;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.service.SceneConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SceneConfigServiceImp implements SceneConfigService {

    @Autowired
    private SceneConfigMapper sceneConfigMapper;

    @Override
    public String updateSceneCode(String sceneId){
        SceneConfig sceneConfig = sceneConfigMapper.selectBySceneId(sceneId);
        String code = IDGeneratorUtil.getID();

        if(sceneConfig == null){
            sceneConfig = new SceneConfig();
            sceneConfig.setSchemeCode(code);
            sceneConfig.setSceneId(sceneId);
            sceneConfigMapper.insert(sceneConfig);
        }else {
            sceneConfig.setSchemeCode(code);
            sceneConfigMapper.updateById(sceneConfig);
        }
        return code;
    }

}
