package com.cyberpeace.auth.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.session.DelegatingIndexResolver;
import org.springframework.session.FlushMode;
import org.springframework.session.PrincipalNameIndexResolver;
import org.springframework.session.Session;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

import com.cyberpeace.auth.constant.AuthConstant;

/**
 * 基于 SpringHttpSessionConfiguration 拓展
 * @author SN
 * @date 2023/12/05
 */
@EnableSpringHttpSession
public class RedisSessionConfig {

	/**
	 * 自定义的 redis 连接属性
	 */
	@Value("${redis.host}")
	String host;

	@Value("${redis.password:}")
	String password;

	@Value("${redis.port}")
	int port;

	@Value("${redis.database:0}")
	int database;

	@Value("${redis.maxInactiveIntervalInSeconds}")
	int maxInactiveIntervalInSeconds;

	@Bean
	public RedisIndexedSessionRepository sessionRepository() {

		// this.host = ConfigUtil.getContextProperty("redis.host");
		// this.port = new Integer(ConfigUtil.getContextProperty("redis.port"));
		// this.password = ConfigUtil.getContextProperty("redis.password");
		// this.maxInactiveIntervalInSeconds = new
		// Integer(ConfigUtil.getContextProperty("redis.maxInactiveIntervalInSeconds"));

		// redis 配置
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
		configuration.setHostName(host);
		configuration.setPort(port);
		configuration.setPassword(password);
		configuration.setDatabase(database);

		// connectionFactory 创建
		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(configuration);
		// 相当于交给 Spring 容器初始化
		connectionFactory.afterPropertiesSet();

		// RedisTemplate 创建
		RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(connectionFactory);
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		// 相当于交给 Spring 容器初始化
		redisTemplate.afterPropertiesSet();

		// SessionRepository 创建
		RedisIndexedSessionRepository sessionRepository = new RedisIndexedSessionRepository(
				redisTemplate);
		// 自定义过期时间
		sessionRepository.setDefaultMaxInactiveInterval(maxInactiveIntervalInSeconds);
		// 自定义 session 前缀
		sessionRepository.setRedisKeyNamespace("toolbox:session");
		// 自定义 principal name
		sessionRepository.setIndexResolver(
				new DelegatingIndexResolver<>(new CustomPrincipalNameIndexResolver<>()));
		sessionRepository.setFlushMode(FlushMode.IMMEDIATE);
		return sessionRepository;
	}
}

class CustomPrincipalNameIndexResolver<S extends Session> extends PrincipalNameIndexResolver<S> {

	@Override
	public String resolveIndexValueFor(S session) {
		String principalName = session.getAttribute(getIndexName());
		if (principalName != null) {
			return principalName;
		}
		Object authentication = session.getAttribute(AuthConstant.SPRING_SECURITY_CONTEXT);
		if (authentication != null) {
			TbUser tbUser = (TbUser) ((SecurityContext) authentication).getAuthentication()
					.getPrincipal();
			return tbUser.getUserId();
		}

		return null;
	}
}