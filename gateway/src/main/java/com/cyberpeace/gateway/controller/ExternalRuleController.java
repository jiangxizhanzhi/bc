package com.cyberpeace.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-实操习题相关api")
@Slf4j
@RestController
@RequestMapping("/gateway/rule")
public class ExternalRuleController {

	@ApiOperation("记分排行规则分页列表")
	@GetMapping("/score/page")
	public BaseRsp getScoreRankingRuleList() {

		return new BaseRsp();
	}

	@ApiOperation("记分排行规则详情")
	@GetMapping("/score")
	public BaseRsp getScoreRankingRuleDetail() {

		return new BaseRsp();
	}

	@ApiOperation("轮次机制规则列表")
	@GetMapping("/round/page")
	public BaseRsp getRoundRuleList() {

		return new BaseRsp();
	}

	@ApiOperation("轮次机制规则详情")
	@GetMapping("/round")
	public BaseRsp getRoundRuleDetail() {

		return new BaseRsp();
	}

	@ApiOperation("违规判定规则列表")
	@GetMapping("/violation/page")
	public BaseRsp getViolationRuleList() {

		return new BaseRsp();
	}

	@ApiOperation("违规判定规则列表")
	@GetMapping("/violation")
	public BaseRsp getViolationRuleDetail() {

		return new BaseRsp();
	}

}
