package com.cyberpeace.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-防御安全策略相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/strategy")
public class ExternalActionFrameController {

	@ApiOperation("策略列表")
	@GetMapping("/page")
	public BaseRsp getStrategyList() {
		return new BaseRsp<>();
	}

	@ApiOperation("策略详情")
	@GetMapping("/")
	public BaseRsp getStrategyDetail() {
		return new BaseRsp<>();
	}

	@ApiOperation("攻击战法列表")
	@GetMapping("/attack/page")
	public BaseRsp getTacticAttackList() {
		return new BaseRsp<>();
	}

	@ApiOperation("攻击战法详情")
	@GetMapping("/attack")
	public BaseRsp getTacticAttackDetail() {
		return new BaseRsp<>();
	}

	@ApiOperation("防御战法列表")
	@GetMapping("/defense/page")
	public BaseRsp getTacticDefenseList() {
		return new BaseRsp<>();
	}

	@ApiOperation("防御战法详情")
	@GetMapping("/defense")
	public BaseRsp getTacticDefenseDetail() {
		return new BaseRsp<>();
	}
}
