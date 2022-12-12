package com.cyberpeace.situation.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.SituationErrorCode;
import com.cyberpeace.situation.constant.TaskType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.DashboardBasicReq;
import com.cyberpeace.situation.controller.req.DashboardChangeReq;
import com.cyberpeace.situation.controller.req.EventQueryReq;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.controller.res.DashboardIdRes;
import com.cyberpeace.situation.dao.entity.DashboardAttribute;
import com.cyberpeace.situation.dao.mapper.DashboardAttributeMapper;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.context.JtTaskEventContext;
import com.cyberpeace.situation.service.context.TaskEventContext;

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

	@ApiOperation("获取任务/科目发布的大屏ID")
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

		if (!"任务执行".equals(taskStatus)) {
			throw new BusinessException(SituationErrorCode.TASK_NOT_RUNNING);
		}

		TaskType type = Arrays.stream(TaskType.class.getEnumConstants())
				.filter(t -> t.getName().equals(taskType)).findFirst().orElse(TaskType.QB);

		if (!type.getSupport()) {
			throw new BusinessException(SituationErrorCode.TASK_TYPE_NOT_SUPPORTED, type.getName());
		}

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getByTaskId(taskId);

		String dashboardId = dashboardAttribute == null ? taskId
				: dashboardAttribute.getDashboardId();

		return new BaseRsp<>(new DashboardIdRes(dashboardId, type.getCode()));

	}

	@ApiOperation("获取大屏基本属性")
	@GetMapping("/basic_attribute/")
	public BaseRsp<DashboardBasicAttributeRes>
			getDashboardBasicAttribute(@RequestParam("dashboard_id") @ApiParam(required = true,
					name = "dashboard_id", value = "大屏ID") String dashboardId) {
		return new BaseRsp<>(dashboardService.getDashboardBasicAttribute(dashboardId));
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

		DashboardAttribute dashboardAttribute = dashboardAttributeMapper.getById("lgzx");
		WsMessage<JSONObject> wsMessage = new WsMessage<>(MessageType.BackToHome.getDesc(),
				JsonUtil.java2JsonObject(dashboardAttribute));
		situationHandlerService.pushDataToDashboard(wsMessage, dashboardBasicReq.getDashboardId(),
				dashboardBasicReq.getFeatureId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取最新的任务事件")
	@GetMapping("/event/task/")
	public BaseRsp<Boolean> getTaskEvent(
			@ApiParam(name = "feature_id", value = "特征ID",
					required = true) @RequestParam(value = "feature_id") String featureId,
			@ApiParam(name = "dashboard_id", value = "大屏ID",
					required = true) @RequestParam(value = "dashboard_id") String dashboardId,
			@ApiParam(name = "sub_range_id", value = "分靶场ID",
					required = false) @RequestParam(value = "sub_range_id") String subRangeId) {

		TaskEventContext taskEventContext = new TaskEventContext();
		WsMessage<TaskEventContext> wsMessage = new WsMessage<>(MessageType.TaskEvent.getDesc(),
				taskEventContext);
		String sceneId = dashboardId + "_" + featureId;
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
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
	@GetMapping("/event/task/ad/")
	public BaseRsp<Boolean> getAdTaskEvent(EventQueryReq eventQueryReq) {

		WsMultiNodeDynamic wsEvent = situationHandlerService.getRandomWsMultNodeDynamic("demo",
				null);
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(MessageType.TaskEventAd.getDesc(),
				wsEvent);
		String sceneId = eventQueryReq.getDashboardId() + "_" + eventQueryReq.getFeatureId();
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(true);
	}

}
