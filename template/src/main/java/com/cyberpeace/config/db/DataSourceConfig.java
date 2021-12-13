package com.cyberpeace.config.db;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/datasource.properties")
public class DataSourceConfig {

	private Map<String, DataSource> dataSourceMap = Maps.newHashMap();

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.bc")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		log.info("config loaded! dbDB init success!");

		dataSourceMap.put("bc", dataSource);

		return dataSource;
	}

	public DataSource getDataSource(String schemaName) {
		return dataSourceMap.get(schemaName);
	}

}
