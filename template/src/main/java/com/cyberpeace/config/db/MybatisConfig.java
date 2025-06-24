package com.cyberpeace.config.db;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.baomidou.mybatisplus.core.MybatisXMLLanguageDriver;
import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
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

import javax.sql.DataSource;

/**
 * mybatis 配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
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
	 * @return {@code SqlSessionFactory }
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
	 * @return {@code MybatisPlusInterceptor }
	 * @throws
	 * @see MybatisPlusInterceptor
	 */
	@Bean
	public MybatisPlusInterceptor paginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 指定数据库方言为 MYSQL
		PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.DM);
		// 单页分页条数限制
		pageInterceptor.setMaxLimit(10000L);
		interceptor.addInnerInterceptor(pageInterceptor);
		return interceptor;
	}

	/**
	 * 全局配置
	 *
	 * @return {@code GlobalConfig }
	 * @throws
	 * @see GlobalConfig
	 */
	@Bean
	public GlobalConfig globalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(metaObjectHandler());
		GlobalConfig.DbConfig dbConfig = new GlobalConfig.DbConfig();
		dbConfig.setLogicDeleteValue("UNIX_TIMESTAMP()");
		dbConfig.setLogicNotDeleteValue(NOT_DELETE);
		globalConfig.setDbConfig(dbConfig);
		return globalConfig;
	}

	/**
	 * SQL会话模板
	 *
	 * @return {@code SqlSessionTemplate }
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
	 * @return {@code DemonstrationWhiteInterceptor }
	 * @throws
	 * @see DemonstrationWhiteInterceptor
	 */
	public DemonstrationWhiteInterceptor demonstrationWhiteInterceptor() {
		return new DemonstrationWhiteInterceptor();
	}

	/**
	 * 元对象处理程序
	 *
	 * @return {@code MetaObjectHandler }
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
	 * @return {@code DataSourceTransactionManager }
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
	 * @return {@code TransactionTemplate }
	 * @throws
	 * @see TransactionTemplate
	 */
	@Bean
	public TransactionTemplate transactionTemplate() {
		return new TransactionTemplate(transactionManager());
	}
}
