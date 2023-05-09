package com.cyberpeace.config.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

/**
 * 计划配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Configuration
public class ScheduleConfig {

	@Bean
	public TaskScheduler taskScheduler() {
		return new MyTaskScheduler();
	}
}
