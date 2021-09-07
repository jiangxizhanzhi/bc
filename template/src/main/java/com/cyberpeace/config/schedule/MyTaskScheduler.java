package com.cyberpeace.config.schedule;

import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

public class MyTaskScheduler extends ThreadPoolTaskScheduler {

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
		return super.scheduleAtFixedRate(task, period);
	}
}
