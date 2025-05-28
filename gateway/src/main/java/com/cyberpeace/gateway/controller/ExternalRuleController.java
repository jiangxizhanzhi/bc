/**
 * 这段代码定义了一个Spring Boot控制器类 `ExternalRuleController`，用于处理与规则库相关的外部API请求。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.gateway.controller` 
 * - **类名**: `ExternalRuleController` 
 * - **注解**: 
 * - `@RestController`: 标识这是一个RESTful风格的控制器。 
 * - `@Slf4j`: 使用Lombok的注解来自动创建一个日志记录器。 
 * - `@Api(tags = "外部接口-规则库相关api")`: 用于Swagger文档生成，标识这个控制器处理的是规则库相关的外部API。 
 * ### 方法概述 
 * 该控制器包含了多个GET请求处理方法，每个方法都有对应的Swagger文档注解 `@ApiOperation`，并返回一个 `BaseRsp` 对象。以下是各个方法的详细说明： 
 * 1. **getScoreRankingRuleList** 
 * - **路径**: `/gateway/rule/score/page` 
 * - **功能**: 获取记分排行规则的分页列表。 
 * - **返回值**: `BaseRsp` 
 * 2. **getScoreRankingRuleDetail** 
 * - **路径**: `/gateway/rule/score` 
 * - **功能**: 获取记分排行规则的详情。 
 * - **返回值**: `BaseRsp` 
 * 3. **getRoundRuleList** 
 * - **路径**: `/gateway/rule/round/page` 
 * - **功能**: 获取轮次机制规则的列表。 
 * - **返回值**: `BaseRsp` 
 * 4. **getRoundRuleDetail** 
 * - **路径**: `/gateway/rule/round` 
 * - **功能**: 获取轮次机制规则的详情。 
 * - **返回值**: `BaseRsp` 
 * 5. **getViolationRuleList** 
 * - **路径**: `/gateway/rule/violation/page` 
 * - **功能**: 获取违规判定规则的列表。 
 * - **返回值**: `BaseRsp` 
 * 6. **getViolationRuleDetail** 
 * - **路径**: `/gateway/rule/violation` 
 * - **功能**: 获取违规判定规则的详情。 
 * - **返回值**: `BaseRsp` 
 * ### 问题 
 * - 在 `getViolationRuleList` 和 `getViolationRuleDetail` 方法中，`@ApiOperation` 注解的内容是重复的，都是“违规判定规则列表”。这可能是代码错误，需要修改其中一个方法的注解内容。 
 * ### 总结 
 * 该控制器类实现了与规则库相关的多个API接口，包括记分排行规则、轮次机制规则和违规判定规则的列表和详情获取。通过使用Spring Boot和Swagger，这些API可以方便地进行文档生成和测试。 
**/
package com.cyberpeace.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "外部接口-规则库相关api")
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
