package com.cyberpeace.cs.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.CreateSnapshotContext;
import com.cyberpeace.cs.service.context.SceneFlowContext;
import com.cyberpeace.cs.service.context.scene.SceneData;
import com.cyberpeace.integratation.model.mp.MpHostState;
import com.cyberpeace.integratation.model.scene.MergeSnapshotContext;
import com.cyberpeace.integratation.model.scene.SceneNodeListReq;

/**
 * 当地场景Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface LocalSceneService extends IService<LocalScene> {

	/**
	 * 获取由id
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code LocalScene }
	 */
	LocalScene getById(String localSceneId);

	/**
	 * 获取由业务实例id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code LocalScene }
	 */
	LocalScene getByBusinessInstanceId(String businessInstanceId);

	/**
	 * 开始场景
	 *
	 * @param localScene 当地场景
	 * @return {@code String }
	 */
	String startScene(LocalScene localScene);

	/**
	 * 测试场景
	 *
	 * @param localScene 当地场景
	 * @return {@code String }
	 */
	String testScene(LocalScene localScene);

	/**
	 * 停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 */
	Boolean stopScene(String localSceneId);

	/**
	 * 停场景
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 */
	Boolean stopScene(LocalScene localScene);

	/**
	 * 暂停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 */
	Boolean pauseScene(String localSceneId);

	/**
	 * 取消暂停场景
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 */
	Boolean unpauseScene(String localSceneId);

	/**
	 * create topo
	 *
	 * @param topoData     拓扑数据
	 * @param businessType 业务类型
	 * @return {@code String }
	 */
	String saveTopo(JSONObject topoData, Integer businessType);

	/**
	 * update topo
	 *
	 * @param topoData 拓扑数据
	 * @param topoId   拓扑id
	 * @return topoId
	 */
	String saveTopo(JSONObject topoData, String topoId);

	/**
	 * 设定topo的类型
	 *
	 * @param topoId        拓扑id
	 * @param referenceType 类型
	 * @return 拓扑id
	 */
	String setTopoType(String topoId, Integer referenceType);

	/**
	 * delete mp topo
	 *
	 * @param topoId
	 */
	void deleteTopo(String topoId);

	/**
	 * 重命名中台场景模板
	 *
	 * @param topoId 拓扑id
	 * @param name   名称
	 * @return
	 */
	Boolean renameTopo(String topoId, String name);

	/**
	 * 获取节点计数
	 *
	 * @param localScene 当地场景
	 * @return {@code Integer }
	 */
	Integer getNodeCount(LocalScene localScene);

	/**
	 * 获取节点计数
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Integer }
	 */
	Integer getNodeCount(String localSceneId);

	/**
	 * 获取场景数据
	 *
	 * @param scene  场景
	 * @param userId 用户id
	 * @return {@code JSONObject }
	 */
	JSONObject getSceneData(LocalScene scene, String userId);

	/**
	 * 获取场景数据
	 *
	 * @param mpSceneId 中台场景id
	 * @param userId    用户id
	 * @return {@code JSONObject }
	 */
	JSONObject getSceneData(String mpSceneId, String userId);

	/**
	 * 获取拓扑数据
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject }
	 */
	JSONObject getTopoData(String topoId);

	/**
	 * 执行节点和流量操作前确认场景已开启
	 *
	 * @param localScene 当地场景
	 * @return {@code Boolean }
	 */
	Boolean ensureSceneStarted(LocalScene localScene);

	/**
	 * 确保场景开始
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code Boolean }
	 */
	Boolean ensureSceneStarted(String localSceneId);

	/**
	 * 重启节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 */
	Boolean restartNode(String localSceneId, String nodeId);

	/**
	 * 重新创建节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 */
	Boolean recreateNode(String localSceneId, String nodeId);

	/**
	 * 启动流程
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @param flowId     流id
	 * @return {@code Boolean }
	 */
	Boolean startFlow(LocalScene localScene, String nodeId, String flowId);

	/**
	 * 停止流
	 *
	 * @param localScene 当地场景
	 * @param nodeId     节点id
	 * @param flowId     流id
	 * @return {@code Boolean }
	 */
	Boolean stopFlow(LocalScene localScene, String nodeId, String flowId);

	/**
	 * 获取场景流量映射表
	 *
	 * @param sceneData
	 * @param interactiveType
	 * @return Map<String, SceneFlowContext>
	 */
	Map<String, SceneFlowContext> getSceneFlowList(SceneData sceneData, Integer interactiveType);

	/**
	 * 随机启动一个流量
	 *
	 * @param mpSceneId
	 * @param sceneFlowContextMap
	 * @return Boolean
	 */
	Boolean startRandomFlow(String mpSceneId, Map<String, SceneFlowContext> sceneFlowContextMap);

	/**
	 * 关闭mp场景
	 *
	 * @param scene 场景
	 */
	void deleteRemoteScene(LocalScene scene);

	/**
	 * 获取监控会话
	 *
	 * @param localScene
	 * @param studentId
	 * @return
	 */
	JSONArray getMonitorSessions(LocalScene localScene, String studentId);


	/**
	 * 获取多个节点的主机状态列表
	 *
	 * @param mpSceneId 场景id
	 * @param nodeIds   节点id列表
	 * @return {@code List<MpHostState>}
	 */
	List<MpHostState> getHostStateList(String mpSceneId, List<String> nodeIds);

	/**
	 * 节点快照
	 *
	 * @param localScene
	 * @param context
	 * @return
	 */
	Boolean nodeSnapshot(LocalScene localScene, CreateSnapshotContext context);

	/**
	 * 场景快照历史记录
	 *
	 * @param sceneId
	 * @param imageId
	 * @return
	 */
	JSONArray snapshotHistory(String sceneId, String imageId);

	/**
	 * 场景节点快照合并
	 *
	 * @param sceneId
	 * @param snapshotContext
	 * @return
	 */
	JSONObject mergeSnapshot(String sceneId, MergeSnapshotContext snapshotContext);

	/**
	 * 删除快照
	 *
	 * @param sceneId
	 * @param nodeId
	 * @param snapshotId
	 * @return
	 */
	Boolean deleteSnapshot(String sceneId, String nodeId, String snapshotId);

	/**
	 * 场景暂停
	 *
	 * @param localScene
	 * @return
	 */
	Boolean pauseScene(LocalScene localScene);

	/**
	 * 场景从暂停状态恢复为运行状态
	 *
	 * @param localScene
	 * @return
	 */
	Boolean unpauseScene(LocalScene localScene);

	/**
	 * 创建节点
	 *
	 * @param localSceneId 当地场景id
	 * @param nodeId       节点id
	 * @return {@code Boolean }
	 */
	Boolean createNode(String localSceneId, String nodeId);

	/**
	 * 动态更新节点属性
	 *
	 * @param mpSceneId  场景id
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
	 * @param localScene  当地场景
	 * @param updateScene 场景数据
	 */
	void updateScene(LocalScene localScene, JSONObject updateScene);

	/**
	 * 启动单个节点
	 *
	 * @param mpSceneId 国会议员场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean}
	 */
	Boolean startNode(String mpSceneId, String nodeId);

	/**
	 * 单个节点关机
	 *
	 * @param mpSceneId 国会议员场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean}
	 */
	Boolean stopNode(String mpSceneId, String nodeId);

	/**
	 * 通过场景id重新启动节点
	 *
	 * @param mpSceneId 国会议员场景id
	 * @param nodeId    节点id
	 * @return {@code Boolean}
	 */
	Boolean restartNodeBySceneId(String mpSceneId, String nodeId);

	/**
	 * 通过实例id获取资产列表
	 *
	 * @param businessInstanceId 业务实例id
	 * @param category           类别
	 * @param name               业务实例id
	 * @param realTimeHostState  是否展示实时主机状态
	 * @return {@code JSONArray}
	 */
	JSONArray getAssetListByInstanceId(String businessInstanceId, Integer category, String name,
			Boolean realTimeHostState);

	/**
	 * 通过拓扑id获取资产列表
	 *
	 * @param topoId   拓扑id
	 * @param category 类别
	 * @param name     名称
	 * @return {@code JSONArray}
	 */
	JSONArray getAssetListByTopoId(String topoId, Integer category, String name);

	/**
	 * 获取节点统计数据
	 *
	 * @param localSceneId 当地场景id
	 * @return {@code JSONObject}
	 */
	JSONObject getNodeStatistics(String localSceneId);

	/**
	 * 通过拓扑id获取节点统计数据
	 *
	 * @param topoId 拓扑id
	 * @return {@code JSONObject}
	 */
	JSONObject getNodeStatisticsByTopoId(String topoId);

	/**
	 * 通过业务实例id获取场景id
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code String}
	 */
	String getMpSceneIdByBusinessInstanceId(String businessInstanceId);

	/**
	 * 获取资产列表
	 *
	 * @param req 请求体
	 * @return {@code List<JSONObject>}
	 */
	List<JSONObject> getAssetList(SceneNodeListReq req);

	/**
	 * 通过业务实例id获取所有资产
	 *
	 * @param businessInstanceId 业务实例id
	 * @return {@code List<JSONObject>}
	 */
	List<JSONObject> getAllAssetByBusinessInstanceId(String businessInstanceId);

	/**
	 * 通过mp scene id获取localscene
	 *
	 * @param mpSceneId 议员scenen id
	 * @return {@code LocalScene}
	 */
	LocalScene getOrExceptionByMpSceneId(String mpSceneId);
}
