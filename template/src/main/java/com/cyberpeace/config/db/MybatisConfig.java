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

// 配置类，用于扫描指定包下的Mapper接口并进行自动注册
@Configuration
@MapperScan(basePackages = { "com.cyberpeace.**.mapper" })
public class MybatisConfig {

	// 自动注入数据源
	@Autowired
	private DataSource dataSource;

	// 逻辑删除标识
	private String DELETE_VALUE = "0";
	// 未删除标识
	private String NOT_DELETE = "1";

	/**
	 * 创建并配置SqlSessionFactory bean
	 * @return 配置好的SqlSessionFactory对象
	 * @throws Exception 如果配置过程中发生错误则抛出异常
	 */
	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		MybatisConfiguration configuration = new MybatisConfiguration();
		// 设置默认的脚本语言驱动
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		// 设置JDBC类型对于NULL值的处理
		configuration.setJdbcTypeForNull(JdbcType.NULL);

		// 自定义session，需要手动添加拦截器
		configuration.addInterceptor(paginationInterceptor());
		configuration.addInterceptor(demonstrationWhiteInterceptor());
		sqlSessionFactory.setConfiguration(configuration);
		// 设置全局配置
		sqlSessionFactory.setGlobalConfig(globalConfig());
		// 设置mapper映射文件的位置
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mapper/*/*.xml"));
		return sqlSessionFactory.getObject();
	}

	/**
	 * 创建并配置分页拦截器
	 * @return 配置好的MybatisPlusInterceptor对象
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 指定数据库方言为 MYSQL
		PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		// 设置单页分页条数限制
		pageInterceptor.setMaxLimit(10000L);
		interceptor.addInnerInterceptor(pageInterceptor);
		return interceptor;
	}

	/**
	 * 创建并配置全局配置对象
	 * @return 配置好的GlobalConfig对象
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		// 设置元对象处理器
		globalConfig.setMetaObjectHandler(metaObjectHandler());
		// 配置数据库相关设置
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		// dbConfig.setLogicDeleteField("status");
		// 设置逻辑删除值
		dbConfig.setLogicDeleteValue("UNIX_TIMESTAMP()");
		// 设置未删除值
		dbConfig.setLogicNotDeleteValue(NOT_DELETE);
		globalConfig.setDbConfig(dbConfig);
		return globalConfig;
	}

	/**
	 * 创建并配置SqlSessionTemplate bean
	 * @return 配置好的SqlSessionTemplate对象
	 * @throws Exception 如果配置过程中发生错误则抛出异常
	 */
	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	/**
	 * 创建并配置演示环境白名单拦截器
	 * @return 配置好的DemonstrationWhiteInterceptor对象
	 */
	public DemonstrationWhiteInterceptor demonstrationWhiteInterceptor() {
		return new DemonstrationWhiteInterceptor();
	}

	/**
	 * 创建并配置元对象处理器
	 * @return 配置好的MetaObjectHandler对象
	 */
	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MybatisFillHandler();
	}

	/**
	 * 创建并配置数据源事务管理器
	 * @return 配置好的DataSourceTransactionManager对象
	 */
	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	/**
	 * 创建并配置事务模板
	 * @return 配置好的TransactionTemplate对象
	 */
	@Bean
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(transactionManager());
	}
}

