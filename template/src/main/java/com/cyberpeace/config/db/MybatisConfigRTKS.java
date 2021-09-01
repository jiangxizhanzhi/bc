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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.cyberpeace.rtks.**.mapper",
		sqlSessionFactoryRef = "rtksSqlSessionFactory")
public class MybatisConfigRTKS {

	@Autowired
	@Qualifier("rtks")
	private DataSource dataSource;

	@Bean(name = "rtksSqlSessionFactory")
	public SqlSessionFactory rtksSqlSessionFactory() throws Exception {
		MybatisSqlSessionFactoryBean sqlSessionFactory = new MybatisSqlSessionFactoryBean();
		sqlSessionFactory.setDataSource(dataSource);
		MybatisConfiguration configuration = new MybatisConfiguration();
		configuration.setDefaultScriptingLanguage(MybatisXMLLanguageDriver.class);
		configuration.setJdbcTypeForNull(JdbcType.NULL);
		// 自定义session，需要手动添加拦截器
		configuration.addInterceptor(rtksPaginationInterceptor());
		sqlSessionFactory.setConfiguration(configuration);
		sqlSessionFactory.setGlobalConfig(rtksGlobalConfig());
		sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
				.getResources("classpath*:mapper/rtks/*.xml"));
		return sqlSessionFactory.getObject();
	}

	@Bean(name = "rtksPaginationInterceptor")
	public MybatisPlusInterceptor rtksPaginationInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 指定数据库方言为 MYSQL
		PaginationInnerInterceptor pageInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
		// 单页分页条数限制
		pageInterceptor.setMaxLimit(10000L);
		interceptor.addInnerInterceptor(pageInterceptor);
		return interceptor;
	}

	@Bean(name = "rtksGlobalConfig")
	public GlobalConfig rtksGlobalConfig() {
		GlobalConfig globalConfig = new GlobalConfig();
		globalConfig.setMetaObjectHandler(rtksMetaObjectHandler());

		return globalConfig;
	}

	@Bean(name = "rtksSqlSessionTemplate")
	public SqlSessionTemplate rtksSqlSessionTemplate() throws Exception {
		return new SqlSessionTemplate(rtksSqlSessionFactory());
	}

	@Bean(name = "rtksMetaObjectHandler")
	public MetaObjectHandler rtksMetaObjectHandler() {
		return new MybatisFillHandler();
	}

	@Bean(name = "rtksTransactionManager")
	public DataSourceTransactionManager rtksTransactionManager() {
		return new DataSourceTransactionManager(dataSource);
	}

	@Bean(name = "rtksTransactionTemplate")
	public TransactionTemplate rtksTransactionTemplate() {
		return new TransactionTemplate(rtksTransactionManager());
	}
}
