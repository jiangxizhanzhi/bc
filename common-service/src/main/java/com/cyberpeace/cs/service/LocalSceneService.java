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

/**
 * 本地场景服务
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public interface LocalSceneService extends IService<LocalScene> {

	/**
	 * 通过id获取
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link LocalScene}
	 */
	LocalScene getById(String localSceneId);

	/**
	 * 由业务实例id获取
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@link LocalScene}
	 */
	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 开始场景
	 *
	 * @param localScene 本地场景
	 * @return {@link String}
	 */
	String startScene(LocalScene localScene);

	/**
	 * 测试场景
	 *
	 * @param localScene 本地场景
	 * @return {@link String}
	 */
	String testScene(LocalScene localScene);

	/**
	 * 停止场景
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link Boolean}
	 */
	Boolean stopScene(String localSceneId);

	/**
	 * 停止场景
	 *
	 * @param localScene 本地场景
	 * @return {@link Boolean}
	 */
	Boolean stopScene(LocalScene localScene);

	/**
	 * 暂停场景
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link Boolean}
	 */
	Boolean pauseScene(String localSceneId);

	/**
	 * unpause场景
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link Boolean}
	 */
	Boolean unpauseScene(String localSceneId);

	/**
	 * 保存拓扑
	 * create topo
	 *
	 * @param topoData     拓扑数据
	 * @param businessType 业务类型
	 * @return {@link String}
	 */
	String saveTopo(JSONObject topoData, Integer businessType);

	/**
	 * 保存拓扑
	 * update topo
	 *
	 * @param topoData 拓扑数据
	 * @param topoId   拓扑id
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
	 * 删除拓扑
	 * delete mp topo
	 *
	 * @param topoId 拓扑id
	 */
	void deleteTopo(String topoId);

	/**
	 * 重命名拓扑
	 * 重命名中台场景模板
	 *
	 * @param topoId 拓扑id
	 * @param name   名字
	 * @return {@link Boolean}
	 */
	Boolean renameTopo(String topoId, String name);

	/**
	 * 得到节点数
	 *
	 * @param localScene 本地场景
	 * @return {@link Integer}
	 */
	Integer getNodeCount(LocalScene localScene);

	/**
	 * 得到节点数
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link Integer}
	 */
	Integer getNodeCount(String localSceneId);

	/**
	 * 得到场景数据
	 *
	 * @param scene  场景
	 * @param userId 用户id
	 * @return {@link JSONObject}
	 */
	JSONObject getSceneData(LocalScene scene, String userId);

	/**
	 * 得到场景数据
	 *
	 * @param mpSceneId 场景id
	 * @param userId    用户id
	 * @return {@link JSONObject}
	 */
	JSONObject getSceneData(String mpSceneId, String userId);

	/**
	 * 得到拓扑数据
	 *
	 * @param topoId 拓扑id
	 * @return {@link JSONObject}
	 */
	JSONObject getTopoData(String topoId);

	/**
	 * 确保场景开始
	 * 执行节点和流量操作前确认场景已开启
	 *
	 * @param localScene 本地场景
	 * @return {@link Boolean}
	 */
	Boolean ensureSceneStarted(LocalScene localScene);

	/**
	 * 确保场景开始
	 *
	 * @param localSceneId 本地场景id
	 * @return {@link Boolean}
	 */
	Boolean ensureSceneStarted(String localSceneId);

	/**
	 * 重新启动节点
	 *
	 * @param localSceneId 本地场景id
	 * @param nodeId       节点id
	 * @return {@link Boolean}
	 */
	Boolean restartNode(String localSceneId, String nodeId);

	/**
	 * 重新创建节点
	 *
	 * @param localSceneId 本地场景id
	 * @param nodeId       节点id
	 * @return {@link Boolean}
	 */
	Boolean recreateNode(String localSceneId, String nodeId);

	/**
	 * 开始流
	 *
	 * @param localScene 本地场景
	 * @param nodeId     节点id
	 * @param flowId     流id
	 * @return {@link Boolean}
	 */
	Boolean startFlow(LocalScene localScene, String nodeId, String flowId);

	/**
	 * 停止流
	 *
	 * @param localScene 本地场景
	 * @param nodeId     节点id
	 * @param flowId     流id
	 * @return {@link Boolean}
	 */
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
	 * 删除远程场景
	 * 关闭mp场景
	 *
	 * @param scene 场景
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
	 *
	 * @param localScene 本地场景
	 * @param context    上下文
	 * @return {@link Boolean}
	 */
	Boolean nodeSnapshot(LocalScene localScene, CreateSnapshotContext context);

	/**
	 * 快照历史
	 * 场景快照历史记录
	 *
	 * @param sceneId 场景id
	 * @param imageId 形象标识
	 * @return {@link JSONArray}
	 */
	JSONArray snapshotHistory(String sceneId, String imageId);

	/**
	 * 合并快照
	 * 场景节点快照合并
	 *
	 * @param sceneId         场景id
	 * @param snapshotContext 快照上下文
	 * @return {@link JSONObject}
	 */
	JSONObject mergeSnapshot(String sceneId, MergeSnapshotContext snapshotContext);

	/**
	 * 删除快照
	 *
	 * @param sceneId    场景id
	 * @param nodeId     节点id
	 * @param snapshotId 快照id
	 * @return {@link Boolean}
	 */
	Boolean deleteSnapshot(String sceneId, String nodeId, String snapshotId);
}
