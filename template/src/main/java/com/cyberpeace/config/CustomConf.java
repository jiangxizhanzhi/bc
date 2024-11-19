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
 *
 * @author yinqingrong@version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Configuration
public class CustomConf {

	/**
	 * 获取spring上下文util
	 *
	 * @return {@code SpringContextUtil }
	 * @throws
	 * @see SpringContextUtil
	 */
	@Bean
	public static SpringContextUtil getSpringContextUtil() {
		return new SpringContextUtil();
	}

	/**
	 * 获取配置util
	 *
	 * @return {@code PropertyPlaceholderConfigurer }
	 * @throws
	 * @see PropertyPlaceholderConfigurer
	 */
	@Bean
	public static PropertyPlaceholderConfigurer getConfigUtil() {
		PropertyPlaceholderConfigurer configurer = new ConfigUtil();

		Resource[] resources = new ClassPathResource[] {
				new ClassPathResource("modules/integration.properties"),
				new ClassPathResource("modules/oj.properties"),
				new ClassPathResource("modules/cr.properties"),
				new ClassPathResource("modules/rtks.properties"),
				new ClassPathResource("modules/common.properties"),
				new ClassPathResource("modules/evaluate.properties"),
				new ClassPathResource("application.properties"),
				new ClassPathResource("modules/ojj.properties"),
				new ClassPathResource("modules/errorMessageZH.properties"),
				new ClassPathResource("modules/errorMessageEN.properties") };
		configurer.setLocations(resources);

		return configurer;
	}

}
