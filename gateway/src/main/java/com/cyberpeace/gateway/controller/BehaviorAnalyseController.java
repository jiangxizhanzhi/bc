package com.cyberpeace.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;
import com.cyberpeace.gateway.service.BehaviorAnalyseService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 行为分析Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
@Api(tags = "回调接口")
@Slf4j
// @RestController
@RequestMapping("/cs/behavior/analyse")
public class BehaviorAnalyseController {

	/**
	 * 行为分析Service
	 *
	 * @see BehaviorAnalyseService
	 */
	@Autowired
	private BehaviorAnalyseService behaviorAnalyseService;

	/**
	 * 获取分析结果
	 *
	 * @return {@code BaseRsp<BehaviorAnalyseResult> }
	 * @throws
	 * @see BaseRsp<BehaviorAnalyseResult>
	 */
	@ApiOperation("分析结果")
	@GetMapping("")
	public BaseRsp<BehaviorAnalyseResult> getAnalyseResult() {
		BehaviorAnalyseResult res = behaviorAnalyseService.getAnalyseResult();
		return new BaseRsp<>(res);
	}
}
