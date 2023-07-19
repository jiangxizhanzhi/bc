package com.cyberpeace.integratation.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import com.cyberpeace.integratation.model.mp.MpDeviceInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.*;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.flow.FlowStatisticsContext;
import com.cyberpeace.integratation.model.situation.flow.FlowTrendContext;
import com.cyberpeace.integratation.model.situation.flow.NodeFlowRankContext;
import com.cyberpeace.integratation.model.situation.layer.LayerEventContext;
import com.cyberpeace.integratation.model.situation.layer.LayerTopoContext;
import com.cyberpeace.integratation.model.situation.node.*;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.*;
import com.cyberpeace.integratation.model.situation.user.UserActivityContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class RemoteSituationServiceImpl implements RemoteSituationService {

	@Autowired
	private SituationEnvService situationEnvService;

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

	private String PG_GET_RUN_ID;

	private String TS_RELEASE_SCREEN;

	private String TS_EVENT_AXIS;

	private String TS_FLOW_STATISTICS;

	private String TS_FLOW_NODE_RANK;

	private String TS_FLOW_TREND;

	private String TS_LAYER_TOPO;

	private String TS_LAYER_EVENT;

	private String TS_NODE_RANK_ATTACK;

	private String TS_NODE_SIMILAR;

	private String TS_DISTRIBUTE_THREATENED;

	private String TS_USER_ACTIVITY;

	private String TS_NODE_STATISTICS_STATUS;

	private String TS_NODE_CRITICAL_DEGREE;

	private String TS_LINK_STATISTICS_STATUS;

	private String TS_NODE_MIGRATION;

	private String TS_NODE_PROTOCOL;

	private String TS_ZH_STAT_NUM;

	private String PLATFORM_LINK_LIST_URL;

	@Autowired
	private RemoteOperationServiceImpl remoteOperationService;

	Cache<String, Date> queryTimeCache = CacheBuilder.newBuilder()
			.expireAfterAccess(1, TimeUnit.DAYS).build();

	@PostConstruct
	public void init() {
		String outerHostTs = situationEnvService.getTsHost();
		String outerHostXd = situationEnvService.getXdHost();
		String outerHostPg = situationEnvService.getPgHost();
		RANGE_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("range.info");
		CENTER_INFO_URL = outerHostTs + ConfigUtil.getContextProperty("center.info");
		PLATFORM_LIST_URL = outerHostXd + ConfigUtil.getContextProperty("platform.list");
		PLATFORM_LINK_LIST_URL = outerHostXd + ConfigUtil.getContextProperty("platform.link.list");
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
		PG_GET_RUN_ID = outerHostPg + ConfigUtil.getContextProperty("pg.run.get");

		TS_RELEASE_SCREEN = outerHostTs + ConfigUtil.getContextProperty("xd.scheme.status");
		TS_EVENT_AXIS = outerHostTs + ConfigUtil.getContextProperty("event.axis");
		TS_FLOW_STATISTICS = outerHostTs + ConfigUtil.getContextProperty("flow.statistics");
		TS_FLOW_NODE_RANK = outerHostTs + ConfigUtil.getContextProperty("flow.node.rank");
		TS_FLOW_TREND = outerHostTs + ConfigUtil.getContextProperty("flow.trend");
		TS_LAYER_TOPO = outerHostTs + ConfigUtil.getContextProperty("layer.topo");
		TS_LAYER_EVENT = outerHostTs + ConfigUtil.getContextProperty("layer.event");
		TS_NODE_RANK_ATTACK = outerHostTs + ConfigUtil.getContextProperty("node.attack.rank");
		TS_NODE_SIMILAR = outerHostTs + ConfigUtil.getContextProperty("node.similar");
		TS_DISTRIBUTE_THREATENED = outerHostTs
				+ ConfigUtil.getContextProperty("node.distribute.threatened");
		TS_USER_ACTIVITY = outerHostTs + ConfigUtil.getContextProperty("user.activity");
		TS_NODE_STATISTICS_STATUS = outerHostTs
				+ ConfigUtil.getContextProperty("node.statistics.status");
		TS_NODE_CRITICAL_DEGREE = outerHostTs
				+ ConfigUtil.getContextProperty("node.critical.degree");
		TS_LINK_STATISTICS_STATUS = outerHostTs
				+ ConfigUtil.getContextProperty("link.statistics.status");
		TS_NODE_MIGRATION = outerHostTs + ConfigUtil.getContextProperty("node.migration");
		TS_NODE_PROTOCOL = outerHostTs
				+ ConfigUtil.getContextProperty("protocol.statistics.status");
		TS_ZH_STAT_NUM = outerHostTs
				+ ConfigUtil.getContextProperty("zh.stat.num");

	}

	@Override
	public SubRangeInfoContext getSubRangeInfo(String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = HttpClientUtil.tsGet(RANGE_INFO_URL + subRangeId,
				SubRangeInfoContext.class, "获取态势系统分靶场信息失败");
		return subRangeInfoContext;
	}

	@Override
	public CenterInfoContext getCenterInfo(String centerId) {
		CenterInfoContext centerInfoContext = HttpClientUtil.tsGet(CENTER_INFO_URL + centerId,
				CenterInfoContext.class, "获取态势系统联管中心信息失败");
		return centerInfoContext;
	}

	@Override
	public List<PlatformContext> getPlatformList(String taskId, String subjectId, String subRangeId,
			String schemeId) {
		String url = PLATFORM_LIST_URL + "?taskId=" + taskId + "&subjectId=" + subjectId
				+ "&subRangeId=" + subRangeId + "&schemeId=" + schemeId;
		JSONArray result = HttpClientUtil.tsGet(url, JSONArray.class, "获取想定系统平台列表信息失败");
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

	@Override
	public List<PlatformLinkContext> getPlatformLinkList(String taskId, String subjectId,
			String subRangeId, String schemeId) {
		String url = PLATFORM_LINK_LIST_URL + "?taskId=" + taskId + "&subjectId=" + subjectId
				+ "&subRangeId=" + subRangeId + "&schemeId=" + schemeId;
		String result = HttpClientUtil.tsGet(url, String.class, "获取想定系统平台连线列表信息失败");
		List<PlatformLinkContext> platformLinkContexts = JSONArray.parseArray(result,
				PlatformLinkContext.class);
		return platformLinkContexts;
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
		ThirdPartyTopoBo topoBo = HttpClientUtil.tsGet(
				XD_TOPO_URL + "?taskId=" + platformReq.getTaskId() + "&envId=" + platformReq.getEnvId() + "&subjectId=" + platformReq.getSubjectId()+ "&platformId=" + platformReq.getPlatformId(), ThirdPartyTopoBo.class,
				"获取想定系统拓扑信息失败");

		NodeListApiReq nodeListApiReq = BeanUtils.copy(platformReq, NodeListApiReq.class);
		nodeListApiReq.setNodeIds(nodeIdList);
		NetworkInfoContext networkInfoContext = HttpClientUtil.tsPost(NETWORK_INFO_URL,
				nodeListApiReq, NetworkInfoContext.class, "获取态势系统子网信息失败");

		if (networkInfoContext == null) {
			networkInfoContext = new NetworkInfoContext();
		}

		networkInfoContext.setNodeCount(nodeIdList.size());
		networkInfoContext.setNodeTypeStatistics(getNodeTypeStatistics(topoBo));
		return networkInfoContext;
	}

	@Override
	public List<NodeTypeStatisticsContext> getNodeTypeStatistics(ThirdPartyTopoBo topo) {
		Map<String, Integer> nodeTypeStatistics = Maps.newHashMap();
		List<NodeTypeStatisticsContext> nodeTypeStatisticsContexts = Lists.newArrayList();
		topo.getNodes().forEach(elementContext -> {
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

		return nodeTypeStatisticsContexts;
	}

	@Override
	public List<TypeStatisticsContext> getLinkTypeStatistics(ThirdPartyTopoBo topo) {
		Map<String, Integer> typeStatistics = Maps.newHashMap();
		List<TypeStatisticsContext> typeStatisticsContexts = Lists.newArrayList();
		topo.getConnections().forEach(connection -> {
			String type = connection.getConnectionType();
			if (typeStatistics.containsKey(type)) {
				typeStatistics.put(type, typeStatistics.get(type) + 1);
			} else {
				typeStatistics.put(type, 1);
			}
		});
		typeStatistics.forEach((key, value) -> {
			typeStatisticsContexts.add(new TypeStatisticsContext(key, value));
		});
		return typeStatisticsContexts;
	}

	@Override
	public ListRsp<TaskContext> getTaskList(TaskListReq taskListReq) {
		LgTaskContextPageRes rsp = HttpClientUtil.tsPost(TASK_LIST_URL, taskListReq,
				LgTaskContextPageRes.class, "获取态势系统任务列表失败");
		ListRsp<TaskContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public List<SubjectContext> getSubjectList(SubjectListReq subjectListReq) {
		String rsp = HttpClientUtil.tsPost(SUBJECT_LIST_URL, subjectListReq, String.class,
				"获取态势系统科目列表失败");
		List<SubjectContext> res = JsonUtil.toJsonObjectArray(rsp, SubjectContext.class);
		return res;
	}

	@Override
	public NodeInfoContext getNodeInfo(NodeIdApiReq nodeIdApiReq) {
		NodeInfoContext context = HttpClientUtil.tsPost(NODE_INFO_URL, nodeIdApiReq,
				NodeInfoContext.class, "获取态势系统节点基本信息失败");
		return context;
	}

	@Override
	public ListRsp<NodeActionContext> getNodeAction(NodeActionReq nodeActionReq) {
		LgNodeActionPageRsp rsp = HttpClientUtil.tsPost(NODE_ACTION_URL, nodeActionReq,
				LgNodeActionPageRsp.class, "获取态势系统节点行为数据失败");
		ListRsp<NodeActionContext> res = rsp.convert();
		res.setList(rsp.getList());

		return res;
	}

	@Override
	public List<NodeTrendContext> getNodeTrend(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.tsPost(NODE_TREND_URL, nodeIdApiReq, String.class,
				"获取态势系统节点行为趋势失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeTrendContext.class);
	}

	@Override
	public ListRsp<UserActionContext> getUserAction(NodeIdPageReq nodeIdPageReq) {
		LgUserActionRsp rsp = HttpClientUtil.tsPost(USER_ACTION_URL, nodeIdPageReq,
				LgUserActionRsp.class, "获取态势系统用户行为数据失败");
		ListRsp<UserActionContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	@Override
	public List<UserTrendContext> getUserTrend(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.tsPost(USER_TREND_URL, nodeIdApiReq, String.class,
				"获取态势系统用户行为趋势失败");
		List<UserTrendContext> res = JsonUtil.toJsonObjectArray(rsp, UserTrendContext.class);
		return res;
	}

	@Override
	public NodeConnectionContext getNodeConnection(NodeIdApiReq nodeIdApiReq) {
		return HttpClientUtil.tsPost(NODE_CONNECTION_URL, nodeIdApiReq, NodeConnectionContext.class,
				"获取态势系统链路数据失败");
	}

	@Override
	public NodeWeakInfoContext getNodeWeakInfo(NodeIdApiReq nodeIdApiReq) {
		return HttpClientUtil.tsPost(NODE_WEAK_INFO_URL, nodeIdApiReq, NodeWeakInfoContext.class,
				"获取态势系统节点脆弱性数据失败");
	}

	@Override
	public ListRsp<NodeWeakContext> getNodeWeakList(NodeIdPageReq nodeIdPageReq) {
		LgNodeWeakRsp rsp = HttpClientUtil.tsPost(NODE_WEAK_LIST_URL, nodeIdPageReq,
				LgNodeWeakRsp.class, "获取态势系统节点脆弱性列表失败");
		ListRsp<NodeWeakContext> res = rsp.convert();
		res.setList(rsp.getList());
		return res;
	}

	private String getQueryTime(String featureId) {

		if (StringUtils.isBlank(featureId)) {
			return null;
		}

		Date currentLastTime = queryTimeCache.getIfPresent(featureId);
		return TimeUtil.format1(currentLastTime);
	}

	@Override
	public List<TaskEventContext> getTaskEventList(TaskEventReq taskEventReq, String featureId) {
		taskEventReq.setQueryTime(getQueryTime(featureId));
		String url = HttpClientUtil.convertRawUrl(taskEventReq, TASK_EVENT_URL);
		String rsp = HttpClientUtil.tsGet(url, String.class, "获取态势系统任务动效失败");
		List<TaskEventContext> res = JsonUtil.toJsonObjectArray(rsp, TaskEventContext.class);

		// 更新查询时间缓存
		if (res != null && !res.isEmpty()) {
			queryTimeCache.put(featureId, res.get(0).getEventTime());
		}

		return res;
	}

	@Override
	public List<AdEventContext> getAdEventList(EventReq eventReq, String featureId) {
		eventReq.setQueryTime(getQueryTime(featureId));
		String rsp = HttpClientUtil.tsPost(AD_EVENT_URL, eventReq, String.class, "获取态势系统攻防动效失败");
		List<AdEventContext> res = JsonUtil.toJsonObjectArray(rsp, AdEventContext.class);

		// 更新查询时间缓存
		if (res != null && !res.isEmpty()) {
			queryTimeCache.put(featureId, res.get(0).getEventTime());
		}
		return res;
	}

	@Override
	public List<NodeEventContext> getNodeEventList(EventReq eventReq, String featureId) {
		String rsp = HttpClientUtil.tsPost(NODE_STATUS_URL, eventReq, String.class,
				"获取态势系统节点状态动效失败");
		List<NodeEventContext> res = JsonUtil.toJsonObjectArray(rsp, NodeEventContext.class);
		return res;
	}

	@Override
	public TopoContext getTopo(TopoReq topoReq, Boolean adjust) {

		ThirdPartyTopoBo topoBo = getXdTopo(topoReq);

		TopoContext topoContext = topoBo.convert(adjust,
				remoteOperationService.getModelTypeListMap(),
				remoteOperationService.getModelListMap());

		// 二次处理区域数据
		// topoBo.setAreas(topoContext);

		return topoContext;
	}

	@Override
	public ThirdPartyTopoBo getXdTopo(TopoReq topoReq) {
		String url = HttpClientUtil.convertRawUrl(topoReq, XD_TOPO_URL);
		return HttpClientUtil.tsGet(url, ThirdPartyTopoBo.class, "获取想定系统拓扑数据失败");

	}

	@Override
	public List<SchemeRspBo> getSchemeList(SituationTaskReq req) {
		String rsp = HttpClientUtil.tsGet(XD_SCHEME_LIST + req.toQuery(), String.class,
				"获取想定系统可视化方案列表失败");
		List<SchemeRspBo> res = JsonUtil.toJsonObjectArray(rsp, SchemeRspBo.class);
		return res;
	}

	@Override
	public SchemeVisualizationBo getScheme(String schemeId) {
		if (StringUtils.isBlank(schemeId)) {
			throw new BusinessException(ErrorCode.EMPTY_SCHEME_ID);
		}
		String targetUrl = XD_SCHEME + "?schemeId=" + schemeId;
		SchemeVisualizationBo rsp = HttpClientUtil.tsGet(targetUrl, SchemeVisualizationBo.class,
				"获取想定系统可视化方案详情失败");
		return rsp;
	}

	@Override
	public List<TopoHistoryBo> getTopoHistory(SituationTaskReq req) {
		String rsp = HttpClientUtil.tsGet(XD_TOPO_HISTORY + req.toQuery(), String.class,
				"获取想定系统历史拓扑数据失败");
		List<TopoHistoryBo> res = Lists.newArrayList();

		try {

			res = JsonUtil.toJsonObjectArray(rsp, TopoHistoryBo.class);

		} catch (JSONException e) {
			TopoHistoryBo topoHistoryBo = JsonUtil.toJsonObject(rsp, TopoHistoryBo.class);
			res.add(topoHistoryBo);
		}

		return res;
	}

	@Override
	public Boolean releaseScreen(String schemeId, String dashboardId) {
		ReleaseScreenBo releaseScreenBo = new ReleaseScreenBo();
		releaseScreenBo.setSchemeId(schemeId);
		releaseScreenBo.setDashboardId(dashboardId);

		BooleanRes rsp = HttpClientUtil.tsPost(XD_RELEASE_SCREEN, releaseScreenBo, BooleanRes.class,
				"想定系统发布大屏失败");
		return rsp.getResult();
	}

	@Override
	public Boolean releaseScreen(StatusOrderContext statusOrderContext) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg",
				JsonUtil.toJsonString(statusOrderContext, PropertyNamingStrategy.SnakeCase));
		jsonObject.put("task_id", statusOrderContext.getTaskId());

		BooleanRes rsp = HttpClientUtil.tsPost(TS_RELEASE_SCREEN, jsonObject, BooleanRes.class,
				"想定系统发布大屏失败");
		return rsp.getResult();
	}

	private List<String> getNodeIdList(NodeListReq nodeListReq) {
		String url = HttpClientUtil.convertRawUrl(nodeListReq, XD_NODE_LIST);
		String rsp = HttpClientUtil.tsGet(url, String.class, "获取想定系统平台下节点列表数据失败");
		List<String> res = JsonUtil.toJsonObjectArray(rsp, String.class);
		return res;
	}

	@Override
	public String getRunId(String subjectId) {
		RunContext runContext = HttpClientUtil.tsGet(PG_GET_RUN_ID + subjectId, RunContext.class,
				"获取评估系统评估实例ID失败");
		return runContext.getRunId();
	}

	@Override
	public List<NodeBasicInfoContext> getSimilarNodes(NodeIdApiReq nodeIdApiReq) {
		String rsp = HttpClientUtil.tsPost(TS_NODE_SIMILAR, nodeIdApiReq, String.class,
				"获取态势系统相似节点数据失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeBasicInfoContext.class);
	}

	@Override
	public TopoContext getCrossLayerTopo(TaskBaseReq taskBaseReq) {
		LayerTopoContext rep = HttpClientUtil.tsPost(TS_LAYER_TOPO, taskBaseReq,
				LayerTopoContext.class, "获取态势系统跨层分析拓扑失败");
		return rep.convert();
	}

	@Override
	public List<LayerEventContext> getCrossLayerEvent(TaskBaseReq taskBaseReq) {
		EventLayerRsp eventLayerRsp = HttpClientUtil.tsPost(TS_LAYER_EVENT, taskBaseReq,
				EventLayerRsp.class, "获取态势系统跨层分析事件失败");
		return eventLayerRsp.getEvents();
	}

	@Override
	public EventAxisRsp getEventAxis(TaskBaseReq taskBaseReq) {
		return HttpClientUtil.tsPost(TS_EVENT_AXIS, taskBaseReq, EventAxisRsp.class, "获取态势系统事件轴失败");
	}

	@Override
	public List<NodeThreatenedDistributeContext>
			getNodeThreatenedDistribute(TaskBaseReq taskBaseReq) {
		String rsp = HttpClientUtil.tsPost(TS_DISTRIBUTE_THREATENED, taskBaseReq, String.class,
				"获取态势系统受威胁节点分布数据失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeThreatenedDistributeContext.class);
	}

	@Override
	public List<NodeAttackStatisticsContext> getNodeRankAttack(TaskSizeReq taskSizeReq) {
		String rsp = HttpClientUtil.tsPost(TS_NODE_RANK_ATTACK, taskSizeReq, String.class,
				"获取态势系统攻击节点排名数据失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeAttackStatisticsContext.class);
	}

	@Override
	public List<UserActivityContext> getUserActivity(TaskSizeReq taskSizeReq) {
		String rsp = HttpClientUtil.tsPost(TS_USER_ACTIVITY, taskSizeReq, String.class,
				"获取态势系统用户活跃度数据失败");
		return JsonUtil.toJsonObjectArray(rsp, UserActivityContext.class);
	}

	@Override
	public FlowStatisticsContext getFlowStatistics(TaskBaseReq taskBaseReq) {
		return HttpClientUtil.tsPost(TS_FLOW_STATISTICS, taskBaseReq, FlowStatisticsContext.class,
				"获取态势系统流量统计数据失败");
	}

	@Override
	public List<NodeFlowRankContext> getNodeFlowRank(TaskSizeReq taskSizeReq) {
		String rsp = HttpClientUtil.tsPost(TS_FLOW_NODE_RANK, taskSizeReq, String.class,
				"获取态势系统节点流量排名失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeFlowRankContext.class);
	}

	@Override
	public FlowTrendContext getFlowTrend(TaskBaseReq taskBaseReq) {
		return HttpClientUtil.tsPost(TS_FLOW_TREND, taskBaseReq, FlowTrendContext.class,
				"获取态势系统流量趋势数据失败");
	}

	@Override
	public List<StatusStatisticsContext> getNodeStatusStatistics(TaskSizeReq taskSizeReq) {
		String rsp = HttpClientUtil.tsPost(TS_NODE_STATISTICS_STATUS, taskSizeReq, String.class,
				"获取态势系统节点状态统计数据失败");
		return JsonUtil.toJsonObjectArray(rsp, StatusStatisticsContext.class);
	}

	@Override
	public List<NodeCriticalDegreeContext> getNodeCriticalDegree(TaskSizeReq taskSizeReq) {
		String rsp = HttpClientUtil.tsPost(TS_NODE_CRITICAL_DEGREE, taskSizeReq, String.class,
				"获取态势系统节点重要度数据失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeCriticalDegreeContext.class);
	}

	@Override
	public List<StatusStatisticsContext> getLinkStatusStatistics(TaskBaseReq taskBaseReq) {
		String rsp = HttpClientUtil.tsPost(TS_LINK_STATISTICS_STATUS, taskBaseReq, String.class,
				"获取态势系统链路状态统计数据失败");
		return JsonUtil.toJsonObjectArray(rsp, StatusStatisticsContext.class);
	}

	@Override
	public List<NodeMigrationContext> getNodeMigrations(EventReq eventReq, String featureId) {
		eventReq.setQueryTime(getQueryTime(featureId));
		String rsp = HttpClientUtil.tsPost(TS_NODE_MIGRATION, eventReq, String.class,
				"获取态势系统节点迁移事件失败");
		List<NodeMigrationContext> res = JsonUtil.toJsonObjectArray(rsp,
				NodeMigrationContext.class);

		// 更新查询时间缓存
		if (res != null && !res.isEmpty()) {
			queryTimeCache.put(featureId, res.get(0).getTime());
		}
		return res;
	}

	@Override
	public List<NodeProtocolContext> getNodeProtocols(TaskBaseReq taskBaseReq) {
		String rsp = HttpClientUtil.tsPost(TS_NODE_PROTOCOL, taskBaseReq, String.class,
				"获取态势系统节点协议数据失败");
		return JsonUtil.toJsonObjectArray(rsp, NodeProtocolContext.class);
	}

	@Override
	public RangeData getRangeData(String bcId) {
		String targetUrl = TS_ZH_STAT_NUM;
		return  HttpClientUtil.rawGet(targetUrl+bcId, RangeData.class);
	}

}
