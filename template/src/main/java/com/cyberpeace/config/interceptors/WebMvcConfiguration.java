package com.cyberpeace.config.interceptors;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.cyberpeace.common.util.CustomExceptionHandlerResolver;

/**
 * 拦截器的配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
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
	 * 新增拦截 器
	 *
	 * @param registry 注册表
	 * @throws
	 * @see InterceptorRegistry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(practiceGateWayInterceptor).addPathPatterns("/gateway/theory/**",
				"/gateway/exercise/**");
		registry.addInterceptor(requestInterceptor);
	}

	/**
	 * 配置处理程序异常解析程序
	 *
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
