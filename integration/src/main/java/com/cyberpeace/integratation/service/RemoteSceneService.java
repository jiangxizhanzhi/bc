package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

public interface RemoteSceneService {

	String startScene(String topoId, SceneOpsContext sceneStartContext);

	String startScene(String topoId, SceneOpsContext sceneStartContext, Boolean isTest);

	Boolean stopScene(String sceneId, SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);
}
