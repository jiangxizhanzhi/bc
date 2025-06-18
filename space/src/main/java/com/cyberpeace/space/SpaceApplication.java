package com.cyberpeace.space;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 空间行为依赖
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码是一个 Spring Boot 应用程序的入口类，主要功能如下： 
 * 1. **注解说明**： 
 * - `@SpringBootApplication`：标识这是一个 Spring Boot 应用程序，启用自动配置和组件扫描。 
 * - `exclude = { DataSourceAutoConfiguration.class }`：排除默认的数据库数据源自动配置。 
 * - `scanBasePackages = { "com.cyberpeace" }`：指定扫描的基础包路径为 `com.cyberpeace`。 
 * - `@ServletComponentScan`：启用对自定义 Servlet、Filter 和 Listener 的扫描。 
 * 2. **主方法**： 
 * - `main` 方法通过 `SpringApplication.run` 启动应用程序。 
 * 3. **用途**： 
 * - 该类用于启动基于 Spring Boot 的空间行为依赖项目，适用于不使用默认数据库配置的场景。 
**/
@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class SpaceApplication {

	/**
	 * 主要
	 *
	 * @param args
	 * @throws
	 * @see String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpaceApplication.class, args);
	}

}
