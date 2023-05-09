package com.cyberpeace.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示异步处理程序
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
@Component
public class DemoAsyncHandler {

	@Async
	public void testAsync() {
		log.info(Thread.currentThread().getName() + " from testAsync!");
	}
}
