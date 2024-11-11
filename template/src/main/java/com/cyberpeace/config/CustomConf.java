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
 * 该类用于定义项目所需的自定义Spring配置
 * @author yinqingrong
 */
@Configuration
public class CustomConf {

    /**
     * 创建SpringContextUtil实例
     * 该方法定义了一个Spring Bean，用于获取Spring上下文工具类实例
     * @return SpringContextUtil实例，用于获取Spring上下文中的Bean
     */
    @Bean
    public static SpringContextUtil getSpringContextUtil() {
        return new SpringContextUtil();
    }

    /**
     * 创建PropertyPlaceholderConfigurer实例
     * 该方法定义了一个Spring Bean，用于配置属性文件
     * 它初始化了多个属性文件，以便在项目中使用
     * @return PropertyPlaceholderConfigurer实例，用于加载属性文件
     */
    @Bean
    public static PropertyPlaceholderConfigurer getConfigUtil() {
        PropertyPlaceholderConfigurer configurer = new ConfigUtil();

        // 定义并加载多个属性文件，以便在项目中使用
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

