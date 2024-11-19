package com.cyberpeace.integratation.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.model.scene.*;

/**
 * 场景事件状态代号： http://10.10.10.11/download/attachments/2461959/222.png?version=1&modificationDate=1607060293000&api=v2
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface RemoteSceneService {

	/**
	 * 启动前台场景
	 *
	 * @param sceneStartContext 场景启动上下文
	 * @return {@code String }
	 * @throws
	 * @see SceneStartContext
	 * @see String
	 */
	String startScene(SceneStartContext sceneStartContext);

	/**
	 * 启动后台测试场景
	 *
	 * @param sceneStartContext 场景启动上下文
	 * @return {@code String }
	 * @throws
	 * @see SceneStartContext
	 * @see String
	 */
	String testScene(SceneStartContext sceneStartContext);

	/**
	 * 停止场景
	 *
	 * @param sceneOpsContext 场景操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see SceneOpsContext
	 * @see Boolean
	 */
	Boolean stopScene(SceneOpsContext sceneOpsContext);

	/**
	 * 暂停场景
	 *
	 * @param sceneOpsContext 场景操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see SceneOpsContext
	 * @see Boolean
	 */
	Boolean pauseScene(SceneOpsContext sceneOpsContext);

	/**
	 * 获取由id
	 *
	 * @param sceneId 场景id
	 * @param userId  用户id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getById(String sceneId, String userId);

	/**
	 * 获取由id和面积idfor3d
	 *
	 * @param sceneId 场景id
	 * @param areaId  面积id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getByIdAndAreaIdFor3D(String sceneId, String areaId);

	/**
	 * 取消暂停场景
	 *
	 * @param sceneOpContext 场景OP上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see SceneOpsContext
	 * @see Boolean
	 */
	Boolean unpauseScene(SceneOpsContext sceneOpContext);

	/**
	 * 重启节点
	 *
	 * @param nodeOpsContext 节点操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see NodeOpsContext
	 * @see Boolean
	 */
	Boolean restartNode(NodeOpsContext nodeOpsContext);

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsContext 节点操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see NodeOpsContext
	 * @see Boolean
	 */
	Boolean recreateNode(NodeOpsContext nodeOpsContext);

	/**
	 * 场景导出
	 *
	 * @param sceneDumpContext 场景导出上下文
	 * @return {@code JSONObject }
	 * @throws
	 * @see SceneDumpContext
	 * @see JSONObject
	 */
	JSONObject sceneDump(SceneDumpContext sceneDumpContext);

	/**
	 * 获取流量信息
	 *
	 * @param flowId
	 * @return MpFlow
	 * @throws
	 * @see String
	 * @see MpFlow
	 */
	MpFlow getFlowById(String flowId);

	/**
	 * 更新节点流量的源IP
	 *
	 * @param flowOpsContext
	 * @return Boolean
	 * @throws
	 * @see FlowOpsContext
	 * @see Boolean
	 */
	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	/**
	 * 启动流量
	 *
	 * @param flowOpsContext 流量操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see FlowOpsContext
	 * @see Boolean
	 */
	Boolean startFlow(FlowOpsContext flowOpsContext);

	/**
	 * 停止流量
	 *
	 * @param context
	 * @return
	 * @throws
	 * @see FlowOpsContext
	 * @see Boolean
	 */
	Boolean stopFlow(FlowOpsContext context);

	/**
	 * 批量启动流量
	 *
	 * @param flowBatchOpsContext
	 * @return Boolean
	 * @throws
	 * @see FlowBatchOpsContext
	 * @see Boolean
	 */
	Boolean batchStartFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 批量停止流量
	 *
	 * @param flowBatchOpsContext
	 * @return Boolean
	 * @throws
	 * @see FlowBatchOpsContext
	 * @see Boolean
	 */
	Boolean batchStopFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 查询场景节点流量执行记录
	 *
	 * @param flowRecordContext
	 * @return JSONObject
	 * @throws
	 * @see FlowRecordContext
	 * @see JSONObject
	 */
	JSONObject getFlowRecords(FlowRecordContext flowRecordContext);

	/**
	 * 获取场景节点会话
	 *
	 * @param sceneIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see JSONArray
	 */
	JSONArray getNodeSessions(List<String> sceneIds);

	/**
	 * 获取场景场景监控和远程协助地址
	 *
	 * @param sceneIds 场景编号
	 * @param nodeIds  节点编号
	 * @param userIds  用户编号
	 * @param type     类型 all=监控和远程协助都获取 assistance=远程协助地址
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see List<String>
	 * @see String
	 * @see JSONArray
	 */
	JSONArray getNodeSessions(List<String> sceneIds, List<String> nodeIds, List<String> userIds,
			String type);

	/**
	 * 获取所有远程协助列表
	 *
	 * @param sceneIds
	 * @param nodeIds
	 * @param userIds  用户id列表
	 * @return key=userId value=地址
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see List<String>
	 * @see Map<String, String>
	 */
	Map<String, String> getAssistanceMap(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds);

	/**
	 * 获取场景监控会话
	 *
	 * @param mpSceneId
	 * @param studentId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONArray
	 */
	JSONArray getMonitorSessions(String mpSceneId, String studentId);

	/**
	 * 获取流量日志
	 *
	 * @param flowLogsPageContext
	 * @return JSONObject
	 * @throws
	 * @see FlowLogsPageContext
	 * @see JSONObject
	 */
	JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext);

	/**
	 * 获取主机状态
	 *
	 * @param elementCommonContext
	 * @return List<MpHostState>
	 * @throws
	 * @see ElementCommonContext
	 * @see List<MpHostState>
	 */
	List<MpHostState> getHostState(ElementCommonContext elementCommonContext);

	/**
	 * 获取流量趋势
	 *
	 * @param elementCommonContext
	 * @return List<MpFlowTrend>
	 * @throws
	 * @see ElementCommonContext
	 * @see List<MpFlowTrend>
	 */
	List<MpFlowTrend> getFlowTrend(ElementCommonContext elementCommonContext);

	/**
	 * 获取事件类型
	 *
	 * @param elementCommonContext
	 * @return MpEventAggr
	 * @throws
	 * @see ElementCommonContext
	 * @see MpEventAggr
	 */
	MpEventAggr getEventAggr(ElementCommonContext elementCommonContext);

	/**
	 * 获取风险统计
	 *
	 * @param elementCommonContext
	 * @return MpRiskCount
	 * @throws
	 * @see ElementCommonContext
	 * @see MpRiskCount
	 */
	MpRiskCount getRiskCount(ElementCommonContext elementCommonContext);

	/**
	 * 获取探针采集信息（分页）
	 *
	 * @param collectionEventPageContext
	 * @return MpCollectionEvent
	 * @throws
	 * @see CollectionEventPageContext
	 * @see List<MpCollectionEvent>
	 */
	List<MpCollectionEvent>
			getCollectionEvent(CollectionEventPageContext collectionEventPageContext);

	/**
	 * 场景节点打快照
	 *
	 * @param snapshotContext
	 * @return
	 * @throws
	 * @see NodeSnapshotContext
	 * @see Boolean
	 */
	Boolean nodeSnapshot(NodeSnapshotContext snapshotContext);

	/**
	 * 快照历史记录
	 *
	 * @param imageId
	 * @return
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	JSONArray snapshotHistory(String imageId);

	/**
	 * 快照合并
	 *
	 * @param mergeSnapshotContext
	 * @return
	 * @throws
	 * @see MergeSnapshotContext
	 * @see JSONObject
	 */
	JSONObject mergeSnapshot(MergeSnapshotContext mergeSnapshotContext);

	/**
	 * 快照删除
	 *
	 * @param deleteContext
	 * @return
	 * @throws
	 * @see SnapshotDeleteContext
	 * @see Boolean
	 */
	Boolean deleteSnapshot(SnapshotDeleteContext deleteContext);

	/**
	 * 获取场景下节点及链路状态
	 *
	 * @param mpSceneId
	 * @return JSONObject
	 * @throws
	 * @see String
	 * @see List<MpNodeStatus>
	 */
	List<MpNodeStatus> getConnectionStatus(String mpSceneId);

	/**
	 * 取代访问ip地址exe
	 *
	 * @param elementPorts Element 端口
	 * @throws
	 * @see JSONObject
	 */
	void replaceAccessIpExe(JSONObject elementPorts);

	/**
	 * 创建节点
	 *
	 * @param nodeOpsContext 节点操作上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see NodeOpsContext
	 * @see Boolean
	 */
	Boolean createNode(NodeOpsContext nodeOpsContext);

	/**
	 * 动态更新节点属性
	 *
	 * @param context
	 * @return
	 * @throws
	 * @see NodeUpdateContext
	 * @see JSONObject
	 */
	JSONObject updateNode(NodeUpdateContext context);

	/**
	 * 删除节点
	 *
	 * @param context 上下文
	 * @return {@code JSONObject}
	 * @throws
	 * @see NodeOpsContext
	 * @see JSONObject
	 */
	JSONObject deleteNode(NodeOpsContext context);

	/**
	 * 动态更新场景
	 *
	 * @param context 上下文
	 * @return {@code JSONObject}
	 * @throws
	 * @see SceneUpdateContext
	 * @see JSONObject
	 */
	JSONObject updateScene(SceneUpdateContext context);

	/**
	 * 启动单个节点
	 *
	 * @param context 上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see NodeOpsContext
	 * @see Boolean
	 */
	Boolean startNode(NodeOpsContext context);

	/**
	 * 单个节点关机
	 *
	 * @param context 上下文
	 * @return {@code Boolean}
	 * @throws
	 * @see NodeOpsContext
	 * @see Boolean
	 */
	Boolean stopNode(NodeOpsContext context);

	/**
	 * 获取节点列表
	 *
	 * @param req 请求体
	 * @return {@code JSONArray}
	 * @throws
	 * @see SceneNodeListReq
	 * @see JSONArray
	 */
	JSONArray getAssetList(SceneNodeListReq req);

	/**
	 * 通过场景id获取资产统计数据
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONObject}
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getAssetsStatisticsBySceneId(String sceneId);


	/**
	 * 屏幕录制
	 *
	 * @param sceneId 场景id
	 * @param nodeId  节点id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
	JSONObject screenrecord(String sceneId,String nodeId);

	/**
	 * 攻击类型计数
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	JSONArray attackTypeCount(String mpSceneId);

	/**
	 * 场景趋势
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	JSONArray sceneTrend(String mpSceneId);

	/**
	 * 获取事件总数计数
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONObject }
	 * @throws
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getEventTotalCount(String sceneId);

	/**
	 * 获取攻击类型计数组
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	JSONArray getAttackTypeCountGroup(String sceneId);

	/**
	 * 获取事件特 伦 特计数
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONArray }
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	JSONArray getEventTrentCount(String sceneId);

	/**
	 * 获取事件时间列表
	 *
	 * @param sceneEventContext 场景事件上下文
	 * @return {@code JSONArray }
	 * @throws
	 * @see SceneEventContext
	 * @see JSONArray
	 */
	JSONArray getEventTimeList(SceneEventContext sceneEventContext);

	/**
	 * 获取事件列表
	 *
	 * @param sceneEventContext 场景事件上下文
	 * @return {@code JSONArray }
	 * @throws
	 * @see SceneEventContext
	 * @see JSONArray
	 */
	JSONArray getEventList(SceneEventContext sceneEventContext);

	/**
	 * 获取主机事件列表
	 *
	 * @param req 请求体
	 * @return {@code List<MpHostEvent> }
	 * @throws
	 * @see SceneEventHostListReq
	 * @see List<MpHostEvent>
	 */
	List<MpHostEvent> getHostEventList(SceneEventHostListReq req);

	/**
	 * 获取层节点数据
	 *
	 * @param sceneId 场景id
	 * @param areaId
	 * @return {@code JSONObject}
	 * @throws
	 * @see String
	 * @see String
	 * @see JSONObject
	 */
	JSONObject getByArea(String sceneId, String areaId);
}
