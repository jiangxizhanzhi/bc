package com.cyberpeace;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * servlet初始化器
 *
 * @author admin
 * @date 2024/11/12
 */
public class ServletInitializer extends SpringBootServletInitializer {

	/**
	 * 配置
	 *
	 * @param application 应用
	 * @return {@link SpringApplicationBuilder }
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(OJApplication.class);
	}

}
