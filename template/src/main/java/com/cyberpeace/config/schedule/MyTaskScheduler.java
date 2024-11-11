package com.cyberpeace.config.schedule;

import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * MyTaskScheduler 类继承自 ThreadPoolTaskScheduler，主要用于任务调度
 * 它通过线程池来执行任务，可以有效地管理多个异步任务的执行
 * 这个类重写了 scheduleAtFixedRate 方法，以定制任务按固定速率执行的行为
 */
public class MyTaskScheduler extends ThreadPoolTaskScheduler {

    /**
     * 重写 scheduleAtFixedRate 方法，按照固定的周期执行任务
     * 这个方法会在初始延迟后，每隔指定的周期执行一次任务
     *
     * @param task 需要执行的任务，一个实现了 Runnable 接口的对象
     * @param period 执行任务的周期，单位为毫秒
     * @return 返回一个 ScheduledFuture 对象，表示异步计算的结果
     *         可以通过这个对象来取消任务的执行或者获取任务的执行结果
     */
    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        return super.scheduleAtFixedRate(task, period);
    }
}

