package com.cyberpeace.config.auth;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;

import com.cyberpeace.auth.security.CustomRedisSessionRegistry;

@Configuration
public class SecurityConfig {

	@Resource
	@Qualifier("sessionRepository")
	private RedisIndexedSessionRepository sessionRepository;

	@Bean
	public CustomRedisSessionRegistry<? extends Session> sessionRegistry() {
		return new CustomRedisSessionRegistry<>(sessionRepository);
	}
}
