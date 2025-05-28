package com.cyberpeace.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示异步处理程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class DemoAsyncHandler {

	/**
	 * 测试异步
	 *
	 * @throws
	 */
	@Async
	public void testAsync() {
		log.info(Thread.currentThread().getName() + " from testAsync!");
	}
}
