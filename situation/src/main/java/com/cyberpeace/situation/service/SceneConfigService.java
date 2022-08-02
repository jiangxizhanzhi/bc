package com.cyberpeace.situation.service;

public interface SceneConfigService {

    String updateSceneCode(String sceneId);

    Boolean isCurrentScheme(String sceneId, String code);

    void updateScheduleById(String sceneId, String scheduleStage);

    String getScheduleBySceneId(String sceneId);

}
