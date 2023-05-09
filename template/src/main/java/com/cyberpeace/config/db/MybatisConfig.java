package com.cyberpeace.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.logging.stdout.StdOutImpl;
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
 * mybatis配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Configuration
@MapperScan(basePackages = { "com.cyberpeace.**.mapper" })
public class MybatisConfig {

	@Autowired
	private DataSource dataSource;

	/** 逻辑删除*/
	private String DELETE_VALUE = "0";

	/** 非删除*/
	private String NOT_DELETE = "1";

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);

		// 自定义session，需要手动添加拦截器
		configuration.addInterceptor(paginationInterceptor());
		sqlSessionFactory.setConfiguration(configuration);
		sqlSessionFactory.setGlobalConfig(globalConfig());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mapper/*/*.xml"));
		return sqlSessionFactory.getObject();
	}

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

	@Bean
	public SqlSessionTemplate sqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(sqlSessionFactory());
	}

	@Bean
	public MetaObjectHandler metaObjectHandler() {
		return new MybatisFillHandler();
	}

	@Bean
	public DataSourceTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(transactionManager());
	}
}
