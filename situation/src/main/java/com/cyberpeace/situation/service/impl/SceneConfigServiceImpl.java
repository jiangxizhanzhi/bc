package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.model.DynamicSchemeProcess;
import com.cyberpeace.situation.service.SceneConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SceneConfigServiceImpl implements SceneConfigService {

    Map<String, String> schemeCodeCache = new HashMap<>();

    Map<String, String> schemeStageCache = new HashMap<>();

    Map<String, DynamicSchemeProcess> dynamicSchemeCache = new HashMap<>();

    @Override
    public String updateSceneCode(String sceneId){
        String code = IDGeneratorUtil.getID();
        schemeCodeCache.put(sceneId, code);
        return code;
    }

    @Override
    public void clearSceneCode(String sceneId){
        schemeCodeCache.remove(sceneId);
    }

    @Override
    public Boolean isCurrentScheme(String sceneId, String code){
        String currentCode = schemeCodeCache.getOrDefault(sceneId, null);
        return code.equals(currentCode);
    }

    @Override
    public void updateScheduleById(String sceneId, String scheduleStage){
        schemeStageCache.put(sceneId, scheduleStage);
    }

    @Override
    public String getScheduleBySceneId(String sceneId){
        return schemeStageCache.getOrDefault(sceneId, null);
    }

    @Override
    public String getCurrentScheme(String sceneId){
        return schemeCodeCache.getOrDefault(sceneId, null);
    }

    @Override
    public void setDynamicSchemeCache(String sceneId, DynamicSchemeProcess dynamicSchemeProcess){
        dynamicSchemeCache.put(sceneId, dynamicSchemeProcess);
    }

    @Override
    public DynamicSchemeProcess getDynamicSchemeCache(String sceneId){
        return dynamicSchemeCache.getOrDefault(sceneId, null);
    }

}
