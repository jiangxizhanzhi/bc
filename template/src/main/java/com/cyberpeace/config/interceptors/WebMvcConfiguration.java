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

	/**
	 * 练习门道路拦截 器
	 *
	 * @see PracticeGateWayInterceptor
	 */
	@Autowired
	private PracticeGateWayInterceptor practiceGateWayInterceptor;

	/**
	 * 请求拦截器
	 *
	 * @see RequestInterceptor
	 */
	@Autowired
	private RequestInterceptor requestInterceptor;

	/**
	 * 调用计数拦截 器
	 *
	 * @see CallCountInterceptor
	 */
	@Autowired
	private CallCountInterceptor callCountInterceptor;

	/**
	 * 新增interceptors
	 *
	 * @param registry
	 * @throws
	 * @see InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(practiceGateWayInterceptor).addPathPatterns("/gateway/theory/**",
				"/gateway/exercise/**");
		registry.addInterceptor(requestInterceptor);
		registry.addInterceptor(callCountInterceptor).addPathPatterns("/gateway/**", "/space/**");
	}

	/**
	 * @param resolvers 解析 器
	 * @throws
	 * @see List<HandlerExceptionResolver>
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		// 劫持spring的异常处理
		resolvers.clear();
		resolvers.add(new CustomExceptionHandlerResolver());
	}
}
