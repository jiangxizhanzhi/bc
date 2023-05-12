package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.model.scene.*;

/**
 * 场景事件状态代号： http://10.10.10.11/download/attachments/2461959/222.png?version=1&modificationDate=1607060293000&api=v2
 */
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

	JSONObject sceneDump(SceneDumpContext sceneDumpContext);

	/**
	 * 获取流量信息
	 * @param flowId
	 * @return MpFlow
	 */
	MpFlow getFlowById(String flowId);

	/**
	 * 更新节点流量的源IP
	 * @param flowOpsContext
	 * @return Boolean
	 */
	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	/**
	 * 启动流量
	 */
	Boolean startFlow(FlowOpsContext flowOpsContext);

	/**
	 * 停止流量
	 * @param context 
	 * @return
	 */
	Boolean stopFlow(FlowOpsContext context);

	/**
	 * 批量启动流量
	 * @param flowBatchOpsContext
	 * @return Boolean
	 */
	Boolean batchStartFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 批量停止流量
	 * @param flowBatchOpsContext
	 * @return Boolean
	 */
	Boolean batchStopFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 查询场景节点流量执行记录
	 * @param flowRecordContext
	 * @return JSONObject
	 */
	JSONObject getFlowRecords(FlowRecordContext flowRecordContext);

	/**
	 * 获取场景节点会话
	 * @param sceneIds
	 * @return
	 */
	JSONArray getNodeSessions(List<String> sceneIds);

	/**
	 * 获取场景场景监控和远程协助地址
	 * @param sceneIds 场景编号
	 * @param nodeIds 节点编号
	 * @param userIds 用户编号
	 * @param type 类型 all=监控和远程协助都获取 assistance=远程协助地址
	 * @return
	 */
	JSONArray getNodeSessions(List<String> sceneIds, List<String> nodeIds, List<String> userIds,
			String type);

	/**
	 * 获取所有远程协助列表
	 * @param sceneIds
	 * @param nodeIds
	 * @return key=userId value=地址
	 */
	Map<String, String> getAssistanceMap(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds);

	/**
	 * 获取场景监控会话
	 * @param mpSceneId
	 * @param studentId
	 * @return
	 */
	JSONArray getMonitorSessions(String mpSceneId, String studentId);

	/**
	 * 获取流量日志
	 * @param flowLogsPageContext
	 * @return JSONObject
	 */
	JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext);

	/**
	 * 获取主机状态
	 * @param elementCommonContext
	 * @return List<MpHostState>
	 */
	List<MpHostState> getHostState(ElementCommonContext elementCommonContext);

	/**
	 * 获取流量趋势
	 * @param elementCommonContext
	 * @return List<MpFlowTrend>
	 */
	List<MpFlowTrend> getFlowTrend(ElementCommonContext elementCommonContext);

	/**
	 * 获取事件类型
	 * @param elementCommonContext
	 * @return MpEventAggr
	 */
	MpEventAggr getEventAggr(ElementCommonContext elementCommonContext);

	/**
	 * 获取风险统计
	 * @param elementCommonContext
	 * @return MpRiskCount
	 */
	MpRiskCount getRiskCount(ElementCommonContext elementCommonContext);

	/**
	 * 获取探针采集信息（分页）
	 * @param collectionEventPageContext
	 * @return MpCollectionEvent
	 */
	List<MpCollectionEvent>
			getCollectionEvent(CollectionEventPageContext collectionEventPageContext);

	/**
	 * 场景节点打快照
	 * @param snapshotContext
	 * @return
	 */
	Boolean nodeSnapshot(NodeSnapshotContext snapshotContext);

	/**
	 * 快照历史记录
	 * @param imageId
	 * @return
	 */
	JSONArray snapshotHistory(String imageId);

	/**
	 * 快照合并
	 * @param mergeSnapshotContext
	 * @return
	 */
	JSONObject mergeSnapshot(MergeSnapshotContext mergeSnapshotContext);

	/**
	 * 快照删除
	 * @param deleteContext
	 * @return
	 */
	Boolean deleteSnapshot(SnapshotDeleteContext deleteContext);

	/**
	 * 获取场景下节点及链路状态
	 * @param mpSceneId
	 * @return JSONObject
	 */
	List<MpNodeStatus> getConnectionStatus(String mpSceneId);

	void replaceAccessIpExe(JSONObject elementPorts);

	Boolean createNode(NodeOpsContext nodeOpsContext);

	/**
	 * 动态更新节点属性
	 * @param context
	 * @return
	 */
	JSONObject updateNode(NodeUpdateContext context);

	/**
	 * 删除节点
	 *
	 * @param context 上下文
	 * @return {@code JSONObject}
	 */
	JSONObject deleteNode(NodeOpsContext context);

	/**
	 * 动态更新场景
	 *
	 * @param context 上下文
	 * @return {@code JSONObject}
	 */
	JSONObject updateScene(SceneUpdateContext context);
}
