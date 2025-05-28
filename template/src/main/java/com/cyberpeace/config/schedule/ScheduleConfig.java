package com.cyberpeace.config.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

/**
 * 计划配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Configuration
public class ScheduleConfig {

	/**
	 * 任务计划程序
	 *
	 * @return 任务计划程序
	 * @throws
	 * @see TaskScheduler
	 */
	@Bean
	public TaskScheduler taskScheduler() {
		return new MyTaskScheduler();
	}
}
