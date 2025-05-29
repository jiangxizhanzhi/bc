package com.cyberpeace.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

/**
 * mybatis 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 该代码是一个 Spring Boot 配置类，用于配置 MyBatis 和 MyBatis-Plus 的相关功能。以下是代码的主要功能和结构分析： 
 * 1. **注解与配置**： 
 * - 使用 `@Configuration` 注解表明这是一个 Spring 配置类。 
 * - 使用 `@MapperScan` 注解指定 Mapper 接口所在的包路径。 
 * 2. **数据源注入**： 
 * - 通过 `@Autowired` 注解将 `DataSource` 对象注入到配置类中。 
 * 3. **SQL 会话工厂**： 
 * - 定义了 `sqlSessionFactory()` 方法，用于创建 `SqlSessionFactory` 实例。 
 * - 使用 `MybatisSqlSessionFactoryBean` 设置数据源、自定义配置、全局配置以及 XML 映射文件的位置。 
 * 4. **分页拦截器**： 
 * - 定义了 `paginationInterceptor()` 方法，用于实现分页功能。 
 * - 使用 `PaginationInnerInterceptor` 并指定数据库方言为 MySQL，设置单页最大分页条数为 10000。 
 * 5. **全局配置**： 
 * - 定义了 `globalConfig()` 方法，用于配置 MyBatis-Plus 的全局设置。 
 * - 包括逻辑删除字段的配置（如删除值和非删除值）以及元对象处理器的设置。 
 * 6. **SQL 会话模板**： 
 * - 定义了 `sqlSessionTemplate()` 方法，用于创建 `SqlSessionTemplate` 实例，方便在项目中使用 MyBatis 的会话操作。 
 * 7. **演示白色拦截器**： 
 * - 定义了 `demonstrationWhiteInterceptor()` 方法，用于创建自定义的拦截器。 
 * 8. **元对象处理器**： 
 * - 定义了 `metaObjectHandler()` 方法，用于处理 MyBatis-Plus 的元对象填充逻辑。 
 * 9. **事务管理**： 
 * - 定义了 `transactionManager()` 方法，用于创建基于数据源的事务管理器。 
 * - 定义了 `transactionTemplate()` 方法，用于创建事务模板。 
 * ### 总结 
 * 该配置类主要完成了以下几个方面的配置： 
 * - 数据源的注入和管理。 
 * - MyBatis 和 MyBatis-Plus 的核心配置（如会话工厂、分页拦截器、全局配置等）。 
 * - 提供了事务管理和事务模板的支持。 
 * - 集成了自定义拦截器和元对象处理器，增强了 MyBatis 的功能。 
**/
@Configuration
@MapperScan(basePackages = { "com.cyberpeace.**.mapper" })
public class MybatisConfig {

	/**
	 * 数据源
	 *
	 * @see DataSource
	 */
	@Autowired
	private DataSource dataSource;

	/**
	 * 逻辑删除
	 *
	 * @see String
	 */
	private String DELETE_VALUE = "0";

	/**
	 * 非删除
	 *
	 * @see String
	 */
	private String NOT_DELETE = "1";

	/**
	 * SQL会话厂
	 *
	 * @return SQL会话厂
	 * @throws @throws Exception 例外
	 * @see SqlSessionFactory
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);

		// 自定义session，需要手动添加拦截器
		configuration.addInterceptor(paginationInterceptor());
		configuration.addInterceptor(demonstrationWhiteInterceptor());
		sqlSessionFactory.setConfiguration(configuration);
		sqlSessionFactory.setGlobalConfig(globalConfig());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mapper/*/*.xml"));
		return sqlSessionFactory.getObject();
	}

	/**
	 * 分页拦截器
	 *
	 * @return
	 * @throws
	 * @see MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 指定数据库方言为 MYSQL
		PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		// 单页分页条数限制
		pageInterceptor.setMaxLimit(10000L);
		interceptor.addInnerInterceptor(pageInterceptor);
		return interceptor;
	}

	/**
	 * @return 全局配置
	 * @throws
	 * @see GlobalConfig
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(metaObjectHandler());
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		// dbConfig.setLogicDeleteField("status");
		dbConfig.setLogicDeleteValue("UNIX_TIMESTAMP()");
		dbConfig.setLogicNotDeleteValue(NOT_DELETE);
		globalConfig.setDbConfig(dbConfig);
		return globalConfig;
	}

	/**
	 * SQL会话template
	 *
	 * @return SQL会话模板
	 * @throws @throws Exception 例外
	 * @see SqlSessionTemplate
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	/**
	 * 演示白色拦截器
	 *
	 * @return 演示白色拦截器
	 * @throws
	 * @see DemonstrationWhiteInterceptor
	 */
	public DemonstrationWhiteInterceptor demonstrationWhiteInterceptor() {
		return new DemonstrationWhiteInterceptor();
	}

	/**
	 * 元对象处理程序
	 *
	 * @return 元对象处理程序
	 * @throws
	 * @see MetaObjectHandler
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MybatisFillHandler();
	}

	/**
	 * 事务经理
	 *
	 * @return 数据源事务经理
	 * @throws
	 * @see DataSourceTransactionManager
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * 事务模板
	 *
	 * @return 事务模板
	 * @throws
	 * @see TransactionTemplate
	 */
	@Bean
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(transactionManager());
	}
}

