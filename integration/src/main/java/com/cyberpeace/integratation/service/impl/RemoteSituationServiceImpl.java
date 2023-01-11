package com.cyberpeace.integratation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.LgNodeActionPageRsp;
import com.cyberpeace.integratation.model.situation.res.LgNodeWeakRsp;
import com.cyberpeace.integratation.model.situation.res.LgTaskContextPageRes;
import com.cyberpeace.integratation.model.situation.res.LgUserActionRsp;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

	private String RANGE_INFO_URL;

	private String CENTER_INFO_URL;

	private String PLATFORM_LIST_URL;

	private String TASK_LIST_URL;

	private String SUBJECT_LIST_URL;

	private String NETWORK_INFO_URL;

	private String NODE_INFO_URL;

	private String NODE_ACTION_URL;

	private String NODE_TREND_URL;

	private String USER_ACTION_URL;

	private String USER_TREND_URL;

	private String NODE_CONNECTION_URL;

	private String NODE_WEAK_INFO_URL;

	private String NODE_WEAK_LIST_URL;

	private String TASK_EVENT_URL;

	private String AD_EVENT_URL;

	private String NODE_STATUS_URL;

	private String XD_TOPO_URL;

	private String XD_SCHEME_LIST;

	private String XD_SCHEME;

	private String XD_TOPO_HISTORY;

	private String XD_NODE_LIST;

	private String XD_RELEASE_SCREEN;

	@Autowired
	private RemoteOperationServiceImpl remoteOperationService;

	Cache<String, Date> queryTimeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	@PostConstruct
	public void init() {
		String outerHostTs = "http://" + ConfigUtil.getContextProperty("outer.host.ts");
		String outerHostXd = "http://" + ConfigUtil.getContextProperty("outer.host.xd");
		RANGE_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("range.info");
		CENTER_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("center.info");
		PLATFORM_LIST_URL = outerHostXd + ConfigUtil.getContextProperty("platform.list");
		TASK_LIST_URL = outerHostTs + ConfigUtil.getContextProperty("task.list.all");
		SUBJECT_LIST_URL = outerHostTs + ConfigUtil.getContextProperty("subject.list.all");
		NETWORK_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("network.info");
		NODE_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("node.info");
		NODE_ACTION_URL = outerHostTs + ConfigUtil.getContextProperty("node.action");
		NODE_TREND_URL = outerHostTs + ConfigUtil.getContextProperty("node.trend");
		USER_ACTION_URL = outerHostTs + ConfigUtil.getContextProperty("user.action");
		USER_TREND_URL = outerHostTs + ConfigUtil.getContextProperty("user.trend");
		NODE_CONNECTION_URL = outerHostTs + ConfigUtil.getContextProperty("node.connection");
		NODE_WEAK_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("node.weak.info");
		NODE_WEAK_LIST_URL = outerHostTs + ConfigUtil.getContextProperty("node.weak.list");
		TASK_EVENT_URL = outerHostTs + ConfigUtil.getContextProperty("event.task");
		AD_EVENT_URL = outerHostTs + ConfigUtil.getContextProperty("event.ad");
		NODE_STATUS_URL = outerHostTs + ConfigUtil.getContextProperty("event.node");
		XD_TOPO_URL = outerHostXd + ConfigUtil.getContextProperty("xd.topo");
		XD_SCHEME_LIST = outerHostXd + ConfigUtil.getContextProperty("xd.scheme.list");
		XD_SCHEME = outerHostXd + ConfigUtil.getContextProperty("xd.scheme");
		XD_TOPO_HISTORY = outerHostXd + ConfigUtil.getContextProperty("xd.topo.history");
		XD_NODE_LIST = outerHostXd + ConfigUtil.getContextProperty("xd.node.list");
		XD_RELEASE_SCREEN = outerHostXd + ConfigUtil.getContextProperty("xd.release.screen");
	}

	@Override
	public SubRangeInfoContext getSubRangeInfo(String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = HttpClientUtil.rawGet(RANGE_INFO_URL + subRangeId,
				SubRangeInfoContext.class);
		return subRangeInfoContext;
	}

	@Override
	public CenterInfoContext getCenterInfo(String centerId) {
		CenterInfoContext centerInfoContext = HttpClientUtil.rawGet(CENTER_INFO_URL + centerId,
				CenterInfoContext.class);
		return centerInfoContext;
	}

	@Override
	public List<PlatformContext> getPlatformList(String taskId, String subjectId, String subRangeId,
			String schemeId) {
		JSONArray result = HttpClientUtil.rawGet(PLATFORM_LIST_URL + "?taskId=" + taskId
				+ "&subjectId=" + subjectId + "&subRangeId=" + subRangeId + "&schemeId=" + schemeId,
				JSONArray.class);
		List<PlatformContext> platformContexts = JSONArray
				.parseArray(JSONObject.toJSONString(result), PlatformContext.class);

		Map<String, ModelContext> modelListCache = remoteOperationService.getModelListMap();

		platformContexts.forEach(platformContext -> {
			String platformUrl = getModelThreeDimensionPic(modelListCache,
					platformContext.getModelId());
			if (platformUrl != null) {
				platformContext.setPlatformUrl(platformUrl);
			}
		});
		return platformContexts;
	}

	private String getModelThreeDimensionPic(Map<String, ModelContext> modelContextMap,
			String modelId) {
		ModelContext modelContext = modelContextMap.get(modelId);
		return modelContext == null ? null : modelContext.getThreeDimensionPic();
	}

	@Override
	public NetworkInfoContext getNetworkInfo(PlatformReq platformReq) {
		NodeListReq nodeListReq = BeanUtils.copy(platformReq, NodeListReq.class);
		List<String> nodeIdList = getNodeIdList(nodeListReq);
		ThirdPartyTopoBo topoBo = HttpClientUtil.rawGet(
				XD_TOPO_URL + "?platformId=" + platformReq.getPlatformId(), ThirdPartyTopoBo.class);
		Map<String, Integer> nodeTypeStatistics = Maps.newHashMap();
		List<NodeTypeStatisticsContext> nodeTypeStatisticsContexts = Lists.newArrayList();
		topoBo.getNodes().forEach(elementContext -> {
			if (nodeTypeStatistics.get(elementContext.getDeviceType()) == null) {
				nodeTypeStatistics.put(elementContext.getDeviceType(), 1);
			} else {
				nodeTypeStatistics.put(elementContext.getDeviceType(),
						nodeTypeStatistics.get(elementContext.getDeviceType()) + 1);
			}
		});

		Map<String, ModelTypeContext> modelTypeContextMap = remoteOperationService
				.getModelTypeListMap();

		nodeTypeStatistics.forEach((key, value) -> {

			if (modelTypeContextMap.get(key) != null) {
				nodeTypeStatisticsContexts.add(new NodeTypeStatisticsContext(
						modelTypeContextMap.get(key).getTypeName(), value));
			}
		});

		NodeListApiReq nodeListApiReq = BeanUtils.copy(platformReq, NodeListApiReq.class);
		nodeListApiReq.setNodeIds(nodeIdList);
		NetworkInfoContext networkInfoContext = HttpClientUtil.post(NETWORK_INFO_URL,
				nodeListApiReq, NetworkInfoContext.class);
		networkInfoContext.setNodeCount(nodeIdList.size());
		networkInfoContext.setNodeTypeStatistics(nodeTypeStatisticsContexts);
		return networkInfoContext;
	}

	@Override
	public ListRsp<TaskContext> getTaskList(TaskListReq taskListReq) {
		LgTaskContextPageRes rsp = HttpClientUtil.post(TASK_LIST_URL, taskListReq,
				LgTaskContextPageRes.class);
		ListRsp<TaskContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public List<SubjectContext> getSubjectList(SubjectListReq subjectListReq) {
		String rsp = HttpClientUtil.post(SUBJECT_LIST_URL, subjectListReq, String.class);
		List<SubjectContext> res = JsonUtil.toJsonObjectArray(rsp, SubjectContext.class);
		return res;

	}

	@Override
	public NodeInfoContext getNodeInfo(NodeIdApiReq nodeIdApiReq) {
		NodeInfoContext context = HttpClientUtil.post(NODE_INFO_URL, nodeIdApiReq,
				NodeInfoContext.class);
		return context;
	}

	@Override
	public ListRsp<NodeActionContext> getNodeAction(NodeActionReq nodeActionReq) {
		LgNodeActionPageRsp rsp = HttpClientUtil.post(NODE_ACTION_URL, nodeActionReq,
				LgNodeActionPageRsp.class);
		ListRsp<NodeActionContext> res = rsp.convert();
		res.setList(rsp.getList());

		return res;
	}

	@Override
	public List<NodeTrendContext> getNodeTrend(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.post(NODE_TREND_URL, nodeIdApiReq, String.class);
		List<NodeTrendContext> res = JsonUtil.toJsonObjectArray(rsp, NodeTrendContext.class);
		return res;
	}

	@Override
	public ListRsp<UserActionContext> getUserAction(NodeIdPageReq nodeIdPageReq) {
		LgUserActionRsp rsp = HttpClientUtil.post(USER_ACTION_URL, nodeIdPageReq,
				LgUserActionRsp.class);
		ListRsp<UserActionContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public List<UserTrendContext> getUserTrend(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.post(USER_TREND_URL, nodeIdApiReq, String.class);
		List<UserTrendContext> res = JsonUtil.toJsonObjectArray(rsp, UserTrendContext.class);
		return res;
	}

	@Override
	public NodeConnectionContext getNodeConnection(NodeIdApiReq nodeIdApiReq) {
		return HttpClientUtil.post(NODE_CONNECTION_URL, nodeIdApiReq, NodeConnectionContext.class);
	}

	@Override
	public NodeWeakInfoContext getNodeWeakInfo(NodeIdApiReq nodeIdApiReq) {
		return HttpClientUtil.post(NODE_WEAK_INFO_URL, nodeIdApiReq, NodeWeakInfoContext.class);
	}

	@Override
	public ListRsp<NodeWeakContext> getNodeWeakList(NodeIdPageReq nodeIdPageReq) {
		LgNodeWeakRsp rsp = HttpClientUtil.post(NODE_WEAK_LIST_URL, nodeIdPageReq,
				LgNodeWeakRsp.class);
		ListRsp<NodeWeakContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	private Date getQueryTime(String featureId) {

		if (StringUtils.isBlank(featureId)) {
			return null;
		}

		Date currentLastTime = queryTimeCache.getIfPresent(featureId);
		return currentLastTime;
	}

	@Override
	public List<TaskEventContext> getTaskEventList(TaskEventReq taskEventReq, String featureId) {
		taskEventReq.setQueryTime(getQueryTime(featureId));
		String rsp = HttpClientUtil.rawGetByParam(taskEventReq, TASK_EVENT_URL, String.class);
		List<TaskEventContext> res = JsonUtil.toJsonObjectArray(rsp, TaskEventContext.class);

		// 更新查询时间缓存
		if (res != null && !res.isEmpty()) {
			queryTimeCache.put(featureId, res.get(res.size() - 1).getEventTime());
		}

		return res;
	}

	@Override
	public List<AdEventContext> getAdEventList(EventReq eventReq, String featureId) {
		eventReq.setQueryTime(getQueryTime(featureId));
		String rsp = HttpClientUtil.post(AD_EVENT_URL, eventReq, String.class);
		List<AdEventContext> res = JsonUtil.toJsonObjectArray(rsp, AdEventContext.class);

		// 更新查询时间缓存
		if (res != null && !res.isEmpty()) {
			queryTimeCache.put(featureId, res.get(res.size() - 1).getEventTime());
		}
		return res;
	}

	@Override
	public List<NodeEventContext> getNodeEventList(EventReq eventReq, String featureId) {
		String rsp = HttpClientUtil.post(NODE_STATUS_URL, eventReq, String.class);
		List<NodeEventContext> res = JsonUtil.toJsonObjectArray(rsp, NodeEventContext.class);
		return res;
	}

	@Override
	public TopoContext getTopo(TopoReq topoReq, Boolean adjust) {

		String query = "?envId=" + topoReq.getEnvId();

		if (StringUtils.isNotBlank(topoReq.getNetworkId())) {
			query = query + "&networkId=" + topoReq.getNetworkId();
		} ;
		if (StringUtils.isNotBlank(topoReq.getPlatformId())) {
			query = query + "&platformId=" + topoReq.getPlatformId();
		} ;
		if (StringUtils.isNotBlank(topoReq.getTaskId())) {
			query = query + "&taskId=" + topoReq.getTaskId();
		} ;
		if (StringUtils.isNotBlank(topoReq.getSubjectId())) {
			query = query + "&subjectId=" + topoReq.getSubjectId();
		} ;
		if (topoReq.getLevel() != null) {
			query = query + "&level=" + topoReq.getLevel();
		} ;

		ThirdPartyTopoBo topoBo = HttpClientUtil.rawGet(XD_TOPO_URL + query,
				ThirdPartyTopoBo.class);

		TopoContext topoContext = topoBo.convert(adjust,
				remoteOperationService.getModelTypeListMap(),
				remoteOperationService.getModelListMap());

		topoBo.setAreas(topoContext);

		return topoContext;
	}

	@Override
	public List<SchemeRspBo> getSchemeList(SituationTaskReq req) {

		String rsp = HttpClientUtil.rawGet(XD_SCHEME_LIST + req.toQuery(), String.class);
		List<SchemeRspBo> res = JsonUtil.toJsonObjectArray(rsp, SchemeRspBo.class);
		return res;
	}

	@Override
	public SchemeVisualizationBo getScheme(String schemeId) {
		if (StringUtils.isBlank(schemeId)) {
			throw new BusinessException(ErrorCode.EMPTY_SCHEME_ID);
		}
		String targetUrl = XD_SCHEME + "?schemeId=" + schemeId;
		SchemeVisualizationBo rsp = HttpClientUtil.rawGet(targetUrl, SchemeVisualizationBo.class);
		return rsp;
	}

	@Override
	public List<TopoHistoryBo> getTopoHistory(SituationTaskReq req) {
		String rsp = HttpClientUtil.rawGet(XD_TOPO_HISTORY + req.toQuery(), String.class);
		List<TopoHistoryBo> res = JsonUtil.toJsonObjectArray(rsp, TopoHistoryBo.class);
		return res;
	}

	@Override
	public Boolean releaseScreen(String schemeId, String dashboardId) {
		ReleaseScreenBo releaseScreenBo = new ReleaseScreenBo();
		releaseScreenBo.setSchemeId(schemeId);
		releaseScreenBo.setDashboardId(dashboardId);

		BooleanRes rsp = HttpClientUtil.post(XD_RELEASE_SCREEN, releaseScreenBo, BooleanRes.class);
		return rsp.getResult();
	}

	private List<String> getNodeIdList(NodeListReq nodeListReq) {
		String rsp = HttpClientUtil.rawGetByParam(nodeListReq, XD_NODE_LIST, String.class);
		List<String> res = JsonUtil.toJsonObjectArray(rsp, String.class);
		return res;
	}

}
