package com.cyberpeace.situation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.integratation.model.situation.AdEventContext;
import com.cyberpeace.integratation.model.situation.NodeEventContext;
import com.cyberpeace.integratation.model.situation.TaskEventContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.DynamicParameter;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.TaskStatus;
import com.cyberpeace.situation.constant.TaskType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.entity.DynamicEffectsLibrary;
import com.cyberpeace.situation.dao.entity.DynamicEffectsMapping;
import com.cyberpeace.situation.dao.entity.EventTypeContext;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.dao.mapper.DynamicEffectsLibraryMapper;
import com.cyberpeace.situation.dao.mapper.EventTypeContextMapper;
import com.cyberpeace.situation.dao.mapper.SituationDynamicEffectMappingMapper;
import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.ViewAngle;
import com.cyberpeace.situation.model.ws.WsEventAd;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.context.JtTaskEventContext;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势大屏")
@RestController
@RequestMapping("/dashboard/")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private DashboardAttributeMapper dashboardAttributeMapper;

	@Autowired
	private RemoteSituationService remoteSituationService;

	@Autowired
	private SituationDynamicEffectMappingMapper situationDynamicEffectMappingMapper;

	@Autowired
	private EventTypeContextMapper eventTypeContextMapper;

	@Autowired
	private DynamicEffectsLibraryMapper dynamicEffectsLibraryMapper;

	@ApiOperation("获取大屏类型属性（前台）")
	@GetMapping("/id/")
	public BaseRsp<DashboardIdRes> getDashboardId(
			@ApiParam(name = "task_id", value = "任务ID",
					required = true) @RequestParam(value = "task_id") String taskId,
			@ApiParam(name = "subject_id", value = "科目ID", required = false) @RequestParam(
					value = "subject_id", required = false) String subjectId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID", required = false) @RequestParam(
					value = "sub_range_id", required = false) String subRangeId,
			@ApiParam(name = "task_status", value = "任务状态",
					required = true) @RequestParam(value = "task_status") String taskStatus,
			@ApiParam(name = "task_type", value = "任务类型",
					required = true) @RequestParam(value = "task_type") String taskType) {

		// 校验状态
		if (!TaskStatus.RWZX.getName().equals(taskStatus)) {
			throw new BusinessException(SituationErrorCode.TASK_NOT_RUNNING);
		}

		// 校验类型
		TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getDesc().equals(taskType)).findFirst().orElse(TaskType.QB);

		if (!type.getSupport()) {
			throw new BusinessException(SituationErrorCode.TASK_TYPE_NOT_SUPPORTED, taskType);
		}

		DashboardIdRes dashboardId = dashboardService.getAttributeByTaskId(taskId, subjectId);

		return new BaseRsp<>(dashboardId);

	}

	@ApiOperation("获取大屏类型属性（后台）")
	@GetMapping("/attribute/type/")
	public BaseRsp<DashboardIdRes>
			getDashboardTypeAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getAttributeByDashboardId(dashboardId));
	}

	@ApiOperation("获取大屏基本属性")
	@GetMapping("/basic_attribute/")
	public BaseRsp<DashboardBasicAttributeRes>
			getDashboardBasicAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getDashboardBasicAttribute(dashboardId));
	}

	@ApiOperation("获取动态字段列表")
	@GetMapping("/dynamic_parameter/")
	public BaseRsp<List<JSONObject>> getDynamicParameter() {
		return new BaseRsp<>(DynamicParameter.getTaskDynamicParameter());
	}

	@ApiOperation("推送大屏属性变更消息")
	@PostMapping("/change_notice/")
	public BaseRsp<Boolean>
			pushDashboardChange(@RequestBody @Validated DashboardChangeReq dashboardChangeReq) {
		WsMessage<JSONObject> wsMessage = new WsMessage<>(dashboardChangeReq.getCode(),
				dashboardChangeReq.getData());
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardChangeReq.getDashboardId(),
				dashboardChangeReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("推送返回首页消息")
	@PostMapping("/back_home/")
	public BaseRsp<Boolean>
			pushBackHomeNotice(@RequestBody @Validated DashboardBasicReq dashboardBasicReq) {

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper
				.getByResourceId("e5c71717f6b44152943ef28a15dd4040");
		JSONObject data = JsonUtil.java2JsonObject(dashboardAttribute);
		ViewAngle viewAngle = JsonUtil.toJsonObject(data.getString("viewAngle"), ViewAngle.class);
		data.put("longitude", viewAngle.getLongitude());
		data.put("latitude", viewAngle.getLatitude());
		data.put("altitude", viewAngle.getAltitude());

		WsMessage<JSONObject> wsMessage = new WsMessage<>(MessageType.BackToHome.getDesc(), data);
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardBasicReq.getDashboardId(),
				dashboardBasicReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取最新的任务事件")
	@PostMapping("/event/task/")
	public BaseRsp<Boolean> getTaskEvent(@RequestBody @Validated TaskEventMsgReq taskEventReq) {

		List<TaskEventContext> taskEventContexts = remoteSituationService
				.getTaskEventList(taskEventReq, taskEventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard("", taskEventReq.getDashboardId(),
				taskEventReq.getFeatureId(), taskEventContexts, null, null);

		return new BaseRsp<>(true);

	}

	@ApiOperation("获取最新的任务解题事件")
	@GetMapping("/event/task/jt/")
	public BaseRsp<Boolean> getJtTaskEvent(EventQueryReq eventQueryReq) {

		JtTaskEventContext jtTaskEventContext = new JtTaskEventContext();
		WsMessage<JtTaskEventContext> wsMessage = new WsMessage<>(MessageType.TaskEventJt.getDesc(),
				jtTaskEventContext);
		String sceneId = eventQueryReq.getDashboardId() + "_" + eventQueryReq.getFeatureId();
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);

		return new BaseRsp<>(true);
	}

	@ApiOperation("获取最新的任务攻防事件")
	@PostMapping("/event/task/ad/")
	public BaseRsp<Boolean> getTaskAdEvent(@RequestBody @Validated EventMsgReq eventReq) {

		List<AdEventContext> adEventContexts = remoteSituationService.getAdEventList(eventReq,
				eventReq.getFeatureId());
		List<WsEventAd> eventAds = eventContext2EventAd(adEventContexts);
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEventAd.getDesc(), eventReq.getDashboardId(),
				eventReq.getFeatureId(), eventAds, null, null);

		return new BaseRsp<>(true);
	}

	@ApiOperation("获取最新的节点状态事件")
	@PostMapping("/event/task/node/")
	public BaseRsp<Boolean> getTaskNodeEvent(@RequestBody @Validated EventMsgReq eventReq) {

		List<NodeEventContext> nodeEventContexts = remoteSituationService.getNodeEventList(eventReq,
				eventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard("", eventReq.getDashboardId(),
				eventReq.getFeatureId(), nodeEventContexts, null, null);

		return new BaseRsp<>(true);
	}

	private List<WsEventAd> eventContext2EventAd(List<AdEventContext> contextList) {
		List<WsEventAd> adList = Lists.newArrayList();
		if (CollectionUtils.isEmpty(contextList)) {
			return adList;
		}
		// collect types
		Set<String> subTypes = contextList.stream().map(AdEventContext::getEventSubType)
				.collect(Collectors.toSet());
		// get code
		List<EventTypeContext> typeContextList = eventTypeContextMapper.selectBatchByName(subTypes);
		if (typeContextList.size() != subTypes.size()) {
			throw new BusinessException(SituationErrorCode.UNKNOWN_EVENT_TYPE);
		}
		Map<String, String> nameCodeMap = StreamApiUtils.toMap(typeContextList,
				EventTypeContext::getName, EventTypeContext::getCode);
		Map<String, EventTypeContext> codeTypeMap = StreamApiUtils.toMap(typeContextList,
				EventTypeContext::getCode, context -> context);
		// 查找映射动效
		List<DynamicEffectsMapping> mappingEffectList = situationDynamicEffectMappingMapper
				.selectBatchByType(codeTypeMap.keySet());
		Map<String, DynamicEffectsMapping> mappingEffectMap = StreamApiUtils
				.toMap(mappingEffectList, DynamicEffectsMapping::getAttackType, effect -> effect);

		// 查询动效列表
		List<String> dynamicEffectNames = Lists.newArrayList();
		mappingEffectList.forEach(dynamicEffectsMapping -> {
			dynamicEffectNames.add(dynamicEffectsMapping.getSourceNodeDynamic());
			dynamicEffectNames.add(dynamicEffectsMapping.getTargetNodeDynamic());
		});

		List<DynamicEffectsLibrary> dynamicEffectsLibraries = dynamicEffectsLibraryMapper
				.selectByNames(dynamicEffectNames);
		Map<String, DynamicEffectsLibrary> dynamicEffectsLibraryMap = Maps.newHashMap();
		dynamicEffectsLibraries.forEach(dynamicEffectsLibrary -> {
			dynamicEffectsLibraryMap.put(dynamicEffectsLibrary.getName(), dynamicEffectsLibrary);
		});

		// 转化
		contextList.forEach(context -> {
			WsEventAd ad = new WsEventAd();
			ad.load(context);

			String code = nameCodeMap.get(context.getEventSubType());
			if (mappingEffectMap.containsKey(code)) {
				// 映射类型
				ad.setDynamicType(2);
				DynamicEffectsMapping effect = mappingEffectMap.get(code);
				LineStyle lineStyle = new LineStyle(effect.getColor(), effect.getLineSize(),
						effect.getLine());
				ad.setSourceNodeDynamicDir(dynamicEffectsLibraryMap
						.get(effect.getSourceNodeDynamic()).getSourceNodeDynamicDir());
				ad.setTargetNodeDynamicDir(dynamicEffectsLibraryMap
						.get(effect.getTargetNodeDynamic()).getSourceNodeDynamicDir());;
				ad.setLineStyle(lineStyle);
				ad.setLookAtTarget(effect.getFocus());
			} else {
				// 内置类型
				ad.setDynamicType(1);
				EventTypeContext typeContext = codeTypeMap.get(code);
				ad.setSourceActionType(typeContext.getSourceActionType());
				ad.setTargetActionType(typeContext.getTargetActionType());
				// todo
				LineStyle lineStyle = new LineStyle("#FF0000", 5, "dashed");
				ad.setLineStyle(lineStyle);
				ad.setLookAtTarget(false);
			}
			adList.add(ad);
		});
		return adList;
	}

}
