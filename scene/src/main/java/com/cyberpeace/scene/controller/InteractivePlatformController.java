package com.cyberpeace.scene.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.service.InteractivePlatformService;
import com.cyberpeace.scene.service.bo.interactive.InteractiveSceneReq;
import com.cyberpeace.scene.service.bo.interactive.UserLinkRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(value = "网安意识互动平台", tags = "网安意识互动平台")
@Slf4j
@RestController
@RequestMapping("/interactive")
public class InteractivePlatformController {

	@Autowired
	private InteractivePlatformService interactivePlatformService;

	@PostMapping("/start")
	@ApiOperation(value = "启动场景")
	public BaseRsp start(@RequestBody InteractiveSceneReq req) {
		interactivePlatformService.startScene(req);
		return new BaseRsp();
	}

	@PostMapping("/stop")
	@ApiOperation(value = "关闭场景")
	public BaseRsp stop(@RequestBody InteractiveSceneReq req) {
		interactivePlatformService.stopScene(req);
		return new BaseRsp();
	}

	@ApiOperation("获取rdp接入链接")
	@GetMapping("/user_link")
	public BaseRsp<UserLinkRsp>
			getUserLink(@ApiParam(value = "场景类型", name = "scenario") Integer scenario) {
		UserLinkRsp rsp = interactivePlatformService.getUserLink(scenario);
		return new BaseRsp<>(rsp);
	}

}
