/**
 * 该代码定义了一个名为 `MyTaskScheduler` 的类，它继承自 Spring 框架中的 `ThreadPoolTaskScheduler` 类。以下是代码的简要分析： 
 * 1. **类定义**： 
 * - `MyTaskScheduler` 是一个自定义的任务调度器，扩展了 `ThreadPoolTaskScheduler`。 
 * - 它主要用于管理线程池并调度定时任务。 
 * 2. **方法重写**： 
 * - 重写了 `scheduleAtFixedRate` 方法，该方法用于以固定的时间间隔调度任务。 
 * - 调用了父类的同名方法 `super.scheduleAtFixedRate(task, period)` 来执行实际的任务调度。 
 * 3. **注释**： 
 * - 类和方法都有详细的注释，说明了其用途和参数。 
 * - 注释中引用了相关的类和接口（如 `Runnable`, `ScheduledFuture`）以及参数类型（`long`），有助于理解方法的功能。 
 * 4. **用途**： 
 * - 该类可以被用来替代默认的 `ThreadPoolTaskScheduler`，提供更灵活的任务调度能力。 
 * 总结：这是一个简单的任务调度器扩展类，主要功能是通过重写 `scheduleAtFixedRate` 方法来调度任务。 
**/
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
