package com.cyberpeace.iov.index.controller;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONArray;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.iov.index.service.IovCanService;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.can.MsgSendReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

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
	public BaseRsp<Void> startReplay(@RequestParam("blf") @NotNull MultipartFile blf) {
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
	public BaseRsp<JSONArray> stopSendMessage() {
		JSONArray result = iovCanService.stopSendMsg();
		return new BaseRsp<>(result);
	}

	@ApiOperation("开始抓取")
	@PostMapping("/sniff/start/")
	public BaseRsp<Void> startSniff() {
		iovCanService.startSniff();
		return new BaseRsp<>();
	}

	@ApiOperation("停止抓取")
	@PostMapping("/sniff/stop/")
	public BaseRsp<Void> stopSniff() {
		iovCanService.stopSniff();
		return new BaseRsp<>();
	}

}
