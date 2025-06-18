package com.cyberpeace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * ojapplication
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */// @EnableCaching
// @EnableScheduling
/**
 * 该代码是一个 Spring Boot 应用程序的主类，以下是简要分析： 
 * 1. **包与导入**： 
 * - 代码位于 `com.cyberpeace` 包中。 
 * - 导入了必要的 Spring Boot 类和注解。 
 * 2. **注解说明**： 
 * - `@SpringBootApplication`：标注这是一个 Spring Boot 应用程序，并排除了默认的 `DataSourceAutoConfiguration` 配置。 
 * - `@ServletComponentScan`：启用 Servlet 组件扫描。 
 * - `scanBasePackages`：指定扫描的基础包为 `com.cyberpeace`。 
 * 3. **主方法**： 
 * - `main` 方法通过 `SpringApplication.run` 启动应用程序。 
 * 4. **功能**： 
 * - 该类是应用程序的入口点，用于启动 Spring Boot 应用。 
**/
@ServletComponentScan
@EnableAspectJAutoProxy
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class },
		scanBasePackages = { "com.cyberpeace" })
public class OJApplication {

	/**
	 * 主要
	 *
	 * @param args 参数
	 * @throws
	 * @see String[]
	 */
	public static void main(String[] args) {
		SpringApplication.run(OJApplication.class, args);
	}

}

