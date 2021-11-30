package com.cyberpeace.integratation.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.scene.NodeOpsContext;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

public interface RemoteSceneService {

	/**
	 * 启动前台场景
	 */
	String startScene(SceneStartContext sceneStartContext);

	/**
	 * 启动后台测试场景
	 */
	String testScene(SceneStartContext sceneStartContext);

	Boolean stopScene(SceneOpsContext sceneOpsContext);

	Boolean pauseScene(SceneOpsContext sceneOpsContext);

	JSONObject getById(String sceneId, String userId);

	Boolean unpauseScene(SceneOpsContext sceneOpContext);

	Boolean restartNode(NodeOpsContext nodeOpsContext);

	Boolean recreateNode(NodeOpsContext nodeOpsContext);
}
