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

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		log.info("config loaded! dbDB init success!");

		return dataSource;
	}

}
