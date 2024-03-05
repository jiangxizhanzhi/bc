package com.cyberpeace.integratation.service.impl;

import static com.cyberpeace.common.constant.WebConstant.SCENE_ID_LENGTH;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteSceneServiceImpl implements RemoteSceneService {

	@Autowired
	private MpEnvService mpEnvService;

	private String SCENE_START_URL;

	private String SCENE_START_TEST_URL;

	private String SCENE_DELETE_URL;

	private String SCENE_CLEAR_URL;

	private String SCENE_RETRIEVE;

	private String SCENE_PAUSE_URL;

	private String SCENE_UNPAUSE_URL;

	private String NODE_RESTART_URL;

	private String NODE_RECREATE_URL;

	private String FLOW_START_URL;

	private String FLOW_STOP_URL;

	private String FLOW_UPDATE_PARAM_URL;

	private String FLOW_BATCH_START_URL;

	private String FLOW_BATCH_STOP_URL;

	private String NODE_SESSION_URL;

	private String FLOW_LOG_URL;

	private String HOST_STATE_URL;

	private String FLOW_TREND_URL;

	private String EVENT_AGGR_URL;

	private String RISK_COUNT_URL;

	private String COLLECTION_EVENT_URL;

	private String COLLECTION_EVENT_DATA_URL;

	private String FLOW_RECORD_URL;

	private String FLOW_RETRIEVE_URL;

	private String SCENE_DUMP_URL;

	@PostConstruct
	public void init() {
		SCENE_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.app");

		SCENE_START_TEST_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.start.test");

		SCENE_DELETE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.delete");

		SCENE_CLEAR_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.clear");

		SCENE_RETRIEVE = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.retrieve");

		SCENE_PAUSE_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.pause");

		SCENE_UNPAUSE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.unpause");

		NODE_RESTART_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.restart");

		NODE_RECREATE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.recreate");

		FLOW_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.start");

		FLOW_STOP_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.stop");

		FLOW_UPDATE_PARAM_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.param.update");

		FLOW_BATCH_START_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.batch.start");

		FLOW_BATCH_STOP_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.batch.stop");

		NODE_SESSION_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.session");

		FLOW_LOG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("scene.flow.log");

		HOST_STATE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.host.state");

		FLOW_TREND_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.flow.trend");

		EVENT_AGGR_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.event.aggr");

		RISK_COUNT_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.risk.count");

		COLLECTION_EVENT_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.probe.collection.event");

		COLLECTION_EVENT_DATA_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.probe.collection.data");

		FLOW_RECORD_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("scene.node.flow.record");

		FLOW_RETRIEVE_URL = mpEnvService.getMpHost()
				+ ConfigUtil.getContextProperty("mp.flow.retrieve");

		SCENE_DUMP_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("mp.scene.dump");

	}

	@Override
	public String startScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_URL);
	}

	@Override
	public String testScene(SceneStartContext sceneStartContext) {
		return startScene(sceneStartContext, SCENE_START_TEST_URL);
	}

	@Override
	public Boolean stopScene(SceneOpsContext sceneOpsContext) {
		String targetUrl = SCENE_DELETE_URL;

		Boolean result = HttpClientUtil.delete(targetUrl, sceneOpsContext, Boolean.class);

		return result;
	}

	@Override
	public Boolean pauseScene(SceneOpsContext sceneOpsContext) {
		return HttpClientUtil.post(SCENE_PAUSE_URL, sceneOpsContext, Boolean.class);
	}

	@Override
	public JSONObject getById(String sceneId, String userId) {
		String targetUrl = SCENE_RETRIEVE + "?id=" + sceneId + "&accessUserId=" + userId;
		return HttpClientUtil.rawGet(targetUrl, JSONObject.class);

	}

	@Override
	public Boolean unpauseScene(SceneOpsContext sceneOpContext) {
		return HttpClientUtil.post(SCENE_UNPAUSE_URL, sceneOpContext, Boolean.class);

	}

	@Override
	public Boolean restartNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RESTART_URL, nodeOpsContext, Boolean.class);
	}

	@Override
	public Boolean recreateNode(NodeOpsContext nodeOpsContext) {
		return HttpClientUtil.post(NODE_RECREATE_URL, nodeOpsContext, Boolean.class);
	}

	@Override
	public MpFlow getFlowById(String flowId) {
		return HttpClientUtil.rawGet(FLOW_RETRIEVE_URL + flowId, MpFlow.class);
	}

	@Override
	public Boolean updateFlowParam(FlowOpsContext flowOpsContext) {
		return HttpClientUtil.post(FLOW_UPDATE_PARAM_URL, flowOpsContext, Boolean.class);
	}

	@Override
	public Boolean startFlow(FlowOpsContext flowOpsContext) {
		return HttpClientUtil.post(FLOW_START_URL, flowOpsContext, Boolean.class);
	}

	@Override
	public Boolean stopFlow(FlowOpsContext context) {
		return HttpClientUtil.post(FLOW_STOP_URL, context, Boolean.class);
	}

	@Override
	public Boolean batchStartFlow(FlowBatchOpsContext flowBatchOpsContext) {
		return HttpClientUtil.post(FLOW_BATCH_START_URL, flowBatchOpsContext, Boolean.class);
	}

	@Override
	public Boolean batchStopFlow(FlowBatchOpsContext flowBatchOpsContext) {
		return HttpClientUtil.post(FLOW_BATCH_STOP_URL, flowBatchOpsContext, Boolean.class);
	}

	@Override
	public JSONObject getFlowRecords(FlowRecordContext flowRecordContext) {
		return HttpClientUtil.post(FLOW_RECORD_URL, flowRecordContext, JSONObject.class);
	}

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

	@Override
	public JSONArray getNodeSessions(List<String> sceneIds) {

		if (CollectionUtils.isEmpty(sceneIds)) {
			return new JSONArray();
		}

		String sceneIdStr = String.join(CommonConstant.CODE_COMMA, sceneIds);
		return HttpClientUtil.rawGet(NODE_SESSION_URL + sceneIdStr, JSONArray.class);
	}

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

	@Override
	public Map<String, String> getAssistanceMap(List<String> sceneIds, List<String> nodeIds,
			List<String> userIds) {

		JSONArray nodeSessions = getNodeSessions(sceneIds, nodeIds, userIds,
				SessionType.ASSISTANCE.type);
		List<Object> filterList = StreamApiUtils.filter(nodeSessions, node -> StringUtils
				.isNotBlank(((JSONObject) node).getString(JsonKeyConstant.ASSISTANCE_URL)));

		List<Object> distinct = StreamApiUtils.distinctByColumn(filterList, obj -> ((JSONObject) obj).getString(JsonKeyConstant.USER_KEY));

		Map<String, String> userAssistanceMap = StreamApiUtils.toMap(distinct,
				object -> ((JSONObject) object).getString(JsonKeyConstant.USER_KEY),
				object -> ((JSONObject) object).getString(JsonKeyConstant.ASSISTANCE_URL));
		return userAssistanceMap;
	}

	@Override
	public JSONArray getMonitorSessions(String mpSceneId, String studentId) {
		String url = NODE_SESSION_URL + mpSceneId;
		if (StringUtils.isNotBlank(studentId)) {
			url = url + "&userIds=" + studentId;
		}
		return HttpClientUtil.rawGet(url, JSONArray.class);
	}

	@Override
	public JSONObject getFlowLogs(FlowLogsPageContext flowLogsPageContext) {
		return HttpClientUtil.rawGetByParam(flowLogsPageContext, FLOW_LOG_URL, JSONObject.class);
	}

	@Override
	public List<MpHostState> getHostState(ElementCommonContext elementCommonContext) {
		String mpHostStates = HttpClientUtil.rawGetByParam(elementCommonContext, HOST_STATE_URL,
				String.class);
		return JsonUtil.toJsonObjectArray(mpHostStates, MpHostState.class);
	}

	@Override
	public List<MpFlowTrend> getFlowTrend(ElementCommonContext elementCommonContext) {
		String flowTrends = HttpClientUtil.rawGetByParam(elementCommonContext, FLOW_TREND_URL,
				String.class);
		return JsonUtil.toJsonObjectArray(flowTrends, MpFlowTrend.class);
	}

	@Override
	public MpEventAggr getEventAggr(ElementCommonContext elementCommonContext) {
		return HttpClientUtil.rawGetByParam(elementCommonContext, EVENT_AGGR_URL,
				MpEventAggr.class);
	}

	@Override
	public MpRiskCount getRiskCount(ElementCommonContext elementCommonContext) {
		return HttpClientUtil.rawGetByParam(elementCommonContext, RISK_COUNT_URL,
				MpRiskCount.class);
	}

	@Override
	public List<MpCollectionEvent>
			getCollectionEvent(CollectionEventPageContext collectionEventPageContext) {
		String mpCollectionEvents = HttpClientUtil.rawGetByParam(collectionEventPageContext,
				COLLECTION_EVENT_DATA_URL, String.class);
		// todo 远程返回异常
		if (mpCollectionEvents == null) {
			throw new RemoteServiceException();
		}
		JSONObject result = JsonUtil.toJsonObject(mpCollectionEvents, JSONObject.class);
		// todo 错误响应
		return JsonUtil.toJsonObjectArray(result.getString("list"), MpCollectionEvent.class);
	}

	@Override
	public JSONObject sceneDump(SceneDumpContext sceneDumpContext) {
		return HttpClientUtil.post(SCENE_DUMP_URL, sceneDumpContext, JSONObject.class);
	}

}
