package com.cyberpeace.cr.crbase.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.LocalSceneManager;
import com.cyberpeace.cr.crbase.service.context.scene.RoomInfo;

public interface SceneManagerService extends IService<LocalSceneManager> {

	LocalSceneManager copySceneManager(Integer appId, String sceneManagerId, Boolean clone);

	LocalSceneManager createManager(Integer appId, String topoId);

	/**
	 * create topo
	 */
	String saveTopo(JSONObject topoData, Integer appId);

	/**
	 * update topo
	 * @return topoId
	 */
	String saveTopo(JSONObject topoData, String topoId);

	Integer getNodeCount(LocalSceneManager sceneManager);

	Integer getNodeCount(String managerId);

	JSONObject getSceneData(LocalSceneManager sceneManager, String userId);

	JSONObject getTopoData(LocalSceneManager sceneManager);

	void updateScene(LocalSceneManager sceneManager, String sceneId);

	String startScene(LocalSceneManager sceneManager, RoomInfo roomInfo, String userId);

	void deleteRemoteScene(LocalSceneManager sceneManager, String userId);
}