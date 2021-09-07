package com.cyberpeace.config.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

@Configuration
public class ScheduleConfig {

	@Bean
	public TaskScheduler taskScheduler() {
		return new MyTaskScheduler();
	}
}
