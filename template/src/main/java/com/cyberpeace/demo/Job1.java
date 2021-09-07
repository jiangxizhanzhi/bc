package com.cyberpeace.demo;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Job1 {

	// @Scheduled(fixedRate = 5000)
	public void invoke() {
		log.info("job=[{}] trigger at [{}]", this.getClass().getSimpleName(),
				System.currentTimeMillis());
	}
}
