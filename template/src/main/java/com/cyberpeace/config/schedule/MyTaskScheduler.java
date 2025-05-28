package com.cyberpeace.config.schedule;

import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * 我的任务调度
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see ThreadPoolTaskScheduler
 * @since 1.0.0
 */
public class MyTaskScheduler extends ThreadPoolTaskScheduler {

	/**
	 * schedule在fixed比率
	 *
	 * @param task   任务
	 * @param period 时期
	 * @return
	 * @throws
	 * @see Runnable
	 * @see long
	 * @see ScheduledFuture<?>
	 */
	@Override
	public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
		return super.scheduleAtFixedRate(task, period);
	}
}
