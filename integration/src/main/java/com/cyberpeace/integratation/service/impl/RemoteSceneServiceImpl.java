package com.cyberpeace.integratation.service.impl;

import static com.cyberpeace.common.constant.WebConstant.SCENE_ID_LENGTH;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.constant.SessionType;
import com.cyberpeace.integratation.model.mp.*;
import com.cyberpeace.integratation.model.scene.*;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程场景Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码是一个Spring服务类 `RemoteSceneServiceImpl` 的实现，用于处理与远程场景相关的各种操作。以下是对其主要功能和结构的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.integratation.service.impl` 
 * - **类名**: `RemoteSceneServiceImpl` 
 * - **注解**: 
 * - `@Slf4j`: 为类添加日志记录功能。 
 * - `@Service`: 将类标识为Spring服务组件。 
 * ### 成员变量 
 * - 定义了多个URL常量，例如场景开始、暂停、停止等操作的URL。 
 * - `MpEnvService mpEnvService`: 通过自动注入依赖，用于获取中台环境的主机信息。 
 * ### 初始化方法 (`init`) 
 * - 在类实例化后执行，初始化所有URL常量，将基础URL（从 `mpEnvService` 获取）与各个操作路径组合成完整的API URL。 
 * ### 主要方法 
 * 1. **场景操作** 
 * - `startScene`: 开始场景。 
 * - `testScene`: 测试场景。 
 * - `stopScene`: 停止场景。 
 * - `pauseScene`: 暂停场景。 
 * - `unpauseScene`: 取消暂停场景。 
 * - `updateScene`: 更新场景。 
 * 2. **节点操作** 
 * - `createNode`: 创建节点。 
 * - `updateNode`: 更新节点。 
 * - `deleteNode`: 删除节点。 
 * - `startNode`: 启动节点。 
 * - `stopNode`: 停止节点。 
 * - `restartNode`: 重启节点。 
 * - `recreateNode`: 重新创建节点。 
 * - `nodeSnapshot`: 创建节点快照。 
 * - `snapshotHistory`: 查看快照历史记录。 
 * - `mergeSnapshot`: 合并快照。 
 * - `deleteSnapshot`: 删除快照。 
 * 3. **流操作** 
 * - `startFlow`: 启动流。 
 * - `stopFlow`: 停止流。 
 * - `updateFlowParam`: 更新流参数。 
 * - `batchStartFlow`: 批量启动流。 
 * - `batchStopFlow`: 批量停止流。 
 * - `getFlowLogs`: 获取流日志。 
 * - `getFlowTrend`: 获取流趋势。 
 * - `getFlowRecords`: 获取流记录。 
 * 4. **其他操作** 
 * - `getById`: 获取场景详情。 
 * - `getAssetsStatisticsBySceneId`: 获取场景资产统计数据。 
 * - `screenrecord`: 获取屏幕录制。 
 * - `attackTypeCount`: 获取攻击类型计数。 
 * - `sceneTrend`: 获取场景趋势。 
 * - `getNodeSessions`: 获取节点会话。 
 * - `getAssistanceMap`: 获取援助地图。 
 * - `getMonitorSessions`: 获取监控会话。 
 * - `getHostState`: 获取主机状态。 
 * - `getEventAggr`: 获取事件聚合。 
 * - `getRiskCount`: 获取风险计数。 
 * - `getCollectionEvent`: 获取收集事件。 
 * - `getConnectionStatus`: 获取连接状态。 
 * - `replaceAccessIp`: 替换访问IP地址。 
 * - `replaceAccessIpExe`: 替换访问IP地址的实现。 
 * ### 技术细节 
 * - 使用了 `HttpClientUtil` 和 `ConfigUtil` 等工具类进行HTTP请求和配置读取。 
 * - 使用了 `fastjson` 进行JSON的序列化和反序列化。 
 * - 使用了 `StreamApiUtils` 工具类进行集合操作，如过滤和去重。 
 * ### 总结 
 * 这个类提供了丰富的API来操作远程场景及其相关资源，如场景、节点、流等。它通过HTTP请求与外部系统进行交互，并处理响应数据。每个方法都有详细的日志记录，确保操作过程的可追踪性和可调试性。 
**/
@Slf4j
@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 场景开始网址
	 */
	private String SCENE_START_URL;

	/**
	 * 场景开始测试网址
	 */
	private String SCENE_START_TEST_URL;

	/**
	 * 场景删除网址
	 */
	private String SCENE_DELETE_URL;

	/**
	 * 场景清楚网址
	 */
	private String SCENE_CLEAR_URL;

	/**
	 * 场景取回
	 */
	private String SCENE_RETRIEVE;

	/**
	 * 场景暂停网址
	 */
	private String SCENE_PAUSE_URL;

	/**
	 * 场景取消暂停网址
	 */
	private String SCENE_UNPAUSE_URL;

	/**
	 * 节点创建 URL
	 */
	private String NODE_CREATE_URL;

	/**
	 * 节点重启 URL
	 */
	private String NODE_RESTART_URL;

	/**
	 * 节点重新创建 URL
	 */
	private String NODE_RECREATE_URL;

	/**
	 * 流启动 URL
	 */
	private String FLOW_START_URL;

	/**
	 * 流停止 URL
	 */
	private String FLOW_STOP_URL;

	/**
	 * 流更新参数 URL
	 */
	private String FLOW_UPDATE_PARAM_URL;

	/**
	 * 流批量开始网址
	 */
	private String FLOW_BATCH_START_URL;

	/**
	 * 流批量停网址
	 */
	private String FLOW_BATCH_STOP_URL;

	/**
	 * 节点会话 URL
	 */
	private String NODE_SESSION_URL;

	/**
	 * 流日志 URL
	 */
	private String FLOW_LOG_URL;

	/**
	 * 主机状态 URL
	 */
	private String HOST_STATE_URL;

	/**
	 * 流趋势 URL
	 */
	private String FLOW_TREND_URL;

	/**
	 * 事件聚合 URL
	 */
	private String EVENT_AGGR_URL;

	/**
	 * 风险计数网址
	 */
	private String RISK_COUNT_URL;

	/**
	 * 集合事件 URL
	 */
	private String COLLECTION_EVENT_URL;

	/**
	 * 收集事件数据 URL
	 */
	private String COLLECTION_EVENT_DATA_URL;

	/**
	 * 流记录 URL
	 */
	private String FLOW_RECORD_URL;

	/**
	 * 流检索 URL
	 */
	private String FLOW_RETRIEVE_URL;

	/**
	 * 节点快照 URL
	 */
	private String NODE_SNAPSHOT_URL;

	/**
	 * 快照历史记录 URL
	 */
	private String SNAPSHOT_HISTORY_URL;

	/**
	 * 快照合并 URL
	 */
	private String SNAPSHOT_MERGE_URL;

	/**
	 * 快照删除 URL
	 */
	private String SNAPSHOT_DELETE_URL;

	/**
	 * 连接状态网址
	 */
	private String CONNECTION_STATUS_URL;

	/**
	 * 场景转 储网址
	 */
	private String SCENE_DUMP_URL;

	/**
	 * 节点更新 URL
	 */
	private String NODE_UPDATE_URL;

	/**
	 * 节点删除 URL
	 */
	private String NODE_DELETE_URL;

	/**
	 * 场景更新网址
	 */
	private String SCENE_UPDATE_URL;

	/**
	 * 节点起始 URL
	 */
	private String NODE_START_URL;

	/**
	 * 节点停止 URL
	 */
	private String NODE_STOP_URL;

	/**
	 * 场景节点列表
	 */
	private String SCENE_NODE_LIST;

	/**
	 * 场景节点统计学
	 */
	private String SCENE_NODE_STATISTICS;

	/**
	 * 节点屏幕记录
	 */
	private String NODE_SCREEN_RECORD;

	/**
	 * 攻击类型计数
	 */
	private String ATTACK_TYPE_COUNT;

	/**
	 * 场景趋势
	 */
	private String SCENE_TREND;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String MP_HOST = mpEnvService.getMpHost();
		SCENE_START_URL = MP_HOST + ConfigUtil.getContextProperty("scene.start.app");

		SCENE_START_TEST_URL = MP_HOST + ConfigUtil.getContextProperty("scene.start.test");

		SCENE_DELETE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.delete");

		SCENE_CLEAR_URL = MP_HOST + ConfigUtil.getContextProperty("scene.clear");

		SCENE_RETRIEVE = MP_HOST + ConfigUtil.getContextProperty("scene.retrieve");

		SCENE_PAUSE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.pause");

		SCENE_UNPAUSE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.unpause");

		SCENE_DUMP_URL = MP_HOST + ConfigUtil.getContextProperty("mp.scene.dump");

		NODE_CREATE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.create");

		NODE_RESTART_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.restart");

		NODE_RECREATE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.recreate");

		FLOW_START_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.flow.start");

		FLOW_STOP_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.flow.stop");

		FLOW_UPDATE_PARAM_URL = MP_HOST
				+ ConfigUtil.getContextProperty("scene.node.flow.param.update");

		FLOW_BATCH_START_URL = MP_HOST
				+ ConfigUtil.getContextProperty("scene.node.flow.batch.start");

		FLOW_BATCH_STOP_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.flow.batch.stop");

		NODE_SESSION_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.session");

		FLOW_LOG_URL = MP_HOST + ConfigUtil.getContextProperty("scene.flow.log");

		HOST_STATE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.host.state");

		FLOW_TREND_URL = MP_HOST + ConfigUtil.getContextProperty("scene.flow.trend");

		EVENT_AGGR_URL = MP_HOST + ConfigUtil.getContextProperty("scene.event.aggr");

		RISK_COUNT_URL = MP_HOST + ConfigUtil.getContextProperty("scene.risk.count");

		COLLECTION_EVENT_URL = MP_HOST
				+ ConfigUtil.getContextProperty("scene.probe.collection.event");

		COLLECTION_EVENT_DATA_URL = MP_HOST
				+ ConfigUtil.getContextProperty("scene.probe.collection.data");

		FLOW_RECORD_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.flow.record");

		FLOW_RETRIEVE_URL = MP_HOST + ConfigUtil.getContextProperty("mp.flow.retrieve");

		NODE_SNAPSHOT_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.snapshot");
		SNAPSHOT_HISTORY_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.snapshot.history");
		SNAPSHOT_MERGE_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.snapshot.merge");
		SNAPSHOT_DELETE_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.snapshot.delete");

		CONNECTION_STATUS_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.connection.status");

		NODE_UPDATE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.modify");

		NODE_DELETE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.node.delete");

		SCENE_UPDATE_URL = MP_HOST + ConfigUtil.getContextProperty("scene.update");

		NODE_START_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.start");

		NODE_STOP_URL = MP_HOST + ConfigUtil.getContextProperty("mp.node.stop");

		SCENE_NODE_LIST = MP_HOST + ConfigUtil.getContextProperty("scene.node.list");

		SCENE_NODE_STATISTICS = MP_HOST + ConfigUtil.getContextProperty("scene.node.statistics");

		NODE_SCREEN_RECORD = MP_HOST + ConfigUtil.getContextProperty("mp.screen.record");

		ATTACK_TYPE_COUNT = MP_HOST + ConfigUtil.getContextProperty("mp.attack.type.count");

		SCENE_TREND = MP_HOST + ConfigUtil.getContextProperty("mp.scene.trend");
	}

	/**
	 * 开始场景
	 *
	 * @param sceneStartContext 场景开始上下文
	 * @return {@code String }
	 */
	@Override
	public String startScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_URL);
	}

	/**
	 * 测试场景
	 *
	 * @param sceneStartContext 场景开始上下文
	 * @return {@code String }
	 */
	@Override
	public String testScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_TEST_URL);
	}

	/**
	 * 停场景
	 *
	 * @param sceneOpsContext 场景老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopScene(SceneOpsContext sceneOpsContext) {
		String targetUrl = SCENE_DELETE_URL;

		return HttpClientUtil.delete(targetUrl, sceneOpsContext, Boolean.class);
	}

	/**
	 * 暂停场景
	 *
	 * @param sceneOpsContext 场景老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean pauseScene(SceneOpsContext sceneOpsContext) {
		return HttpClientUtil.post(SCENE_PAUSE_URL, sceneOpsContext, Boolean.class);
	}

	/**
	 * 获取由id
	 *
	 * @param sceneId 场景id
	 * @param userId  用户id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getById(String sceneId, String userId) {
		String targetUrl = SCENE_RETRIEVE + "?id=" + sceneId + "&accessUserId=" + userId;
		return replaceAccessIp(HttpClientUtil.rawGet(targetUrl, JSONObject.class));

	}

	/**
	 * 取消暂停场景
	 *
	 * @param sceneOpContext 场景OP上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean unpauseScene(SceneOpsContext sceneOpContext) {
		return HttpClientUtil.post(SCENE_UNPAUSE_URL, sceneOpContext, Boolean.class);

	}

	/**
	 * 创建节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean createNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_CREATE_URL, nodeOpsContext, Boolean.class);
	}

	/**
	 * 更新节点
	 *
	 * @param context 上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject updateNode(NodeUpdateContext context) {
		return HttpClientUtil.post(NODE_UPDATE_URL, context, JSONObject.class);
	}

	/**
	 * 删除节点
	 *
	 * @param context 上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject deleteNode(NodeOpsContext context) {
		return HttpClientUtil.post(NODE_DELETE_URL, context, JSONObject.class);
	}

	/**
	 * 更新场景
	 *
	 * @param context 上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject updateScene(SceneUpdateContext context) {
		return HttpClientUtil.post(SCENE_UPDATE_URL, context, JSONObject.class);
	}

	/**
	 * 起始节点
	 *
	 * @param context 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean startNode(NodeOpsContext context) {
		return HttpClientUtil.post(NODE_START_URL, context, Boolean.class);
	}

	/**
	 * 停止节点
	 *
	 * @param context 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopNode(NodeOpsContext context) {
		return HttpClientUtil.post(NODE_STOP_URL, context, Boolean.class);
	}

	/**
	 * 获取资产列表
	 *
	 * @param req 请求体
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getAssetList(SceneNodeListReq req) {
		String targetUrl = SCENE_NODE_LIST + req.toUrl();
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	/**
	 * 获取资产统计学由场景id
	 *
	 * @param sceneId 场景id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getAssetsStatisticsBySceneId(String sceneId) {
		// 只获取一个场景的
		String targetUrl = SCENE_NODE_STATISTICS + "?sceneIds=" + sceneId;
		JSONArray rsp = HttpClientUtil.rawGet(targetUrl, JSONArray.class);
		return rsp.getJSONObject(0);
	}

	/**
	 * 屏幕录制
	 *
	 * @param sceneId 场景id
	 * @param nodeId  节点id
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject screenrecord(String sceneId, String nodeId) {

		String targetUrl = NODE_SCREEN_RECORD + "?csceneId=" + sceneId+"&nodeId="+nodeId+"&page=1&pageSize=999";
		return  HttpClientUtil.rawGet(targetUrl, JSONObject.class);
	}

	/**
	 * 攻击类型计数
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray attackTypeCount(String mpSceneId) {
		String targetUrl = ATTACK_TYPE_COUNT + "?sceneId=" + mpSceneId;
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	/**
	 * 场景趋势
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray sceneTrend(String mpSceneId) {
		String targetUrl = SCENE_TREND + "?sceneId=" + mpSceneId;
		return HttpClientUtil.rawGet(targetUrl, JSONArray.class);
	}

	/**
	 * 重启节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean restartNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RESTART_URL, nodeOpsContext, Boolean.class);
	}

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsContext Node Ops 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean recreateNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RECREATE_URL, nodeOpsContext, Boolean.class);
	}

	/**
	 * 场景转 储
	 *
	 * @param sceneDumpContext 场景转 储上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject sceneDump(SceneDumpContext sceneDumpContext) {
		return HttpClientUtil.post(SCENE_DUMP_URL, sceneDumpContext, JSONObject.class);
	}

	/**
	 * 获取流由id
	 *
	 * @param flowId 流id
	 * @return {@code MpFlow }
	 */
	@Override
	public MpFlow getFlowById(String flowId) {
		return HttpClientUtil.rawGet(FLOW_RETRIEVE_URL + flowId, MpFlow.class);
	}

	/**
	 * Update Flow 参数
	 *
	 * @param flowOpsContext 流操作上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean updateFlowParam(FlowOpsContext flowOpsContext) {
		return HttpClientUtil.post(FLOW_UPDATE_PARAM_URL, flowOpsContext, Boolean.class);
	}

	/**
	 * 启动流程
	 *
	 * @param flowOpsContext 流操作上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean startFlow(FlowOpsContext flowOpsContext) {
		return HttpClientUtil.post(FLOW_START_URL, flowOpsContext, Boolean.class);
	}

	/**
	 * 停止流
	 *
	 * @param context 上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopFlow(FlowOpsContext context) {
		return HttpClientUtil.post(FLOW_STOP_URL, context, Boolean.class);
	}

	/**
	 * 批量开始流
	 *
	 * @param flowBatchOpsContext 流批量老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchStartFlow(FlowBatchOpsContext flowBatchOpsContext) {
		return HttpClientUtil.post(FLOW_BATCH_START_URL, flowBatchOpsContext, Boolean.class);
	}

	/**
	 * 批量停流
	 *
	 * @param flowBatchOpsContext 流批量老年 退休金 计划上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean batchStopFlow(FlowBatchOpsContext flowBatchOpsContext) {
		return HttpClientUtil.post(FLOW_BATCH_STOP_URL, flowBatchOpsContext, Boolean.class);
	}

	/**
	 * 获取流记录
	 *
	 * @param flowRecordContext 流记录上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getFlowRecords(FlowRecordContext flowRecordContext) {
		return HttpClientUtil.post(FLOW_RECORD_URL, flowRecordContext, JSONObject.class);
	}

	/**
	 * 开始场景
	 *
	 * @param context 上下文
	 * @param url     网址
	 * @return {@code String }
	 */
	private String startScene(SceneStartContext context, String url) {
		JSONObject data = HttpClientUtil.post(url, context, JSONObject.class);

		if (null == data) {
			log.error("topoId=[{}] startScene error, response data is null", context.getId());
			throw new RemoteServiceException();
		}

		String sceneId = data.getString("id");
		if (StringUtils.isBlank(sceneId)) {
			log.error("topoId=[{}] startScene error, sceneId is blank", context.getId());
			throw new RemoteServiceException();
		}
		// 校验sceneId长度
		if (sceneId.length() != SCENE_ID_LENGTH) {
			log.error("Illegal sceneId {}", sceneId);
			throw new RemoteServiceException();
		}

		return sceneId;
	}

	/**
	 * 获取节点会话
	 *
	 * @param sceneIds 场景id列表
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getNodeSessions(List<String> sceneIds) {

		if (CollectionUtils.isEmpty(sceneIds)) {
			return new JSONArray();
		}

		String sceneIdStr = String.join(CommonConstant.CODE_COMMA, sceneIds);
		return HttpClientUtil.rawGet(NODE_SESSION_URL + sceneIdStr, JSONArray.class);
	}

	/**
	 * 获取节点会话
	 *
	 * @param sceneIds 场景id列表
	 * @param nodeIds  节点id列表
	 * @param userIds  用户id列表
	 * @param type     类型
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getNodeSessions(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds, String type) {
		if (CollectionUtils.isEmpty(sceneIds)) {
			return new JSONArray();
		}

		String assistanceUrl = this.NODE_SESSION_URL
				+ String.join(CommonConstant.CODE_COMMA, sceneIds);
		if (!CollectionUtils.isEmpty(nodeIds)) {
			assistanceUrl += "&nodeIds=" + String.join(CommonConstant.CODE_COMMA, nodeIds);
		}
		if (!CollectionUtils.isEmpty(userIds)) {
			assistanceUrl += "&userIds=" + String.join(CommonConstant.CODE_COMMA, userIds);
		}
		if (StringUtils.isNotBlank(type)) {
			assistanceUrl += "&type=" + type;
		}

		return HttpClientUtil.rawGet(assistanceUrl, JSONArray.class);
	}

	/**
	 * 获取援助地图
	 *
	 * @param sceneIds 场景id列表
	 * @param nodeIds  节点id列表
	 * @param userIds  用户id列表
	 * @return {@code Map<String, String> }
	 */
	@Override
	public Map<String, String> getAssistanceMap(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds) {

		JSONArray nodeSessions = getNodeSessions(sceneIds, nodeIds, userIds,
				SessionType.ASSISTANCE.type);
		List<Object> filterList = StreamApiUtils.filter(nodeSessions, node -> StringUtils
				.isNotBlank(((JSONObject) node).getString(JsonKeyConstant.ASSISTANCE_URL)));

		List<Object> distinct = StreamApiUtils.distinctByColumn(filterList,
				obj -> ((JSONObject) obj).getString(JsonKeyConstant.USER_KEY));

		Map<String, String> userAssistanceMap = StreamApiUtils.toMap(distinct,
				object -> ((JSONObject) object).getString(JsonKeyConstant.USER_KEY),
				object -> ((JSONObject) object).getString(JsonKeyConstant.ASSISTANCE_URL));
		return userAssistanceMap;
	}

	/**
	 * 获取监控会话
	 *
	 * @param mpSceneId 中台场景id
	 * @param studentId 学员id
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray getMonitorSessions(String mpSceneId, String studentId) {
		String url = NODE_SESSION_URL + mpSceneId;
		if (StringUtils.isNotBlank(studentId)) {
			url = url + "&userIds=" + studentId;
		}
		return HttpClientUtil.rawGet(url, JSONArray.class);
	}

	/**
	 * 获取流原木
	 *
	 * @param flowLogsPageContext 流原木分页上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext) {
		// 该接口必须使用英文
		String tmpLanguage = CommonContext.getLanguage();
		CommonContext.setLanguage(WebConstant.LANGUAGE_EN);
		final JSONObject jsonObject = HttpClientUtil.rawGetByParam(flowLogsPageContext, FLOW_LOG_URL, JSONObject.class);
		CommonContext.setLanguage(tmpLanguage);
		return jsonObject;
	}

	/**
	 * 获取主机州
	 *
	 * @param elementCommonContext 元素通用上下文
	 * @return {@code List<MpHostState> }
	 */
	@Override
	public List<MpHostState> getHostState(ElementCommonContext elementCommonContext) {
		String mpHostStates = HttpClientUtil.rawGetByParam(elementCommonContext, HOST_STATE_URL,
				String.class);
		return JsonUtil.toJsonObjectArray(mpHostStates, MpHostState.class);
	}

	/**
	 * 获取流趋势
	 *
	 * @param elementCommonContext 元素通用上下文
	 * @return {@code List<MpFlowTrend> }
	 */
	@Override
	public List<MpFlowTrend> getFlowTrend(ElementCommonContext elementCommonContext) {
		String flowTrends = HttpClientUtil.rawGetByParam(elementCommonContext, FLOW_TREND_URL,
				String.class);
		return JsonUtil.toJsonObjectArray(flowTrends, MpFlowTrend.class);
	}

	/**
	 * 获取事件aggr
	 *
	 * @param elementCommonContext 元素通用上下文
	 * @return {@code MpEventAggr }
	 */
	@Override
	public MpEventAggr getEventAggr(ElementCommonContext elementCommonContext) {
		return HttpClientUtil.rawGetByParam(elementCommonContext, EVENT_AGGR_URL,
				MpEventAggr.class);
	}

	/**
	 * 获取风险计数
	 *
	 * @param elementCommonContext 元素通用上下文
	 * @return {@code MpRiskCount }
	 */
	@Override
	public MpRiskCount getRiskCount(ElementCommonContext elementCommonContext) {
		return HttpClientUtil.rawGetByParam(elementCommonContext, RISK_COUNT_URL,
				MpRiskCount.class);
	}

	/**
	 * 获取收集事件
	 *
	 * @param collectionEventPageContext 收集事件分页上下文
	 * @return {@code List<MpCollectionEvent> }
	 */
	@Override
	public List<MpCollectionEvent>
			getCollectionEvent(CollectionEventPageContext collectionEventPageContext) {
		String mpCollectionEvents = HttpClientUtil.rawGetByParam(collectionEventPageContext,
				COLLECTION_EVENT_DATA_URL, String.class);
		// 远程返回异常
		if (mpCollectionEvents == null) {
			throw new RemoteServiceException();
		}
		JSONObject result = JsonUtil.toJsonObject(mpCollectionEvents, JSONObject.class);

		return JsonUtil.toJsonObjectArray(result.getString("list"), MpCollectionEvent.class);
	}

	/**
	 * 节点快照
	 *
	 * @param snapshotContext 快照上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean nodeSnapshot(NodeSnapshotContext snapshotContext) {
		return HttpClientUtil.post(NODE_SNAPSHOT_URL,
				JsonUtil.toJsonString(snapshotContext, PropertyNamingStrategy.SnakeCase),
				Boolean.class);
	}

	/**
	 * 快照历史记录
	 *
	 * @param imageId 图像id
	 * @return {@code JSONArray }
	 */
	@Override
	public JSONArray snapshotHistory(String imageId) {
		String requestUrl = String.format(SNAPSHOT_HISTORY_URL, imageId);
		return HttpClientUtil.rawGet(requestUrl, JSONArray.class);
	}

	/**
	 * 合并快照
	 *
	 * @param mergeSnapshotContext 合并快照上下文
	 * @return {@code JSONObject }
	 */
	@Override
	public JSONObject mergeSnapshot(MergeSnapshotContext mergeSnapshotContext) {

		HttpClientUtil.post(SNAPSHOT_MERGE_URL,
				JsonUtil.toJsonString(mergeSnapshotContext, PropertyNamingStrategy.SnakeCase),
				String.class);
		return null;
	}

	/**
	 * 删除快照
	 *
	 * @param deleteContext 删除上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean deleteSnapshot(SnapshotDeleteContext deleteContext) {
		return HttpClientUtil.post(SNAPSHOT_DELETE_URL,
				JsonUtil.toJsonString(deleteContext, PropertyNamingStrategy.SnakeCase),
				Boolean.class);
	}

	/**
	 * 获取连接状态
	 *
	 * @param mpSceneId 中台场景id
	 * @return {@code List<MpNodeStatus> }
	 */
	@Override
	public List<MpNodeStatus> getConnectionStatus(String mpSceneId) {
		String url = CONNECTION_STATUS_URL + mpSceneId;

		JSONObject jsonObject = HttpClientUtil.rawGet(url, JSONObject.class);
		String mpNodeStatus = JsonUtil.toJsonString(
				jsonObject.getOrDefault("node_link_status_list", Lists.newArrayList()));

		return JsonUtil.toJsonObjectArray(mpNodeStatus, MpNodeStatus.class);
	}

	/**
	 * 取代访问ip地址
	 *
	 * @param topoData 拓扑数据
	 * @return {@code JSONObject }
	 */
	private JSONObject replaceAccessIp(JSONObject topoData) {
		if (topoData == null) {
			return null;
		}
		try {
			JSONObject elementPorts = topoData.getJSONObject("scene").getJSONObject("plugins")
					.getJSONObject("cpcloud").getJSONObject("element_ports");
			replaceAccessIpExe(elementPorts);
		} catch (Exception e) {
			log.error("[getById] access ip replacement failed:", e);
		}

		return topoData;
	}

	/**
	 * 取代访问ip地址exe
	 *
	 * @param elementPorts Element 端口
	 */
	@Override
	public void replaceAccessIpExe(JSONObject elementPorts) {
		if (elementPorts == null) {
			return;
		}
		for (String key : elementPorts.keySet()) {
			JSONArray accessModes = elementPorts.getJSONObject(key).getJSONArray("access_modes");
			if (!accessModes.isEmpty()) {
				accessModes.toJavaList(JSONObject.class).forEach(item -> {
					// origin access port
					String accessIp = item.getString("access_ip");
					String accessPort = item.getString("access_port");
					if (!StringUtils.isEmpty(accessIp)) {
						// internet access port
						String internetProxyIp = item.getString("public_net_proxy_ip");
						Integer internetProxyPort = item.getInteger("public_net_proxy_port");
						if (!StringUtils.isEmpty(internetProxyIp)) {
							item.put("access_ip", internetProxyIp);
							item.put("access_port", internetProxyPort);
							log.debug("[replaceAccessIp] access ip replacement [{}:{}] -> [{}:{}]",
									accessIp, accessPort, internetProxyIp, internetProxyPort);
						}
					}
				});
			}
		}
	}
}

