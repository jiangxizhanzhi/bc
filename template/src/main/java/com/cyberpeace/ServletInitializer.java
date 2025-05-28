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
