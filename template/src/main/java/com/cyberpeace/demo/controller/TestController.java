package com.cyberpeace.demo.controller;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
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

/**
 * 测试Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
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
	public BaseRsp createTopo() {
		String result = remoteTopoService.copyTopo(testTopoId);

		return new BaseRsp(result);
	}

	@GetMapping("topoStatistic")
	public BaseRsp topoStatistic() {
		TopoDigest digest = remoteTopoService.getTopoDigest(testTopoId);

		return new BaseRsp(digest);
	}

	@PostMapping
	public BaseRsp<Boolean> convertHtml(@RequestBody DemoReq1 demoReq) {
		boolean result = HtmlToPdfUtil.convert(demoReq.getHtmlFilePath(), demoReq.getPdfFilePath());

		return new BaseRsp(result);
	}
	//
	// @GetMapping("/startScene")
	// public BaseRsp<String> startScene() {
	// String sceneId = remoteSceneService.startScene(, null);
	// return new BaseRsp<>(sceneId);
	// }

	// @GetMapping("/stopScene")
	// public BaseRsp<Boolean> stopScene() {
	// Boolean result =
	// remoteSceneService.stopScene("b3304280b0a14d5aa7cd4dcf03ddd8f", null);
	//
	// return new BaseRsp<>(result);
	// }

	@PostMapping("/add_mp_log")
	public BaseRsp addMpLog() {

		UserInfo userInfo = UserInfoContext.getUserContext();
		remoteLogService.addCrLog(userInfo, OperationModule.SINGLE_TRAIN, OperationType.STOP,"测试对象");

		return new BaseRsp();
	}

}
