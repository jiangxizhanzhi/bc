package com.cyberpeace.demo.controller;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 演示异步处理程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码定义了一个名为 `DemoAsyncHandler` 的 Spring 组件类，用于演示异步处理功能。以下是代码的简要分析： 
 * 1. **注解说明**： 
 * - `@Slf4j`：通过 Lombok 提供日志记录功能，简化了日志对象的创建。 
 * - `@Component`：将该类注册为 Spring 容器中的一个 Bean。 
 * 2. **方法功能**： 
 * - 方法 `testAsync()` 被标注为 `@Async`，表明这是一个异步执行的方法。 
 * - 在方法内部，使用 `log.info()` 打印当前线程名称以及一条日志信息 `"from testAsync!"`，用于验证异步任务是否在独立线程中运行。 
 * 3. **核心特性**： 
 * - 异步执行：调用 `testAsync()` 时，Spring 会将其调度到一个独立的线程中执行，不会阻塞主线程。 
 * - 线程信息：通过 `Thread.currentThread().getName()` 可以观察到异步任务运行在哪个线程上。 
 * 4. **适用场景**： 
 * - 此类适用于需要执行耗时操作但不希望阻塞主线程的场景，例如发送邮件、处理文件上传等。 
 * 总结：此代码展示了如何利用 Spring 的 `@Async` 注解实现异步任务，并通过日志记录验证其行为。 
**/
@Slf4j
@Component
public class DemoAsyncHandler {

	/**
	 * 测试异步
	 *
	 * @throws
	 */
	@Async
	public void testAsync() {
		log.info(Thread.currentThread().getName() + " from testAsync!");
	}
}

