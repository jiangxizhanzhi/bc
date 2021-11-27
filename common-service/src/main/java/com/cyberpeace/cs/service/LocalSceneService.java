package com.cyberpeace.cs.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;

public interface LocalSceneService extends IService<LocalScene> {

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	Boolean startScene(String localSceneId, String userId);

	Boolean stopScene(String localSceneId, String userId);

	Boolean closeScene(String localSceneId);

	Boolean pauseScene(String localSceneId);

	Boolean unPause(String localSceneId);

	LocalScene copyLocalScene(Integer appId, String sceneManagerId, Boolean clone);

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

	JSONObject getTopoData(LocalScene scene);

	void updateMpSceneId(LocalScene scene, String sceneId);

	void deleteRemoteScene(LocalScene scene, String userId);

	void stopLocalScene(LocalScene scene, String userId);

}
