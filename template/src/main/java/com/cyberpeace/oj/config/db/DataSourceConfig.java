package com.cyberpeace.oj.config.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author yinqingrong
 */
@Configuration
@PropertySource("classpath:config/datasource.properties")
public class DataSourceConfig {

	@Bean(name = "oj")
	@ConfigurationProperties(prefix = "spring.datasource.oj")
	public DataSource crDataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		return dataSource;
	}

}
