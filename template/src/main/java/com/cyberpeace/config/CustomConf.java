package com.cyberpeace.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SpringContextUtil;

/**
 * 自定义配置项
 * @author yinqingrong
 */
@Configuration
public class CustomConf {

	@Bean
	public SpringContextUtil getSpringContextUtil() {
		return new SpringContextUtil();
	}

	@Bean
	public PropertyPlaceholderConfigurer getConfigUtil() {
		PropertyPlaceholderConfigurer configurer = new ConfigUtil();

		Resource[] resources = new ClassPathResource[] {
				new ClassPathResource("modules/integration.properties"),
				new ClassPathResource("modules/oj.properties"),
				new ClassPathResource("modules/cr.properties"),
				new ClassPathResource("modules/rtks.properties"),
				new ClassPathResource("application.properties") };
		configurer.setLocations(resources);

		return configurer;
	}

}
