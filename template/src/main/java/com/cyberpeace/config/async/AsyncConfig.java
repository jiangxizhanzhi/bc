package com.cyberpeace.config.async;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 配置异步任务执行器的类
 * 通过实现AsyncConfigurer接口并使用@Configuration和@EnableAsync注解，
 * 该类用于配置Spring应用中的异步任务执行器和异常处理器
 */
@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    /**
     * 配置并创建一个异步任务执行器
     * 该方法初始化一个ThreadPoolTaskExecutor实例，并设置其核心和最大线程池大小、队列容量、线程名称前缀等，
     * 以定义异步任务执行的策略和行为
     *
     * @return Executor 返回配置好的线程池执行器实例，命名为taskExecutor
     */
    @Override
    @Bean(name = "taskExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.initialize();

        // 设置线程池的核心线程数为3
        executor.setCorePoolSize(3);
        // 设置线程池的最大线程数为3
        executor.setMaxPoolSize(3);
        // 设置线程池的队列容量为1000
        executor.setQueueCapacity(1000);
        // 设置线程的空闲存活时间为300秒
        executor.setKeepAliveSeconds(300);
        // 设置线程名称前缀为"bc-Executor-"
        executor.setThreadNamePrefix("bc-Executor-");
        // 设置线程池的拒绝执行策略为调用者运行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    /**
     * 配置并返回一个异步任务的未捕获异常处理器
     * 该方法返回一个SimpleAsyncUncaughtExceptionHandler实例，用于处理异步任务中未捕获的异常
     *
     * @return AsyncUncaughtExceptionHandler 返回配置好的异步任务未捕获异常处理器实例
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }
}

