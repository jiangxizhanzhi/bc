package com.cyberpeace.situation.service;

import com.cyberpeace.situation.model.DynamicSchemeProcess;

public interface SceneConfigService {

    /**
     * 更新当前场景下运行动效方案的code
     * @param sceneId 场景id
     * @return 随机生成的动效方案code
     */
    String updateSceneCode(String sceneId);

    /**
     * 清除当前场景下运行动效方案的code
     * @param sceneId 场景id
     */
    void clearSceneCode(String sceneId);

    /**
     * 判断当前场景是否有其他动效方案正在运行
     * @param sceneId 场景id
     * @param code 随机生成的动效方案的code
     * @return true:无，false:有
     */
    Boolean isCurrentScheme(String sceneId, String code);

    /**
     * 更新当前场景下的动效方案进程信息
     * @param sceneId 场景id
     * @param scheduleStage 进程信息
     */
    void updateScheduleById(String sceneId, String scheduleStage);

    /**
     * 获取当前场景下的进程信息
     * @param sceneId 场景id
     * @return 进程信息
     */
    String getScheduleBySceneId(String sceneId);

    /**
     * 获取当前场景下的运行的动效方案code
     * @param sceneId 场景id
     * @return 运行的动效方案code
     */
    String getCurrentScheme(String sceneId);

    /**
     * 保存当前动效方案的数据和运行进程数据，用于继续动效方案的运行
     * @param sceneId 场景id
     * @param dynamicSchemeProcess 动效方案的数据和运行数据
     */
    void setDynamicSchemeCache(String sceneId, DynamicSchemeProcess dynamicSchemeProcess);

    /**
     * 获取当前场景的动效方案数据和运行数据，用于继续动效方案的运行
     * @param sceneId 场景id
     * @return 动效方案的数据和运行数据
     */
    DynamicSchemeProcess getDynamicSchemeCache(String sceneId);
}
