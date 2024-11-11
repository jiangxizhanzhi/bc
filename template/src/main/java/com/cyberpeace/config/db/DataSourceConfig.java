package com.cyberpeace.config.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/datasource.properties")
public class DataSourceConfig {
    /**
     * 配置并创建数据源Bean
     *
     * 该方法通过Spring的DataSourceBuilder创建一个数据源实例，
     * 并将其配置属性前缀设置为"spring.datasource"，以便自动加载
     * 应用配置文件中相关的数据源配置属性
     *
     * @return DataSource 数据源实例，供其他Bean注入和使用
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();

        // 记录数据源初始化成功的信息
        log.info("config loaded! dbDB init success!");

        return dataSource;
    }

}

