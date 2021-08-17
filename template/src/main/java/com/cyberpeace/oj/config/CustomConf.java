package com.cyberpeace.oj.config;

import com.cyberpeace.oj.common.util.ConfigUtil;
import com.cyberpeace.oj.common.util.SpringContextUtil;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 自定义配置项
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

        Resource[] resources = new ClassPathResource[]{
                new ClassPathResource("integration.properties"),
                new ClassPathResource("custom.properties")
        };
        configurer.setLocations(resources);

        return configurer;
    }

}
