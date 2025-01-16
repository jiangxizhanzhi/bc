package com.cyberpeace.iov.index.controller;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.iov.index.service.bo.can.FuzzingReq;
import com.cyberpeace.iov.index.service.bo.student.RawPacket;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.iov.index.service.IovStudentService;

import io.swagger.annotations.Api;

import java.util.List;

@Api(tags = "student api")
@RestController
@RequestMapping("/iov/student/")
@RequiresAuthentication
public class IovStudentController {

	@Autowired
	private IovStudentService iovStudentService;

	@ApiOperation("蓝牙嗅探(10s返回)")
	@GetMapping("/bluetooth/sniff/")
	public BaseRsp<List<String>> sniffBluetooth() {
		List<String> res = iovStudentService.sniffBluetooth();
		return new BaseRsp<>(res);
	}

	@ApiOperation("WIFI嗅探（30s返回）")
	@GetMapping("/wifi/sniff/")
	public BaseRsp<JSONArray> sniffWifi() {
		JSONArray res = iovStudentService.sniffWifi();
		return new BaseRsp<>(res);
	}

	@ApiOperation("原始报文")
	@GetMapping("/raw/packet/")
	public BaseRsp<RawPacket> getRawPacket() {
		RawPacket res = iovStudentService.getLatestPacket();
		return new BaseRsp<>(res);
	}




}
