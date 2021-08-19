package com.cyberpeace.oj.config.db;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * @author yinqingrong
 */
@Configuration
@PropertySource("classpath:config/datasource.properties")
public class DataSourceConfig {

    @Bean(name = "rtks")
    @ConfigurationProperties(prefix = "spring.datasource.rtks")
    public DataSource rtksDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();

        return dataSource;
    }

    @Bean(name = "oj")
    @ConfigurationProperties(prefix = "spring.datasource.oj")
    public DataSource ojDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();

        return dataSource;
    }
}
