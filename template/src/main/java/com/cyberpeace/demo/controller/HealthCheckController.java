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
 * 健康检查控制器
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Api(tags = "健康检查接口")
@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

	/**
	 * 演示异步处理程序
	 */
	@Autowired
	private DemoAsyncHandler demoAsyncHandler;

	/**
	 * 健康检查
	 *
	 * @return {@link BaseRsp }
	 */
	@ApiOperation("健康检查")
	@GetMapping
	public BaseRsp healthCheck() {
		demoAsyncHandler.testAsync();
		return new BaseRsp();
	}
}
