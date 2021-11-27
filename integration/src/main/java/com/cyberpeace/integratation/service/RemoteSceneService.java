package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.sun.org.apache.xpath.internal.operations.Bool;

public interface RemoteSceneService {

	String startScene(SceneOpsContext sceneStartContext, Boolean isTest);

	Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext);

	Boolean pauseScene(String sceneId, SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);

	Boolean unpauseScene(String mpSceneId, SceneOpsContext sceneOpContext);
}
