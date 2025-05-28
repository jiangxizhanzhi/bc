/**
 * 这段代码是一个基于Spring Boot框架实现的健康检查控制器类。以下是代码的简要分析： 
 * 1. **包与导入**： 
 * - 该类位于`com.cyberpeace.demo.controller`包下。 
 * - 导入了必要的Spring框架类（如`@RestController`, `@GetMapping`, `@RequestMapping`等）以及日志工具类`slf4j`。 
 * 2. **注解说明**： 
 * - `@Slf4j`：为类添加了日志功能。 
 * - `@Api`：用于描述API文档，这里指明了这是一个“健康检查接口”。 
 * - `@RestController`：表明这是一个RESTful风格的控制器。 
 * - `@RequestMapping("/health-check")`：定义了该控制器的基本路径为`/health-check`。 
 * 3. **字段与依赖注入**： 
 * - 类中有一个私有字段`demoAsyncHandler`，它通过`@Autowired`注解实现了自动装配，表示它将由Spring容器管理并注入。 
 * 4. **方法说明**： 
 * - `healthCheck()`方法使用了`@GetMapping`注解，表示它可以通过GET请求访问。 
 * - 方法内部调用了`demoAsyncHandler.testAsync()`来执行某些异步任务，并返回一个`BaseRsp`对象作为响应。 
 * 5. **功能概述**： 
 * - 此控制器的主要功能是提供一个简单的健康检查接口，通过调用异步任务处理程序来验证系统的运行状态，并返回标准的响应对象。 
 * 总体来说，这段代码展示了如何使用Spring Boot构建一个基本的健康检查服务端点，同时结合了异步处理机制。 
**/
package com.cyberpeace.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.model.BaseRsp;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 健康检查Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Api(tags = "健康检查接口")
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

	/**
	 * 演示异步处理程序
	 *
	 * @see DemoAsyncHandler
	 */
	@Autowired
	private DemoAsyncHandler demoAsyncHandler;

	/**
	 * 运行状况检查
	 *
	 * @return 基础响应
	 * @throws
	 * @see BaseRsp
	 */
	@ApiOperation("健康检查")
	@GetMapping
	public BaseRsp healthCheck() {
		demoAsyncHandler.testAsync();
		return new BaseRsp();
	}
}
