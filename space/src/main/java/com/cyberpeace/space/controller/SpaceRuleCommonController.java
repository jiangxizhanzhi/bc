package com.cyberpeace.space.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.space.service.SpaceRuleCommonService;
import com.cyberpeace.space.service.context.rule.RaceStatistics;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "知识空间-规则库-通用")
@RestController
@RequestMapping("/space/rule/common")
public class SpaceRuleCommonController {

	@Autowired
	private SpaceRuleCommonService service;

	@ApiOperation("比赛及训练评估规则统计")
	@GetMapping("/race/statistics")
	public BaseRsp<RaceStatistics> getRaceRuleS() {
		RaceStatistics res = service.getRaceStatistics();
		return new BaseRsp<>(res);
	}
}
