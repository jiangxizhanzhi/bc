package com.cyberpeace.situation.service;

/**
 * 场景配置Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface SceneConfigService {

    /**
     * 更新场景代号
     *
     * @param sceneId 场景id
     * @return {@code String }
     * @throws
     * @see String
     * @see String
     */
    String updateSceneCode(String sceneId);

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
    Boolean isCurrentScheme(String sceneId, String code);

    /**
     * 更新附表由id
     *
     * @param sceneId       场景id
     * @param scheduleStage Schedule 阶段
     * @throws
     * @see String
     * @see String
     */
    void updateScheduleById(String sceneId, String scheduleStage);

    /**
     * 获取附表由场景id
     *
     * @param sceneId 场景id
     * @return {@code String }
     * @throws
     * @see String
     * @see String
     */
    String getScheduleBySceneId(String sceneId);

}
