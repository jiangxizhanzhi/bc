package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.dao.entity.SceneConfig;
import com.cyberpeace.situation.dao.mapper.SceneConfigMapper;
import com.cyberpeace.situation.service.SceneConfigService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class SceneConfigServiceImpl implements SceneConfigService {

    Cache<String, String> schemeCodeCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS).build();

    Cache<String, String> schemeStageCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS).build();


    @Override
    public String updateSceneCode(String sceneId){
        String code = IDGeneratorUtil.getID();
        schemeCodeCache.put(sceneId, code);
        return code;
    }

    @Override
    public Boolean isCurrentScheme(String sceneId, String code){
        String currentCode = schemeCodeCache.getIfPresent(sceneId);
        return code.equals(currentCode);
    }

    @Override
    public Boolean updateScheduleById(String scheduleId, String scheduleStage){
        schemeStageCache.put(scheduleId, scheduleStage);
        return true;
    }

    @Override
    public String getScheduleBySceneId(String sceneId){
        return schemeStageCache.getIfPresent(sceneId);
    }
}
