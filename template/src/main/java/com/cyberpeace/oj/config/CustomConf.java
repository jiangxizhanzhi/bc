package com.cyberpeace.oj.config;

import com.cyberpeace.oj.common.util.SpringContextUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * 自定义配置项
 */
@Configuration
@PropertySource("classpath:custom.properties")
public class CustomConf {

	private String errorPrefix;

	@Bean
	public SpringContextUtil getSpringContextUtil() {
		return new SpringContextUtil();
	}
}
