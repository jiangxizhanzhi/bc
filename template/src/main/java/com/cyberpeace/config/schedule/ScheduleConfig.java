package com.cyberpeace.config.schedule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;

/**
 * 配置类，用于定义调度相关的Bean
 */
@Configuration
public class ScheduleConfig {

    /**
     * 创建并返回一个TaskScheduler实例
     *
     * @return TaskScheduler实例，用于任务调度
     */
    @Bean
    public TaskScheduler taskScheduler() {
        return new MyTaskScheduler();
    }
}

