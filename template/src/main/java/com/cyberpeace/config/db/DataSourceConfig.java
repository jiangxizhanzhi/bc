package com.cyberpeace.config.db;

import java.net.URL;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/datasource.properties")
public class DataSourceConfig implements EnvironmentAware {

	private Map<String, DataSource> dataSourceMap = Maps.newHashMap();

	private String jdbcUrl;

	private String userName;

	private String password;

	private String driverClass;

	@Override
	public void setEnvironment(Environment environment) {
		jdbcUrl = environment.getProperty("spring.datasource.bc.jdbc-url");
		userName = environment.getProperty("spring.datasource.bc.username");
		password = environment.getProperty("spring.datasource.bc.password");
		driverClass = environment.getProperty("spring.datasource.bc.driver-class-name");

		log.info("config loaded! dbConnection={}|{}|{}|{}",
				new Object[] { jdbcUrl, userName, "password ignored", driverClass });
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.bc")
	public DataSource dataSource() {
		DataSource dataSource = DataSourceBuilder.create().url(jdbcUrl).password(password)
				.username(userName).driverClassName(driverClass).build();

		log.info("config loaded! dbDB init success!");

		dataSourceMap.put("bc", dataSource);

		return dataSource;
	}

	public DataSource getDataSource(String schemaName) {
		return dataSourceMap.get(schemaName);
	}

}
