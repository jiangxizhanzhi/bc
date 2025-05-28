/**
 * 这段代码是一个 Spring Boot 配置类，用于动态加载数据库数据源。以下是代码的简要分析： 
 * 1. **注解解析**： 
 * - `@Configuration`：表明这是一个配置类，Spring 容器会扫描并注册其中定义的 Bean。 
 * - `@PropertySource`：指定加载外部属性文件 `classpath:db/datasource.properties`，该文件中包含数据库连接的相关配置信息。 
 * 2. **方法功能**： 
 * - `dataSource()` 方法被标注为 `@Bean`，意味着它返回的对象会被 Spring 容器管理为一个 Bean。 
 * - 使用 `@ConfigurationProperties(prefix = "spring.datasource")` 将 `datasource.properties` 文件中的属性绑定到数据源配置上。 
 * - 调用 `DataSourceBuilder.create().build()` 创建并返回一个数据源实例。 
 * 3. **日志记录**： 
 * - 使用 Lombok 的 `@Slf4j` 注解自动生成日志对象 `log`，并在数据源初始化成功后记录一条日志信息。 
 * 总结：此代码通过读取配置文件中的数据库连接信息，动态创建并配置了一个数据源，供 Spring 应用使用。 
**/
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
 * @author yinqingrong
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Configuration
@PropertySource("classpath:db/datasource.properties")
public class DataSourceConfig {

	/**
	 * 数据源
	 *
	 * @return 数据源
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
