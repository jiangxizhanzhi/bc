package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;

public interface RemoteSceneService {

	String startScene(SceneOpsContext sceneStartContext, Boolean isTest);

	Boolean stopScene(SceneOpsContext sceneOpsContext);

	Boolean pauseScene(SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);

	Boolean unpauseScene(SceneOpsContext sceneOpContext);

	Boolean restartNode(NodeOpsContext nodeOpsContext);

	Boolean recreateNode(NodeOpsContext nodeOpsContext);
}
