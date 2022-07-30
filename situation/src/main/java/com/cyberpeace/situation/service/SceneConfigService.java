package com.cyberpeace.situation.service;

public interface SceneConfigService {

    String updateSceneCode(String sceneId);

    Boolean isCurrentScheme(String sceneId, String code);

    Boolean updateScheduleById(String scheduleId, String scheduleStage);

    String getScheduleBySceneId(String sceneId);
}
