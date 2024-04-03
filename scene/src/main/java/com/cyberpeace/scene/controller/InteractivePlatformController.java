package com.cyberpeace.scene.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.scene.constant.enums.InteractiveScenarioEnum;
import com.cyberpeace.scene.service.InteractivePlatformService;
import com.cyberpeace.scene.service.bo.interactive.InteractiveSceneReq;
import com.cyberpeace.scene.service.bo.interactive.MailBo;
import com.cyberpeace.scene.service.bo.interactive.QQRes;
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
	@ApiOperation(value = "检测场景和虚拟机就绪状态")
	public BaseRsp start(@RequestBody InteractiveSceneReq req) {
		// 检测虚拟机rdp就绪状态
		interactivePlatformService.checkLink(req.getScenario());
		return new BaseRsp();
	}

	@PostMapping("/stop")
	@ApiOperation(value = "关闭当前场景，开启新场景")
	public BaseRsp stop(@RequestBody InteractiveSceneReq req) {
		// 关闭旧场景，启动新场景
		interactivePlatformService.stopScene(req.getScenario());
		interactivePlatformService.startScene(req.getScenario());
		return new BaseRsp();
	}

	@ApiOperation("获取rdp接入链接")
	@GetMapping("/user_link")
	public BaseRsp<UserLinkRsp>
			getUserLink(@ApiParam(value = "场景类型", name = "scenario") Integer scenario) {
		UserLinkRsp rsp = interactivePlatformService.getUserLink(scenario);
		return new BaseRsp<>(rsp);
	}

	@ApiOperation("发送邮件")
	@PostMapping("/mail")
	public BaseRsp sendMail(@RequestBody MailBo mailBo) {
		interactivePlatformService.sendMail(mailBo.getContent());
		return new BaseRsp<>();
	}

	@ApiOperation("发送解密邮件")
	@PostMapping("/mail/decrypt")
	public BaseRsp sendDecryptMail() {
		interactivePlatformService.sendDecryptMail();
		return new BaseRsp<>();
	}

	@ApiOperation("获取绵羊墙列表")
	@GetMapping("/pineapple")
	public BaseRsp<List<QQRes>> getQQ(){
		List<QQRes> res = interactivePlatformService.getQQRes();
		return new BaseRsp(res);
	}


	@ApiOperation("初始化场景")
	@GetMapping("/init")
	public BaseRsp initScene() {
		// start scenes
		interactivePlatformService.stopScene(InteractiveScenarioEnum.RANSOMWARE.getCode());
		interactivePlatformService.stopScene(InteractiveScenarioEnum.U_DISK.getCode());
		interactivePlatformService.startScene(InteractiveScenarioEnum.RANSOMWARE.getCode());
		interactivePlatformService.startScene(InteractiveScenarioEnum.U_DISK.getCode());
		// create snapshot for all nodes
		interactivePlatformService.snapShotAll();
		return new BaseRsp<>();
	}

	@ApiOperation("清空数据")
	@DeleteMapping("/clear")
	public BaseRsp clearAll() {
		interactivePlatformService.clearAll();
		return new BaseRsp();
	}

}
