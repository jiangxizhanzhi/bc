package com.cyberpeace.cs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.service.BehaviorRecordService;
import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "回调接口")
@Slf4j
// @RestController
@RequestMapping("/cs/behavior/analyse")
public class BehaviorAnalyseController {

	@Autowired
	private BehaviorRecordService behaviorRecordService;

	@ApiOperation("分析结果")
	@GetMapping("")
	public BaseRsp<BehaviorAnalyseResult> getAnalyseResult() {
		BehaviorAnalyseResult res = behaviorRecordService.getAnalyseResult();
		return new BaseRsp<>(res);
	}
}
