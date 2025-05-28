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
