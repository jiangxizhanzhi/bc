package com.cyberpeace.iov.index.controller;

import com.cyberpeace.iov.index.service.bo.can.BLFreq;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.iov.index.service.IovCanService;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "CAN api")
@RestController
@RequestMapping("/iov/can/")
@RequiresAuthentication
public class IovCanController {

	@Autowired
	private IovCanService iovCanService;

	@ApiOperation("开始报文模糊")
	@PostMapping("/fuzzing/start/")
	public BaseRsp<Void> startFuzzing(@RequestBody @Validated FuzzingReq req) {
		iovCanService.startFuzzing(req);
		return new BaseRsp<>();
	}

	@ApiOperation("停止报文模糊")
	@PostMapping("/fuzzing/stop/")
	public BaseRsp<Void> stopFuzzing() {
		iovCanService.stopFuzzing();
		return new BaseRsp<>();
	}

	@ApiOperation("开始重放攻击")
	@PostMapping("/replay/start/")
	public BaseRsp<Void> startReplay(BLFreq blFreq) {
		final MultipartFile blf = blFreq.getBlf();
		iovCanService.startReplay(blf);
		return new BaseRsp<>();
	}

	@ApiOperation("停止重放攻击")
	@PostMapping("/replay/stop/")
	public BaseRsp<Void> stopReplay() {
		iovCanService.stopReplay();
		return new BaseRsp<>();
	}

	@ApiOperation("开始发送报文")
	@PostMapping("/msg/start/")
	public BaseRsp<Void> startSendMessage(@RequestBody @Validated MsgSendReq req) {
		iovCanService.startSendMsg(req);
		return new BaseRsp<>();
	}

	@ApiOperation("停止发送报文")
	@PostMapping("/msg/stop/")
	public BaseRsp<Void> stopSendMessage() {
		iovCanService.stopSendMsg();
		return new BaseRsp<>();
	}

}
