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
 * @date 2024/11/19
 * @see AsyncConfigurer
 * @since 1.0.0
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

	/**
	 * 获取异步执行者
	 *
	 * @return {@code Executor }
	 * @throws
	 * @see Executor
	 */
	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.initialize();

		executor.setCorePoolSize(6);
		executor.setMaxPoolSize(12);
		executor.setQueueCapacity(1000);
		executor.setKeepAliveSeconds(300);
		executor.setThreadNamePrefix("bc-Executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	/**
	 * 获取异步未捕获例外处理器
	 *
	 * @return {@code AsyncUncaughtExceptionHandler }
	 * @throws
	 * @see AsyncUncaughtExceptionHandler
	 */
	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
}
