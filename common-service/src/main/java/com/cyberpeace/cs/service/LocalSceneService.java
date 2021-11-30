package com.cyberpeace.cs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;

public interface LocalSceneService extends IService<LocalScene> {

	LocalScene getById(String localSceneId);

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	String startScene(LocalScene localScene);

	Boolean stopScene(String localSceneId, String userId);

	Boolean pauseScene(String localSceneId);

	Boolean unPauseScene(String localSceneId);

	LocalScene copyLocalScene(Integer businessType, String localSceneId, Boolean clone);

	LocalScene createLocalScene(Integer appId, String topoId);

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

	void updateMpSceneId(LocalScene scene, String sceneId);

	void deleteRemoteScene(LocalScene scene);

	void stopLocalScene(LocalScene scene, String userId);

	Boolean restartNode(String localSceneId, String nodeId);

	Boolean recreateNode(String localSceneId, String nodeId);

	Boolean startFlow(String localSceneId, String nodeId, String flowId);
}
