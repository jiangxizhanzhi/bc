package com.cyberpeace.config.db;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据源配置
 *
 * @author yinqingrong@version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/datasource.properties")
public class DataSourceConfig {

	/**
	 * 数据源
	 *
	 * @return {@code DataSource }
	 * @throws
	 * @see DataSource
	 */
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		log.info("config loaded! dbDB init success!");

		return dataSource;
	}

}
