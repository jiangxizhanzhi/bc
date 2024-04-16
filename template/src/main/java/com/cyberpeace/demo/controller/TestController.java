package com.cyberpeace.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.context.SessionToken;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteSupersetService;
import com.cyberpeace.integratation.service.RemoteTopoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Api(tags = "测试接口")
@RestController
@RequestMapping("/test")
@RequiresAuthentication
public class TestController {

	@Autowired
	private RemoteTopoService remoteTopoService;

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private RemoteSupersetService remoteSupersetService;

	String testTopoId = "1dd035784f5b44869c4e96aa07c31fd5";

	@PostMapping("/copytopo")
	public BaseRsp<String> createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp<>(result);
	}

	@GetMapping("topoStatistic")
	public BaseRsp<TopoDigest> topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp<>(digest);
	}

	@PostMapping
	public BaseRsp<Boolean> convertHtml(@RequestBody DemoReq1 demoReq) {
		boolean result = HtmlToPdfUtil.convert(demoReq.getHtmlFilePath(), demoReq.getPdfFilePath());

		return new BaseRsp<>(result);
	}

	@PostMapping("/add_mp_log")
	public BaseRsp addMpLog() {

		UserInfo userInfo = UserInfoContext.getUserContext();
		remoteLogService.addCrLog(userInfo, OperationModule.SINGLE_TRAIN, OperationType.STOP,"测试对象");

		return new BaseRsp();
	}

	@ApiOperation("获取token")
	@GetMapping("/token")
	public BaseRsp<SessionToken> getToken() {
		return new BaseRsp<>(remoteSupersetService.getToken());
	}

	@ApiOperation("创建大屏")
	@GetMapping("/create_dashboard")
	public BaseRsp<String> createDashboard(@RequestParam(name = "dashboard_title") @ApiParam(name = "dashboard_title", value = "大屏标题", required = true)String dashboardTitle) {
		return new BaseRsp<>(remoteSupersetService.createDashboard(dashboardTitle));
	}

	@ApiOperation("删除大屏")
	@GetMapping("/delete_dashboard")
	public BaseRsp<Boolean> deleteDashboard(@RequestParam(name = "dashboard_id") @ApiParam(name = "dashboard_id", value = "大屏id", required = true)String dashboardId) {
		remoteSupersetService.deleteDashboard(Arrays.asList(dashboardId));
		return new BaseRsp<>(true);
	}

	@ApiOperation("获取大屏详情")
	@GetMapping("/detail")
	public BaseRsp<JSONObject> getDashboard(@RequestParam(name = "dashboard_id") @ApiParam(name = "dashboard_id", value = "大屏id", required = true)String dashboardId) {
		return new BaseRsp<>(remoteSupersetService.getDashboardDetail(dashboardId));
	}

	@ApiOperation("获取大屏链接")
	@GetMapping("/permalink")
	public BaseRsp<JSONObject> getDashboardPermalink(@RequestParam(name = "dashboard_id") @ApiParam(name = "dashboard_id", value = "大屏id", required = true)String dashboardId) {
		return new BaseRsp<>(remoteSupersetService.getDashboardPermalink(dashboardId));
	}

}
