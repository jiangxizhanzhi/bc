package com.cyberpeace.situation.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.constant.TaskStatus;
import com.cyberpeace.integratation.constant.TaskType;
import com.cyberpeace.integratation.model.situation.AdEventContext;
import com.cyberpeace.integratation.model.situation.ElementSlimContext;
import com.cyberpeace.integratation.model.situation.NodeEventContext;
import com.cyberpeace.integratation.model.situation.TaskEventContext;
import com.cyberpeace.integratation.service.RemoteSituationService;
import com.cyberpeace.situation.constant.DynamicParameter;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.*;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.model.ws.WsEventAd;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.cyberpeace.situation.service.context.JtTaskEventContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势大屏")
@RestController
@RequiresAuthentication
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
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

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

	@ApiOperation("推送地图数据源变更消息")
	@PostMapping("/map_source/")
	public BaseRsp<Boolean>
			pushMapSourceChange(@RequestBody @Validated MapSourceChangeReq mapSourceChangeReq) {

		// 依据大屏ID修改相应的地图源属性
		DashboardAttribute dashboardAttribute = dashboardService
				.getDashboardAttribute(mapSourceChangeReq.getDashboardId());
		dashboardAttribute.setMapSource(mapSourceChangeReq.getCode());
		dashboardAttributeMapper.updateById(dashboardAttribute);

		// 推送地图切换消息
		JSONObject data = new JSONObject();
		data.put("value", mapSourceChangeReq.getCode());
		WsMessage<JSONObject> wsMessage = new WsMessage<>(MessageType.MapSource.getDesc(), data);
		situationHandlerService.pushDataToDashboard(wsMessage, mapSourceChangeReq.getDashboardId(),
				mapSourceChangeReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("推送返回首页消息")
	@PostMapping("/back_home/")
	public BaseRsp<Boolean>
			pushBackHomeNotice(@RequestBody @Validated DashboardBasicReq dashboardBasicReq) {

		// 获取默认的靶场信息
		String bcCode = SystemPropUtil.getProp("BC_CODE");

		// 获取默认的大屏属性
		DashboardBasicAttributeRes dashboardBasicAttributeRes = dashboardService
				.getDashboardBasicAttribute(bcCode);
		if (dashboardBasicAttributeRes == null) {
			throw new BusinessException(SituationErrorCode.DEFAULT_BC_ERROR);
		}

		// 推送返回靶场大屏消息
		WsMessage<DashboardBasicAttributeRes> wsMessage = new WsMessage<>(
				MessageType.BackToHome.getDesc(), dashboardBasicAttributeRes);
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardBasicReq.getDashboardId(),
				dashboardBasicReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取最新的任务事件")
	@PostMapping("/event/task/")
	public BaseRsp<Boolean> getTaskEvent(@RequestBody @Validated TaskEventMsgReq taskEventReq) {

		List<TaskEventContext> taskEventContexts = remoteSituationService
				.getTaskEventList(taskEventReq, taskEventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEvent.getDesc(),
				taskEventReq.getDashboardId(), taskEventReq.getFeatureId(), taskEventContexts, null,
				null);

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

		// 获取三方攻防事件数据
		List<AdEventContext> adEventContexts = remoteSituationService.getAdEventList(eventReq,
				eventReq.getFeatureId());

		// 获取拓扑配置
		String envId = situationPublishInstanceService
				.getEnvIdByDashboardId(eventReq.getDashboardId());
		Map<String, ElementSlimContext> elementSlimContextMap = topoHandlerService
				.getTopoConfigByEnvId(envId);

		// 处理事件数据
		List<WsEventAd> eventAds = situationHandlerService.eventContext2EventAd(adEventContexts,
				elementSlimContextMap);

		// 推送攻防事件数据
		Boolean result = situationHandlerService.pushEventAds(eventAds, eventReq.getDashboardId(),
				eventReq.getFeatureId());

		return new BaseRsp<>(result);
	}

	@ApiOperation("获取最新的节点状态事件")
	@PostMapping("/event/task/node/")
	public BaseRsp<Boolean> getTaskNodeEvent(@RequestBody @Validated EventMsgReq eventReq) {

		List<NodeEventContext> nodeEventContexts = remoteSituationService.getNodeEventList(eventReq,
				eventReq.getFeatureId());
		situationHandlerService.batchPushDataToDashboard(MessageType.TaskEventNode.getDesc(),
				eventReq.getDashboardId(), eventReq.getFeatureId(), nodeEventContexts, null, null);

		return new BaseRsp<>(true);
	}

}
