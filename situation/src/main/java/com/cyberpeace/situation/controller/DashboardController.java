package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.controller.req.DashboardChangeReq;
import com.cyberpeace.situation.controller.res.DashboardBasicAttributeRes;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.service.DashboardService;
import com.cyberpeace.situation.service.SituationHandlerService;

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

}
