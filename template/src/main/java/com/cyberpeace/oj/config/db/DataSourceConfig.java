package com.cyberpeace.oj.config.db;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.google.common.collect.Maps;

/**
 * @author yinqingrong
 */
@Configuration
@PropertySource("classpath:config/datasource.properties")
public class DataSourceConfig {

	private Map<String, DataSource> dataSourceMap = Maps.newHashMap();

	@Bean(name = "rtks")
	@ConfigurationProperties(prefix = "spring.datasource.rtks")
	public DataSource rtksDataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		dataSourceMap.put("rtks", dataSource);

		return dataSource;
	}

	@Bean(name = "oj")
	@ConfigurationProperties(prefix = "spring.datasource.oj")
	public DataSource ojDataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		dataSourceMap.put("oj", dataSource);

		return dataSource;
	}

	public DataSource getDataSource(String schemaName) {
		return dataSourceMap.get(schemaName);
	}

}
