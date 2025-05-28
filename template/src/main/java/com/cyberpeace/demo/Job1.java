package com.cyberpeace.demo;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 约伯1
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class Job1 {

	// @Scheduled(fixedRate = 5000)
	public void invoke() {
		log.info("job=[{}] trigger at [{}]", this.getClass().getSimpleName(),
				System.currentTimeMillis());
	}
}
