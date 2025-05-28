/**
 * 这段代码定义了一个 `ServletInitializer` 类，继承自 `SpringBootServletInitializer`，用于在部署到传统的 Servlet 容器（如 Tomcat）时初始化 Spring Boot 应用。以下是简要分析： 
 * 1. **类的功能**： 
 * - 该类的主要作用是配置 Spring Boot 应用程序以适应 Servlet 容器环境。 
 * - 通过重写 `configure` 方法，指定应用程序的入口类 `OJApplication`。 
 * 2. **方法分析**： 
 * - `configure(SpringApplicationBuilder application)`：此方法用于配置 Spring Boot 应用程序。它返回一个 `SpringApplicationBuilder` 对象，并通过调用 `application.sources(OJApplication.class)` 指定主应用程序类。 
 * 3. **注释与文档**： 
 * - 类和方法都有详细的注释，说明了其用途和功能，便于维护和理解。 
 * - 注解 `@author` 和 `@version` 提供了作者信息和版本号。 
 * 4. **技术背景**： 
 * - 使用了 Spring Boot 的 `SpringBootServletInitializer`，这是将 Spring Boot 应用部署到传统 Servlet 容器的标准方式。 
 * 总结：这是一个典型的 Spring Boot 应用程序 Servlet 初始化器类，确保应用能够在 Servlet 容器中正确启动并运行。 
**/
package com.cyberpeace;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Servlet 初始化器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see SpringBootServletInitializer
 * @since 1.0.0
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * 配置
	 *
	 * @param application 行为依赖
	 * @return 春天行为依赖建筑工人
	 * @throws
	 * @see SpringApplicationBuilder
	 * @see SpringApplicationBuilder
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OJApplication.class);
	}

}
