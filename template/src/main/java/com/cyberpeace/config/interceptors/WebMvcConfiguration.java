package com.cyberpeace.config.interceptors;

import com.cyberpeace.common.util.CustomExceptionHandlerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * 拦截器的配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see WebMvcConfigurer
 * @since 1.0.0
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	@Autowired
	private PracticeGateWayInterceptor practiceGateWayInterceptor;

	@Autowired
	private RequestInterceptor requestInterceptor;

	@Autowired
	private CallCountInterceptor callCountInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(practiceGateWayInterceptor).addPathPatterns("/gateway/theory/**",
				"/gateway/exercise/**");
		registry.addInterceptor(requestInterceptor);
		registry.addInterceptor(callCountInterceptor).addPathPatterns("/gateway/**", "/space/**");
	}

	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		// 劫持spring的异常处理
		resolvers.clear();
		resolvers.add(new CustomExceptionHandlerResolver());
	}
}
