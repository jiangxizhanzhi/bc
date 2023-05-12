package com.cyberpeace.cs.service;

import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.CreateSnapshotContext;
import com.cyberpeace.cs.service.context.SceneFlowContext;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.scene.MergeSnapshotContext;

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
	 * 设定topo的类型
	 * @param topoId 拓扑id
	 * @param referenceType 类型
	 * @return 拓扑id
	 */
	String setTopoType(String topoId, Integer referenceType);

	/**
	 * delete mp topo
	 * @param topoId 
	 * @return
	 */
	void deleteTopo(String topoId);

	/**
	 * 重命名中台场景模板
	 * @return
	 */
	Boolean renameTopo(String topoId, String name);

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
	 * 获取场景流量映射表
	 * @param sceneData
	 * @param interactiveType
	 * @return Map<String, SceneFlowContext>
	 */
	Map<String, SceneFlowContext> getSceneFlowList(SceneData sceneData, Integer interactiveType);

	/**
	 * 随机启动一个流量
	 * @param mpSceneId
	 * @param sceneFlowContextMap
	 * @return Boolean
	 */
	Boolean startRandomFlow(String mpSceneId, Map<String, SceneFlowContext> sceneFlowContextMap);

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

	/**
	 * 节点快照
	 * @param localScene
	 * @param context
	 * @return
	 */
	Boolean nodeSnapshot(LocalScene localScene, CreateSnapshotContext context);

	/**
	 * 场景快照历史记录
	 * @param sceneId
	 * @param imageId
	 * @return
	 */
	JSONArray snapshotHistory(String sceneId, String imageId);

	/**
	 * 场景节点快照合并
	 * @param sceneId
	 * @param snapshotContext
	 * @return
	 */
	JSONObject mergeSnapshot(String sceneId, MergeSnapshotContext snapshotContext);

	/**
	 * 删除快照
	 * @param sceneId
	 * @param nodeId
	 * @param snapshotId
	 * @return
	 */
	Boolean deleteSnapshot(String sceneId, String nodeId, String snapshotId);

	/**
	 * 场景暂停
	 * @param localScene
	 * @return
	 */
	Boolean pauseScene(LocalScene localScene);

	/**
	 * 场景从暂停状态恢复为运行状态
	 * @param localScene
	 * @return
	 */
	Boolean unpauseScene(LocalScene localScene);

	Boolean createNode(String localSceneId, String nodeId);

	/**
	 * 动态更新节点属性
	 * @param mpSceneId 场景id
	 * @param updateData 更新节点内容，详见 http://10.10.10.11/pages/viewpage.action?pageId=69587011
	 * @return
	 */
	Boolean updateNode(String mpSceneId, JSONObject updateData);

	/**
	 * 删除节点
	 *
	 * @param mpSceneId 场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean}
	 */
	Boolean deleteNode(String mpSceneId, String nodeId);

	/**
	 * 动态更新场景
	 *
	 * @param localScene 当地场景
	 * @param updateScene  场景数据
	 */
	void updateScene(LocalScene localScene, JSONObject updateScene);
}
