package com.cyberpeace.demo;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 工作1
 *
 * @author admin
 * @date 2024/11/12
 */
@Slf4j
@Component
public class Job1 {

	/**
	 * 援引
	 */
	// @Scheduled(fixedRate = 5000)
	public void invoke() {
		log.info("job=[{}] trigger at [{}]", this.getClass().getSimpleName(),
				System.currentTimeMillis());
	}
}
