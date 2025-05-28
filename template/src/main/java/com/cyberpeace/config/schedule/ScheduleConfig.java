/**
 * 这段代码定义了一个Spring配置类 `ScheduleConfig`，用于配置任务调度器。以下是简要分析： 
 * 1. **类注解**： 
 * - 使用了 `@Configuration` 注解，表明这是一个Spring配置类，用于定义Bean。 
 * 2. **方法注解**： 
 * - 方法 `taskScheduler()` 被 `@Bean` 注解标记，表示该方法返回的对象会被注册为Spring容器中的一个Bean。 
 * - 返回值类型是 `TaskScheduler`，这是Spring提供的接口，用于任务调度。 
 * 3. **具体实现**： 
 * - 方法中返回的是 `MyTaskScheduler` 的实例。`MyTaskScheduler` 是一个自定义的任务调度器实现类（需确保该类已定义并实现了 `TaskScheduler` 接口）。 
 * 4. **用途**： 
 * - 该配置类的主要作用是提供一个任务调度器的Bean，供其他组件或服务使用，以执行定时任务或其他需要调度的功能。 
 * 总结：此代码通过Spring的配置机制，定义了一个自定义任务调度器的Bean，便于在应用中进行任务调度。 
**/
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
