package com.cyberpeace.demo.controller;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.constant.LogContent;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.HtmlToPdfUtil;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;

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
	public BaseRsp<Boolean> addMpLog() {

		UserInfo userInfo = UserInfoContext.getUserContext();
		remoteLogService.addOjLog(userInfo, OperationModule.SINGLE_TRAIN, OperationType.STOP, "测试对象",
				LogContent.CR_FORMAT.format(OperationType.STOP, LogContent.SINGLE_TRAIN_ROOM.getMsg(), "测试对象"));
		return new BaseRsp<>(true);
	}

}
