package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;

public interface RemoteSceneService {

	String startScene(SceneOpsContext sceneStartContext, Boolean isTest);

	Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);
}
