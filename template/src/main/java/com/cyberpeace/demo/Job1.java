package com.cyberpeace.demo;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 任务1
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Component
public class Job1 {

	/**
	 * 调用
	 *
	 * @throws
	 */// @Scheduled(fixedRate = 5000)
	public void invoke() {
		log.info("job=[{}] trigger at [{}]", this.getClass().getSimpleName(),
				System.currentTimeMillis());
	}
}
