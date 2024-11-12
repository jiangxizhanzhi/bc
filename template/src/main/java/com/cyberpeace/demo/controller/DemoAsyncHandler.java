package com.cyberpeace.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示异步处理程序
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Component
public class DemoAsyncHandler {

	/**
	 * 异步测试
	 */
	@Async
	public void testAsync() {
		log.info(Thread.currentThread().getName() + " from testAsync!");
	}
}
