package com.cyberpeace.config.db;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@Configuration
@MapperScan(basePackages = "com.cyberpeace.cr.**.mapper",
		sqlSessionFactoryRef = "crSqlSessionFactory")
public class MybatisConfigCR {

	@Autowired
	@Qualifier("cr")
	private DataSource dataSource;

	/** 逻辑删除*/
	private String DELETE_VALUE = "0";

	/** 非删除*/
	private String NOT_DELETE = "1";

	@Bean(name = "crSqlSessionFactory")
	public SqlSessionFactory crSqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		// 自定义session，需要手动添加拦截器
		configuration.addInterceptor(crPaginationInterceptor());
		sqlSessionFactory.setConfiguration(configuration);
		sqlSessionFactory.setGlobalConfig(crGlobalConfig());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mapper/cr/*.xml"));
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "crPaginationInterceptor")
	public MybatisPlusInterceptor crPaginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 指定数据库方言为 MYSQL
		PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		// 单页分页条数限制
		pageInterceptor.setMaxLimit(10000L);
		interceptor.addInnerInterceptor(pageInterceptor);
		return interceptor;
	}

	@Bean(name = "crGlobalConfig")
	public GlobalConfig crGlobalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(crMetaObjectHandler());
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		dbConfig.setLogicDeleteField("status");
		dbConfig.setLogicDeleteValue(DELETE_VALUE);
		dbConfig.setLogicNotDeleteValue(NOT_DELETE);
		globalConfig.setDbConfig(dbConfig);
		return globalConfig;
	}

	@Bean(name = "crSqlSessionTemplate")
	public SqlSessionTemplate crSqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(crSqlSessionFactory());
	}

	@Bean(name = "crMetaObjectHandler")
	public MetaObjectHandler crMetaObjectHandler() {
		return new MybatisFillHandler();
	}

	@Bean(name = "crTransactionManager")
	public DataSourceTransactionManager crTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "crTransactionTemplate")
	public TransactionTemplate crTransactionTemplate() {
		return new TransactionTemplate(crTransactionManager());
	}
}
