/**
 * 这段代码定义了一个Spring Boot控制器类 `ExternalActionFrameController`，用于处理与防御安全策略相关的外部API请求。以下是对代码的简要分析： 
 * 1. **包和导入**： 
 * - 该类位于 `com.cyberpeace.gateway.controller` 包中。 
 * - 导入了Spring Web相关的注解（如 `@RestController`, `@RequestMapping`, `@GetMapping`）和日志相关的库（如 `lombok.extern.slf4j.Slf4j`）。 
 * 2. **类和注解**： 
 * - 类 `ExternalActionFrameController` 被标记为 `@RestController`，表示这是一个RESTful风格的控制器。 
 * - 使用 `@RequestMapping("/gateway/strategy")` 注解指定了该控制器所有方法的基础路径为 `/gateway/strategy`。 
 * - `@Slf4j` 注解用于自动生成日志记录器，方便进行日志记录。 
 * - `@Api` 注解用于描述该控制器提供的API，标签为“外部接口-防御安全策略相关api”。 
 * 3. **方法和注解**： 
 * - 控制器包含多个HTTP GET请求处理方法，每个方法都有 `@ApiOperation` 注解来描述其功能。 
 * - 每个方法返回一个 `BaseRsp` 对象，这是一个通用响应对象，通常用于封装API的响应结果。 
 * - 方法路径和功能如下： 
 * - `/page`：获取策略列表。 
 * - `/`：获取策略详情。 
 * - `/attack/page`：获取攻击战法列表。 
 * - `/attack`：获取攻击战法详情。 
 * - `/defense/page`：获取防御战法列表。 
 * - `/defense`：获取防御战法详情。 
 * 总结：该控制器提供了一系列与防御安全策略相关的API，包括策略列表、策略详情、攻击战法列表和详情、防御战法列表和详情等。每个API方法都返回一个通用的响应对象 `BaseRsp`。 
**/
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
