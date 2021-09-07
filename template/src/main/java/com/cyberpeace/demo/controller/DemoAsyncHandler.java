package com.cyberpeace.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DemoAsyncHandler {

	@Async
	public void testAsync() {
		log.info(Thread.currentThread().getName() + " from testAsync!");
	}
}
