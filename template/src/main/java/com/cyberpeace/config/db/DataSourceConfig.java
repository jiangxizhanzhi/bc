package com.cyberpeace.config.db;

import java.util.Map;

import javax.sql.DataSource;

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

	private String rtksJdbcUrl;

	private String rtksUserName;

	private String rtksPassword;

	private String rtksDriverClass;

	private String ojJdbcUrl;

	private String ojUserName;

	private String ojPassword;

	private String ojDriverClass;

	@Override
	public void setEnvironment(Environment environment) {
		rtksJdbcUrl = environment.getProperty("spring.datasource.rtks.jdbc-url");
		rtksUserName = environment.getProperty("spring.datasource.rtks.username");
		rtksPassword = environment.getProperty("spring.datasource.rtks.password");
		rtksDriverClass = environment.getProperty("spring.datasource.rtks.driver-class-name");

		ojJdbcUrl = environment.getProperty("spring.datasource.oj.jdbc-url");
		ojUserName = environment.getProperty("spring.datasource.oj.username");
		ojPassword = environment.getProperty("spring.datasource.oj.password");
		ojDriverClass = environment.getProperty("spring.datasource.oj.driver-class-name");

		log.info("config loaded! rtksDbConnection={}|{}|{}|{}, ojDbConnetcion={}|{}|{}|{}",
				new Object[] { rtksJdbcUrl, rtksUserName, rtksPassword, rtksDriverClass, ojJdbcUrl,
						ojUserName, ojPassword, ojDriverClass });
	}

	@Bean(name = "rtks")
	@ConfigurationProperties(prefix = "spring.datasource.rtks")
	public DataSource rtksDataSource() {
		DataSource dataSource = DataSourceBuilder.create().url(rtksJdbcUrl).password(rtksPassword)
				.username(rtksUserName).driverClassName(rtksDriverClass).build();

		log.info("config loaded! rtksDB init success!");

		dataSourceMap.put("rtks", dataSource);

		return dataSource;
	}

	@Bean(name = "oj")
	@ConfigurationProperties(prefix = "spring.datasource.oj")
	public DataSource ojDataSource() {
		DataSource dataSource = DataSourceBuilder.create().build();

		log.info("config loaded! ojDB init success!");

		dataSourceMap.put("oj", dataSource);

		return dataSource;
	}

	public DataSource getDataSource(String schemaName) {
		return dataSourceMap.get(schemaName);
	}

}
