/**
 * 这段代码定义了一个名为 `RemoteSceneService` 的接口，该接口包含多个方法用于管理和操作与场景相关的各种功能。以下是对这些方法的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.integratation.service` 
 * - **导入的类**：包括阿里巴巴的 FastJSON 库、自定义的模型类等。 
 * ### 主要方法 
 * 1. **启动和停止场景** 
 * - `startScene(SceneStartContext)` 
 * - `testScene(SceneStartContext)` 
 * - `stopScene(SceneOpsContext)` 
 * - `pauseScene(SceneOpsContext)` 
 * - `unpauseScene(SceneOpsContext)` 
 * 2. **场景数据管理** 
 * - `getById(String, String)` 
 * - `sceneDump(SceneDumpContext)` 
 * - `getFlowById(String)` 
 * - `updateFlowParam(FlowOpsContext)` 
 * - `startFlow(FlowOpsContext)` 
 * - `stopFlow(FlowOpsContext)` 
 * - `batchStartFlow(FlowBatchOpsContext)` 
 * - `batchStopFlow(FlowBatchOpsContext)` 
 * - `getFlowRecords(FlowRecordContext)` 
 * - `getNodeSessions(List<String>)` 
 * - `getNodeSessions(List<String>, List<String>, List<String>, String)` 
 * - `getAssistanceMap(List<String>, List<String>, List<String>)` 
 * - `getMonitorSessions(String, String)` 
 * - `getFlowLogs(FlowLogsPageContext)` 
 * - `getHostState(ElementCommonContext)` 
 * - `getFlowTrend(ElementCommonContext)` 
 * - `getEventAggr(ElementCommonContext)` 
 * - `getRiskCount(ElementCommonContext)` 
 * - `getCollectionEvent(CollectionEventPageContext)` 
 * - `nodeSnapshot(NodeSnapshotContext)` 
 * - `snapshotHistory(String)` 
 * - `mergeSnapshot(MergeSnapshotContext)` 
 * - `deleteSnapshot(SnapshotDeleteContext)` 
 * - `getConnectionStatus(String)` 
 * - `replaceAccessIpExe(JSONObject)` 
 * - `createNode(NodeOpsContext)` 
 * - `updateNode(NodeUpdateContext)` 
 * - `deleteNode(NodeOpsContext)` 
 * - `updateScene(SceneUpdateContext)` 
 * - `startNode(NodeOpsContext)` 
 * - `stopNode(NodeOpsContext)` 
 * - `getAssetList(SceneNodeListReq)` 
 * - `getAssetsStatisticsBySceneId(String)` 
 * - `screenrecord(String, String)` 
 * - `attackTypeCount(String)` 
 * - `sceneTrend(String)` 
 * ### 方法功能描述 
 * - **场景管理**：提供启动、停止、暂停、恢复、获取特定场景信息等功能。 
 * - **流量管理**：管理流量的启动、停止、批量操作以及获取流量相关记录。 
 * - **节点管理**：包括创建、更新、删除、启动、停止节点以及获取节点列表和状态。 
 * - **数据获取**：如获取主机状态、流量趋势、事件类型、风险统计等。 
 * - **快照管理**：包括创建、获取快照历史记录、合并和删除快照。 
 * - **辅助功能**：如屏幕录制、攻击类型计数、场景趋势等。 
 * ### 总结 
 * 该接口主要涉及场景的生命周期管理、流量控制、节点操作、数据获取及辅助功能。每个方法都提供了特定的功能，以满足对复杂场景的管理和操作需求。通过这些方法，可以实现对场景中各个组件的全面控制和监控。 
**/
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
 * @date 2024/11/08
 */
public interface RemoteSceneService {

	/**
	 * 启动前台场景
	 *
	 * @param sceneStartContext 场景开始上下文
	 * @return {@code String }
	 */
	String startScene(SceneStartContext sceneStartContext);

	/**
	 * 启动后台测试场景
	 *
	 * @param sceneStartContext 场景开始上下文
	 * @return {@code String }
	 */
	String testScene(SceneStartContext sceneStartContext);

	/**
	 * 停场景
	 *
	 * @param sceneOpsContext 场景老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	Boolean stopScene(SceneOpsContext sceneOpsContext);

	/**
	 * 暂停场景
	 *
	 * @param sceneOpsContext 场景老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	Boolean pauseScene(SceneOpsContext sceneOpsContext);

	/**
	 * 获取由id
	 *
	 * @param sceneId 场景id
	 * @param userId  用户id
	 * @return {@code JSONObject }
	 */
	JSONObject getById(String sceneId, String userId);

	/**
	 * 取消暂停场景
	 *
	 * @param sceneOpContext 场景OP上下文
	 * @return {@code Boolean }
	 */
	Boolean unpauseScene(SceneOpsContext sceneOpContext);

	/**
	 * 重启节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	Boolean restartNode(NodeOpsContext nodeOpsContext);

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	Boolean recreateNode(NodeOpsContext nodeOpsContext);

	/**
	 * 场景转 储
	 *
	 * @param sceneDumpContext 场景转 储上下文
	 * @return {@code JSONObject }
	 */
	JSONObject sceneDump(SceneDumpContext sceneDumpContext);

	/**
	 * 获取流量信息
	 *
	 * @param flowId
	 * @return MpFlow
	 */
	MpFlow getFlowById(String flowId);

	/**
	 * 更新节点流量的源IP
	 *
	 * @param flowOpsContext
	 * @return Boolean
	 */
	Boolean updateFlowParam(FlowOpsContext flowOpsContext);

	/**
	 * 启动流量
	 *
	 * @param flowOpsContext 流操作上下文
	 * @return {@code Boolean }
	 */
	Boolean startFlow(FlowOpsContext flowOpsContext);

	/**
	 * 停止流量
	 *
	 * @param context
	 * @return
	 */
	Boolean stopFlow(FlowOpsContext context);

	/**
	 * 批量启动流量
	 *
	 * @param flowBatchOpsContext
	 * @return Boolean
	 */
	Boolean batchStartFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 批量停止流量
	 *
	 * @param flowBatchOpsContext
	 * @return Boolean
	 */
	Boolean batchStopFlow(FlowBatchOpsContext flowBatchOpsContext);

	/**
	 * 查询场景节点流量执行记录
	 *
	 * @param flowRecordContext
	 * @return JSONObject
	 */
	JSONObject getFlowRecords(FlowRecordContext flowRecordContext);

	/**
	 * 获取场景节点会话
	 *
	 * @param sceneIds
	 * @return
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
	 */
	Map<String, String> getAssistanceMap(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds);

	/**
	 * 获取场景监控会话
	 *
	 * @param mpSceneId
	 * @param studentId
	 * @return
	 */
	JSONArray getMonitorSessions(String mpSceneId, String studentId);

	/**
	 * 获取流量日志
	 *
	 * @param flowLogsPageContext
	 * @return JSONObject
	 */
	JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext);

	/**
	 * 获取主机状态
	 *
	 * @param elementCommonContext
	 * @return List<MpHostState>
	 */
	List<MpHostState> getHostState(ElementCommonContext elementCommonContext);

	/**
	 * 获取流量趋势
	 *
	 * @param elementCommonContext
	 * @return List<MpFlowTrend>
	 */
	List<MpFlowTrend> getFlowTrend(ElementCommonContext elementCommonContext);

	/**
	 * 获取事件类型
	 *
	 * @param elementCommonContext
	 * @return MpEventAggr
	 */
	MpEventAggr getEventAggr(ElementCommonContext elementCommonContext);

	/**
	 * 获取风险统计
	 *
	 * @param elementCommonContext
	 * @return MpRiskCount
	 */
	MpRiskCount getRiskCount(ElementCommonContext elementCommonContext);

	/**
	 * 获取探针采集信息（分页）
	 *
	 * @param collectionEventPageContext
	 * @return MpCollectionEvent
	 */
	List<MpCollectionEvent>
			getCollectionEvent(CollectionEventPageContext collectionEventPageContext);

	/**
	 * 场景节点打快照
	 *
	 * @param snapshotContext
	 * @return
	 */
	Boolean nodeSnapshot(NodeSnapshotContext snapshotContext);

	/**
	 * 快照历史记录
	 *
	 * @param imageId
	 * @return
	 */
	JSONArray snapshotHistory(String imageId);

	/**
	 * 快照合并
	 *
	 * @param mergeSnapshotContext
	 * @return
	 */
	JSONObject mergeSnapshot(MergeSnapshotContext mergeSnapshotContext);

	/**
	 * 快照删除
	 *
	 * @param deleteContext
	 * @return
	 */
	Boolean deleteSnapshot(SnapshotDeleteContext deleteContext);

	/**
	 * 获取场景下节点及链路状态
	 *
	 * @param mpSceneId
	 * @return JSONObject
	 */
	List<MpNodeStatus> getConnectionStatus(String mpSceneId);

	/**
	 * 取代访问ip地址exe
	 *
	 * @param elementPorts Element 端口
	 */
	void replaceAccessIpExe(JSONObject elementPorts);

	/**
	 * 创建节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	Boolean createNode(NodeOpsContext nodeOpsContext);

	/**
	 * 动态更新节点属性
	 *
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

	/**
	 * 启动单个节点
	 *
	 * @param context 上下文
	 * @return {@code Boolean }
	 */
	Boolean startNode(NodeOpsContext context);

	/**
	 * 单个节点关机
	 *
	 * @param context 上下文
	 * @return {@code Boolean}
	 */
	Boolean stopNode(NodeOpsContext context);

	/**
	 * 获取节点列表
	 *
	 * @param req 请求体
	 * @return {@code JSONArray}
	 */
	JSONArray getAssetList(SceneNodeListReq req);

	/**
	 * 通过场景id获取资产统计数据
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONObject}
	 */
	JSONObject getAssetsStatisticsBySceneId(String sceneId);


	/**
	 * 屏幕录制
	 *
	 * @param sceneId 场景id
	 * @param nodeId  节点id
	 * @return {@code JSONObject }
	 */
	JSONObject screenrecord(String sceneId,String nodeId);

	/**
	 * 攻击类型计数
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 */
	JSONArray attackTypeCount(String mpSceneId);

	/**
	 * 场景趋势
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 */
	JSONArray sceneTrend(String mpSceneId);
}
