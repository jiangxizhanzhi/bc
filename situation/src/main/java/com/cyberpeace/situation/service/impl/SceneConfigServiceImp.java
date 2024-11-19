package com.cyberpeace.situation.service.impl;

import com.cyberpeace.common.util.IDGeneratorUtil;
import com.cyberpeace.situation.service.SceneConfigService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 场景配置Service小鬼
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see SceneConfigService
 * @since 1.0.0
 */
@Service
public class SceneConfigServiceImp implements SceneConfigService {

    /**
     * 方案代号缓存
     *
     * @see Cache<String, String>
     */
    Cache<String, String> schemeCodeCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS).build();

    /**
     * scheme 阶段缓存
     *
     * @see Cache<String, String>
     */
    Cache<String, String> schemeStageCache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.DAYS).build();

    /**
     * 更新场景代号
     *
     * @param sceneId 场景id
     * @return {@code String }
     * @throws
     * @see String
     * @see String
     */
    @Override
    public String updateSceneCode(String sceneId){
        String code = IDGeneratorUtil.getID();
        schemeCodeCache.put(sceneId, code);
        return code;
    }

    /**
     * 是否当前方案
     *
     * @param sceneId 场景id
     * @param code    代号
     * @return {@code Boolean }
     * @throws
     * @see String
     * @see String
     * @see Boolean
     */
    @Override
    public Boolean isCurrentScheme(String sceneId, String code){
        String currentCode = schemeCodeCache.getIfPresent(sceneId);
        return code.equals(currentCode);
    }

    /**
     * 更新附表由id
     *
     * @param sceneId       场景id
     * @param scheduleStage Schedule 阶段
     * @throws
     * @see String
     * @see String
     */
    @Override
    public void updateScheduleById(String sceneId, String scheduleStage){
        schemeStageCache.put(sceneId, scheduleStage);
    }

    /**
     * 获取附表由场景id
     *
     * @param sceneId 场景id
     * @return {@code String }
     * @throws
     * @see String
     * @see String
     */
    @Override
    public String getScheduleBySceneId(String sceneId){
        return schemeStageCache.getIfPresent(sceneId);
    }
}
