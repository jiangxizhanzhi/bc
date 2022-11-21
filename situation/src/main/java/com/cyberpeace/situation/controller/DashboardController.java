package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.DashboardChangeReq;
import com.cyberpeace.situation.controller.req.EventQueryReq;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.context.JtTaskEventContext;
import com.cyberpeace.situation.service.context.TaskEventContext;

import cn.hutool.json.JSONObject;
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
				JsonUtil.toJsonObject(dashboardChangeReq.getData(), JSONObject.class));
		String sceneId = dashboardChangeReq.getDashboardId() + "_"
				+ dashboardChangeReq.getFeatureId();
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
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
