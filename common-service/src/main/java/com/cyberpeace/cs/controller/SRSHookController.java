package com.cyberpeace.cs.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.cs.service.LiveService;
import com.cyberpeace.cs.service.context.rtc.SrsCallbackReq;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "回调接口")
@Slf4j
@RestController
@RequestMapping("/cs/callback")
public class SRSHookController {

	@Autowired
	private LiveService liveService;

	@ApiOperation("SRS回调函数")
	@PostMapping("/srs")
	public Integer srsCallback(@RequestBody @Valid SrsCallbackReq req) {
		liveService.handleCallback(req);
		// 指定格式
		return 0;
	}
}
