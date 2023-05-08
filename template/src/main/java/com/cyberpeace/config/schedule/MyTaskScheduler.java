package com.cyberpeace.config.schedule;

import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 任务调度器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class MyTaskScheduler extends ThreadPoolTaskScheduler {

	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
		return super.scheduleAtFixedRate(task, period);
	}
}
