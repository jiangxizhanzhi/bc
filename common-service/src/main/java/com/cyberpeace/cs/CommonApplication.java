package com.cyberpeace.cs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 该代码是一个 Spring Boot 应用程序的主类，以下是简要分析： 
 * 1. **注解说明**： 
 * - `@SpringBootApplication`：标注这是一个 Spring Boot 应用程序，包含 `@EnableAutoConfiguration`、`@ComponentScan` 和 `@Configuration`。 
 * - `exclude = { DataSourceAutoConfiguration.class }`：排除默认的数据源自动配置，避免加载默认数据源。 
 * - `scanBasePackages = { "com.cyberpeace" }`：指定扫描的基础包为 `com.cyberpeace`。 
 * - `@ServletComponentScan`：启用对自定义 Servlet、Filter 和 Listener 的扫描。 
 * 2. **功能描述**： 
 * - 该类用于启动 Spring Boot 应用程序。 
 * - 使用 `SpringApplication.run()` 方法启动应用，并传入当前类和命令行参数。 
 * 3. **关键点**： 
 * - 排除了默认的数据源配置，可能是因为项目不需要默认的数据源。 
 * - 指定的包路径确保了 Spring 扫描范围仅限于 `com.cyberpeace` 及其子包。 
**/
@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class CommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommonApplication.class, args);
	}

}

