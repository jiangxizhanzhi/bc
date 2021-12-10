package com.cyberpeace.cs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;

public interface LocalSceneService extends IService<LocalScene> {

	LocalScene getById(String localSceneId);

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	String startScene(LocalScene localScene);

	String testScene(LocalScene localScene);

	Boolean stopScene(String localSceneId);

	Boolean pauseScene(String localSceneId);

	Boolean unpauseScene(String localSceneId);

	/**
	 * create topo
	 */
	String saveTopo(JSONObject topoData, Integer businessType);

	/**
	 * update topo
	 * @return topoId
	 */
	String saveTopo(JSONObject topoData, String topoId);

	Integer getNodeCount(LocalScene localScene);

	Integer getNodeCount(String localSceneId);

	JSONObject getSceneData(LocalScene scene, String userId);

	JSONObject getSceneData(String sceneId, String userId);

	JSONObject getTopoData(String topoId);

	Boolean restartNode(String localSceneId, String nodeId);

	Boolean recreateNode(String localSceneId, String nodeId);

	Boolean startFlow(String localSceneId, String nodeId, String flowId);

	/**
	 * 关闭mp场景
	 * @param scene
	 */
	void deleteRemoteScene(LocalScene scene);

}
