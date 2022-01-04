package com.cyberpeace.cs.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;

public interface LocalSceneService extends IService<LocalScene> {

	LocalScene getById(String localSceneId);

	LocalScene getByBusinessInstanceId(String businessInstanceId);

	String startScene(LocalScene localScene);

	String testScene(LocalScene localScene);

	Boolean stopScene(String localSceneId);

	Boolean stopScene(LocalScene localScene);

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

	/**
	 * 重命名中台场景模板
	 */
	String renameTopo(String topoId, String name);

	Integer getNodeCount(LocalScene localScene);

	Integer getNodeCount(String localSceneId);

	JSONObject getSceneData(LocalScene scene, String userId);

	JSONObject getSceneData(String mpSceneId, String userId);

	JSONObject getTopoData(String topoId);

	/**
	 * 执行节点和流量操作前确认场景已开启
	 */
	Boolean ensureSceneStarted(LocalScene localScene);

	Boolean ensureSceneStarted(String localSceneId);

	Boolean restartNode(String localSceneId, String nodeId);

	Boolean recreateNode(String localSceneId, String nodeId);

	Boolean startFlow(LocalScene localScene, String nodeId, String flowId);

	Boolean stopFlow(LocalScene localScene, String nodeId, String flowId);

	/**
	 * 关闭mp场景
	 * @param scene
	 */
	void deleteRemoteScene(LocalScene scene);

	/**
	 * 获取监控会话
	 * @param localScene
	 * @param studentId
	 * @return
	 */
	JSONArray getMonitorSessions(LocalScene localScene, String studentId);

}
