/**
 * 这段代码定义了一个Spring配置类 `AsyncConfig`，用于配置异步任务相关的线程池和异常处理逻辑。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `AsyncConfig` 是一个 Spring 配置类，实现了 `AsyncConfigurer` 接口。 
 * - 它通过 `@Configuration` 和 `@EnableAsync` 注解启用了 Spring 的异步支持。 
 * 2. **核心方法**： 
 * - **`getAsyncExecutor()`**： 
 * - 返回一个 `Executor` 对象，用于管理异步任务的执行。 
 * - 使用了 `ThreadPoolTaskExecutor` 来创建线程池。 
 * - 配置了以下参数： 
 * - `corePoolSize` 和 `maxPoolSize`：均为 3，表示线程池的核心和最大线程数为 3。 
 * - `queueCapacity`：1000，表示任务队列的最大容量。 
 * - `keepAliveSeconds`：300 秒，表示空闲线程的存活时间。 
 * - `threadNamePrefix`：线程名称前缀为 `"bc-Executor-"`。 
 * - `rejectedExecutionHandler`：使用 `CallerRunsPolicy`，当任务队列满时，由调用线程执行任务。 
 * - **`getAsyncUncaughtExceptionHandler()`**： 
 * - 返回一个 `AsyncUncaughtExceptionHandler` 对象，用于处理未捕获的异步任务异常。 
 * - 使用了默认的 `SimpleAsyncUncaughtExceptionHandler`，它会简单地记录异常信息。 
 * 3. **总结**： 
 * - 这个配置类主要负责定义异步任务的线程池和异常处理策略。 
 * - 线程池的配置适合中等规模的任务量，能够有效管理并发任务并避免资源浪费。 
 * - 未捕获异常的处理较为简单，直接记录异常信息，适用于大多数场景。 
**/
package com.cyberpeace.config.async;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 异步配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see AsyncConfigurer
 * @since 1.0.0
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	/**
	 * 获取异步执行者
	 *
	 * @return 执行者
	 * @throws
	 * @see Executor
	 */
	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();

		executor.setCorePoolSize(3);
		executor.setMaxPoolSize(3);
		executor.setQueueCapacity(1000);
		executor.setKeepAliveSeconds(300);
		executor.setThreadNamePrefix("bc-Executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	/**
	 * 获取异步未捕获例外处理器
	 *
	 * @return async 未捕获异常处理程序
	 * @throws
	 * @see AsyncUncaughtExceptionHandler
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
