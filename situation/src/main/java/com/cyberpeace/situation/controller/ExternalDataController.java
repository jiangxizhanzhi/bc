package com.cyberpeace.situation.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.constant.TaskStatus;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.*;
import com.cyberpeace.integratation.model.situation.layer.LayerEventContext;
import com.cyberpeace.integratation.model.situation.req.*;
import com.cyberpeace.integratation.model.situation.res.EventAxisRsp;
import com.cyberpeace.integratation.model.situation.res.EventListRsp;
import com.cyberpeace.integratation.service.RemoteOperationService;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.integratation.service.bo.situation.*;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.*;
import com.cyberpeace.situation.dao.entity.TopoConfig;
import com.cyberpeace.situation.dao.mapper.TopoConfigMapper;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.SituationGisService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.ThirdPartyService;
import com.cyberpeace.situation.service.context.site.CenterContext;
import com.cyberpeace.situation.service.context.site.RangeContext;
import com.cyberpeace.situation.service.context.site.StationContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "外部数据")
@RestController
// @RequiresAuthentication
@RequestMapping("/third/")
public class ExternalDataController {

	@Autowired
	private ThirdPartyService thirdPartyService;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private RemoteOperationService remoteOperationService;

	@Autowired
	private TopoConfigMapper topoConfigMapper;

	@Autowired
	private SituationGisService situationGisService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@ApiOperation("获取攻防事件类型列表")
	@GetMapping("/event_type/list/")
	public BaseRsp<List<EventTypeRes>> getEventTypes() {

		return new BaseRsp<>(thirdPartyService.getEventTypes());
	}

	@ApiOperation("获取LG中心列表")
	@GetMapping("/center/list/")
	public BaseRsp<List<CenterContext>> getCenters() {

		return new BaseRsp<>(thirdPartyService.getCenters());
	}

	@ApiOperation("获取分靶场列表")
	@GetMapping("/range/list/")
	public BaseRsp<List<RangeContext>> getSubRanges(@ApiParam(name = "type", value = "靶场类型",
			required = false) @RequestParam(value = "type", required = false) String type) {

		return new BaseRsp<>(thirdPartyService.getRanges(type));
	}

	@ApiOperation("获取场站列表")
	@GetMapping("/station/list/")
	public BaseRsp<List<StationContext>>
			getStations(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {

		return new BaseRsp<>(thirdPartyService.getStations(subRangeId));
	}

	@ApiOperation("获取特定事件类型列表")
	@GetMapping("/event/list/")
	public BaseRsp<List<EventListRsp>> getEvents() {
		return new BaseRsp<>(thirdPartyService.getTypicalEvents());
	}

	@ApiOperation("获取分靶场详情")
	@GetMapping("/range/")
	public BaseRsp<SubRangeInfoContext>
			getSubRangeInfo(@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = true) @RequestParam(value = "sub_range_id") String subRangeId) {
		SubRangeInfoContext subRangeInfoContext = remoteSituationService
				.getSubRangeInfo(subRangeId);
		subRangeInfoContext.setTitle(subRangeInfoContext.getSubRangeName() + "综合态势");

		return new BaseRsp<>(subRangeInfoContext);
	}

	@ApiOperation("获取LG中心详情")
	@GetMapping("/center/")
	public BaseRsp<CenterInfoContext> getCenterInfo(@ApiParam(name = "center_id", value = "LG中心ID",
			required = true) @RequestParam(value = "center_id") String centerId) {
		CenterInfoContext centerInfoContext = remoteSituationService.getCenterInfo(centerId);

		return new BaseRsp<>(centerInfoContext);
	}

	@ApiOperation("获取平台列表")
	@GetMapping("/platform/list/")
	public BaseRsp<List<PlatformContext>> getPlatforms(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId,
			@ApiParam(name = "scheme_id", value = "可视化方案ID", required = false) @RequestParam(
					value = "scheme_id", required = false) String schemeId) {
		List<PlatformContext> platformList = remoteSituationService.getPlatformList(taskId,
				subjectId, subRangeId, schemeId);

		return new BaseRsp<>(platformList);
	}

	@ApiOperation("获取平台连线列表")
	@GetMapping("/platform/link/list/")
	public BaseRsp<List<PlatformLinkContext>> getPlatformLinks(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId,
			@ApiParam(name = "scheme_id", value = "可视化方案ID", required = false) @RequestParam(
					value = "scheme_id", required = false) String schemeId) {
		List<PlatformLinkContext> platformLinkList = remoteSituationService
				.getPlatformLinkList(taskId, subjectId, subRangeId, schemeId);
		return new BaseRsp<>(platformLinkList);
	}

	@ApiOperation("获取任务类型字典")
	@GetMapping("/task_type/list/")
	public BaseRsp<List<HashMap<String, String>>>
			getTaskType(@ApiParam(name = "all", value = "是否返回全部类型",
					required = false) @RequestParam(value = "all", required = false) Boolean all) {

		if (all == null || !all) {
			return new BaseRsp<>(TaskType.getTaskTypeMap(true));
		}

		return new BaseRsp<>(TaskType.getTaskTypeMap(false));
	}

	@ApiOperation("获取任务状态字典")
	@GetMapping("/task_status/list/")
	public BaseRsp<List<HashMap<String, String>>> getTaskStatus() {

		return new BaseRsp<>(TaskStatus.getTaskStatusMap());
	}

	@ApiOperation("获取网络信息")
	@PostMapping("/network/")
	public BaseRsp<NetworkInfoContext>
			getNetworkInfo(@RequestBody @Validated PlatformReq platformReq) {

		return new BaseRsp<>(remoteSituationService.getNetworkInfo(platformReq));
	}

	@ApiOperation("获取任务列表")
	@GetMapping("/task/list/")
	public BaseRsp<ListRsp<TaskContext>> getTaskList(TaskListPageReq taskListPageReq) {
		return new BaseRsp<>(remoteSituationService
				.getTaskList(BeanUtils.copy(taskListPageReq, TaskListReq.class)));
	}

	@ApiOperation("获取科目列表")
	@GetMapping("/subject/list/")
	public BaseRsp<List<SubjectContext>> getSubjectList(SubjectListReq subjectListReq) {
		return new BaseRsp<>(remoteSituationService.getSubjectList(subjectListReq));
	}

	@ApiOperation("获取节点基础信息")
	@PostMapping("/node/info/")
	public BaseRsp<NodeInfoContext> getNodeInfo(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {
		return new BaseRsp<>(remoteSituationService.getNodeInfo(nodeIdApiReq));
	}

	@ApiOperation("获取相似节点")
	@PostMapping("/node/similar_nodes/")
	public BaseRsp<List<NodeBasicInfoContext>>
			getSimilarNodeList(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {
		List<NodeBasicInfoContext> rsp = remoteSituationService.getSimilarNodes(nodeIdApiReq);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取节点行为数据")
	@GetMapping("/node/action/")
	public BaseRsp<ListRsp<NodeActionContext>> getNodeAction(NodeActionPageReq nodeActionPageReq) {

		return new BaseRsp<>(remoteSituationService
				.getNodeAction(BeanUtils.copy(nodeActionPageReq, NodeActionReq.class)));
	}

	@ApiOperation("获取节点行为趋势")
	@PostMapping("/node/trend/")
	public BaseRsp<List<NodeTrendContext>>
			getNodeTrend(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getNodeTrend(nodeIdApiReq));
	}

	@ApiOperation("获取用户行为数据")
	@GetMapping("/user/action/")
	public BaseRsp<ListRsp<UserActionContext>> getUserAction(NodePageReq nodeListApiReq) {

		return new BaseRsp<>(remoteSituationService
				.getUserAction(BeanUtils.copy(nodeListApiReq, NodeIdPageReq.class)));
	}

	@ApiOperation("获取用户行为趋势")
	@PostMapping("/user/trend/")
	public BaseRsp<List<UserTrendContext>>
			getUserTrend(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getUserTrend(nodeIdApiReq));
	}

	@ApiOperation("获取平台节点链路状态信息")
	@PostMapping("/node/connection/")
	public BaseRsp<NodeConnectionContext>
			getNodeConnection(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getNodeConnection(nodeIdApiReq));
	}

	@ApiOperation("获取平台节点脆弱性分析信息")
	@PostMapping("/node/weak/info/")
	public BaseRsp<NodeWeakInfoContext>
			getNodeWeakInfo(@RequestBody @Validated NodeIdApiReq nodeIdApiReq) {

		return new BaseRsp<>(remoteSituationService.getNodeWeakInfo(nodeIdApiReq));
	}

	@ApiOperation("获取平台节点脆弱性列表")
	@GetMapping("/node/weak/list/")
	public BaseRsp<ListRsp<NodeWeakContext>> getNodeWeakList(NodePageReq nodePageReq) {

		return new BaseRsp<>(remoteSituationService
				.getNodeWeakList(BeanUtils.copy(nodePageReq, NodeIdPageReq.class)));
	}

	@ApiOperation("获取任务事件列表")
	@PostMapping("/event/task/")
	public BaseRsp<List<TaskEventContext>>
			getTaskEventList(@RequestBody @Validated TaskEventMsgReq taskEventReq) {

		return new BaseRsp<>(remoteSituationService.getTaskEventList(taskEventReq, null));
	}

	@ApiOperation("获取攻防事件列表")
	@PostMapping("/event/ad/")
	public BaseRsp<List<AdEventContext>>
			getAdEventList(@RequestBody @Validated EventMsgReq eventReq) {

		return new BaseRsp<>(remoteSituationService.getAdEventList(eventReq, null));
	}

	@ApiOperation("获取节点状态列表")
	@PostMapping("/event/node/")
	public BaseRsp<List<NodeEventContext>>
			getNodeEventList(@RequestBody @Validated EventMsgReq eventReq) {

		return new BaseRsp<>(remoteSituationService.getNodeEventList(eventReq, null));
	}

	@ApiOperation("获取第三方拓扑数据")
	@PostMapping("/topo/")
	public BaseRsp<TopoContext> getTopo(@RequestBody @Validated TopoReq topoReq) {

		// 直接返回样例拓扑
		if ("demo".equals(topoReq.getEnvId())) {
			return new BaseRsp<>(JsonUtil.toJsonObject(
					JsonUtil.toJsonString(getJsonByName("demoTopo")), TopoContext.class));
		}

		if ("test".equals(topoReq.getEnvId())) {
			return new BaseRsp<>(JsonUtil.toJsonObject(
					JsonUtil.toJsonString(getJsonByName("demo5000")), TopoContext.class));
		}

		TopoContext topoContext = remoteSituationService.getTopo(topoReq, false);

		// 处理编排后的拓扑坐标
		if (StringUtils.isNotBlank(topoReq.getEnvId())) {
			TopoConfig topoConfig = topoConfigMapper.selectByEnvId(topoReq.getEnvId());
			if (topoConfig != null) {
				JSONObject json = JsonUtil.toJsonObject(topoConfig.getConfig(), JSONObject.class);
				Map<String, ElementSlimContext> elementSlimContextMap = JSONObject.parseObject(
						json.toJSONString(),
						new TypeReference<Map<String, ElementSlimContext>>() {});
				topoContext.getElements().forEach((key, element) -> {
					element.setPosition(elementSlimContextMap
							.getOrDefault(key, new ElementSlimContext()).getPosition());
				});
			}
		}

		return new BaseRsp<>(topoContext);
	}

	private JSONObject getJsonByName(String name) {
		InputStream inputStream = getClass().getResourceAsStream("/" + name + ".json");
		return JsonUtil.toJsonObject(inputStream, JSONObject.class).getJSONObject("data");
	}

	@ApiOperation("想定-获取任务下可视化方案列表")
	@GetMapping("/scheme/list")
	public BaseRsp<List<SchemeRspBo>> getSchemeList(SituationTaskReq req) {
		List<SchemeRspBo> res = remoteSituationService.getSchemeList(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("想定-获取任务/科目可视化方案")
	@GetMapping("/scheme/visualization")
	public BaseRsp<SchemeVisualizationBo> getSchemeVisualization(
			@ApiParam(value = "可视化方案id", name = "scheme_id") String scheme_id) {
		SchemeVisualizationBo scheme = remoteSituationService.getScheme(scheme_id);
		return new BaseRsp<>(scheme);
	}

	@ApiOperation("配管-获取科目拓扑变更记录")
	@GetMapping("/task/topo/history")
	public BaseRsp<List<TopoHistoryBo>> getTopoHistory(SituationTaskReq req) {
		List<TopoHistoryBo> res = remoteSituationService.getTopoHistory(req);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取站点列表")
	@GetMapping("/bc/list/")
	public BaseRsp<List<BcContext>>
			getBcList(@ApiParam(value = "站点类型", name = "type") String type) {
		List<BcContext> res = remoteOperationService.getBcList(type);
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取模型列表")
	@GetMapping("/model/list/")
	public BaseRsp<Map<String, ModelContext>> getModelList() {
		Map<String, ModelContext> res = remoteOperationService.getModelListMap();
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取模型类型列表")
	@GetMapping("/model/type/list/")
	public BaseRsp<Map<String, ModelTypeContext>> getModelTypeList() {
		Map<String, ModelTypeContext> res = remoteOperationService.getModelTypeListMap();
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取评估页面链接")
	@GetMapping("/evaluate/url/")
	public BaseRsp<EvaluateUrlRes> getEvaluateUrl(
			@ApiParam(name = "subject_id", value = "科目ID", required = true) @RequestParam(
					value = "subject_id", required = true) String subjectId,
			@ApiParam(name = "role", value = "角色", required = true) @RequestParam(value = "role",
					required = true) String role) {
		String runId = remoteSituationService.getRunId(subjectId);
		return new BaseRsp<>(new EvaluateUrlRes(runId, role));
	}

	@ApiOperation("获取跨层分析拓扑")
	@PostMapping("/topo/cross_layer/")
	public BaseRsp<TopoContext> getCrossLayerTopo(@RequestBody @Validated TaskBaseReq taskBaseReq) {
		TopoContext rsp = remoteSituationService.getCrossLayerTopo(taskBaseReq);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取跨层分析事件")
	@PostMapping("/event/cross_layer/")
	public BaseRsp<List<LayerEventContext>>
			getCrossLayerEvent(@RequestBody @Validated TaskDashboardReq taskBaseReq) {
		List<LayerEventContext> rsp = remoteSituationService.getCrossLayerEvent(taskBaseReq);
		// 推送WS消息
		WsMessage<List<LayerEventContext>> wsMessage = new WsMessage<>(
				MessageType.LayerEvent.getDesc(), rsp);
		situationHandlerService.pushDataToDashboard(wsMessage, taskBaseReq.getDashboardId(),
				taskBaseReq.getFeatureId());
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取事件轴")
	@PostMapping("/event/axis/")
	public BaseRsp<EventAxisRsp> getEventAxis(@RequestBody @Validated TaskBaseReq taskBaseReq) {
		EventAxisRsp rsp = remoteSituationService.getEventAxis(taskBaseReq);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("获取7层分析应用层数据")
	@PostMapping("/layer/data/app/")
	public BaseRsp<LayerDataAppRes> getLayerDataApp(@RequestBody @Validated TaskOsiReq taskOsiReq) {

		LayerDataAppRes layerDataAppRes = new LayerDataAppRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);

		taskSizeReq.setSize(10);
		layerDataAppRes.setUserActivity(remoteSituationService.getUserActivity(taskSizeReq));
		taskSizeReq.setSize(5);
		layerDataAppRes
				.setNodeAttackStatistics(remoteSituationService.getNodeRankAttack(taskSizeReq));
		layerDataAppRes.setThreatenedDistribute(
				remoteSituationService.getNodeThreatenedDistribute(taskOsiReq));
		return new BaseRsp<>(layerDataAppRes);
	}

	@ApiOperation("获取7层分析传输层数据")
	@PostMapping("/layer/data/transport/")
	public BaseRsp<LayerDataTransportRes>
			getLayerDataTransport(@RequestBody @Validated TaskOsiReq taskOsiReq) {
		LayerDataTransportRes layerDataTransportRes = new LayerDataTransportRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);
		taskSizeReq.setSize(5);
		layerDataTransportRes.setNodeFlowRank(remoteSituationService.getNodeFlowRank(taskSizeReq));
		layerDataTransportRes
				.setFlowStatistics(remoteSituationService.getFlowStatistics(taskOsiReq));
		layerDataTransportRes.setFlowTrend(remoteSituationService.getFlowTrend(taskOsiReq));
		return new BaseRsp<>(layerDataTransportRes);
	}

	@ApiOperation("获取7层分析网络层数据")
	@PostMapping("/layer/data/net/")
	public BaseRsp<LayerDataNetRes> getLayerDataNet(@RequestBody @Validated TaskOsiReq taskOsiReq) {
		LayerDataNetRes layerDataNetRes = new LayerDataNetRes();
		TaskSizeReq taskSizeReq = BeanUtils.deepCopy(taskOsiReq, TaskSizeReq.class);
		taskSizeReq.setSize(5);
		TopoReq topoReq = BeanUtils.deepCopy(taskOsiReq, TopoReq.class);
		ThirdPartyTopoBo topo = remoteSituationService.getXdTopo(topoReq);
		layerDataNetRes.setNetNum(topo.getNetworks().size());
		layerDataNetRes.setNodeStatus(remoteSituationService.getNodeStatusStatistics(taskSizeReq));
		layerDataNetRes
				.setNodeCriticalDegree(remoteSituationService.getNodeCriticalDegree(taskSizeReq));
		layerDataNetRes.setNodeTypeDistribute(remoteSituationService.getNodeTypeStatistics(topo));
		return new BaseRsp<>(layerDataNetRes);
	}

	@ApiOperation("获取7层分析物理层数据")
	@PostMapping("/layer/data/physics/")
	public BaseRsp<LayerDataPhysicsRes>
			getLayerDataPhysics(@RequestBody @Validated TaskOsiReq taskOsiReq) {
		LayerDataPhysicsRes layerDataPhysicsRes = new LayerDataPhysicsRes();
		TopoReq topoReq = BeanUtils.deepCopy(taskOsiReq, TopoReq.class);
		ThirdPartyTopoBo topo = remoteSituationService.getXdTopo(topoReq);
		layerDataPhysicsRes
				.setConnectionTypeDistribute(remoteSituationService.getLinkTypeStatistics(topo));
		layerDataPhysicsRes
				.setConnectionStatus(remoteSituationService.getLinkStatusStatistics(taskOsiReq));
		layerDataPhysicsRes.setGeoDistribution(getGeoDistribution(taskOsiReq));
		return new BaseRsp<>(layerDataPhysicsRes);
	}

	@ApiOperation("获取7层分析通用数据")
	@PostMapping("/layer/data/common/")
	public BaseRsp<LayerDataCommonRes>
			getLayerDataCommon(@RequestBody @Validated TaskOsiReq taskOsiReq) {
		LayerDataCommonRes layerDataCommonRes = new LayerDataCommonRes();
		layerDataCommonRes.setGeoDistribution(getGeoDistribution(taskOsiReq));
		layerDataCommonRes.setProtocolStatus(remoteSituationService.getNodeProtocols(taskOsiReq));
		return new BaseRsp<>(layerDataCommonRes);
	}

	private List<String> getGeoDistribution(TaskOsiReq taskOsiReq) {
		// 获取节点地理分布
		List<String> geo = Lists.newArrayList();
		List<PlatformContext> platformList = remoteSituationService.getPlatformList(
				taskOsiReq.getTaskId(), taskOsiReq.getSubjectId(), taskOsiReq.getSubRangeId(),
				taskOsiReq.getSchemeId());
		platformList.forEach(platformContext -> {
			geo.add(situationGisService.getProvince(Float.valueOf(platformContext.getLongitude()),
					Float.valueOf(platformContext.getLatitude())));
		});
		return geo.stream().distinct().collect(Collectors.toList());
	}
}
