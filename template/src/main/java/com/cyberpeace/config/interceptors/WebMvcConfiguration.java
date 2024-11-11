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
 * 该配置类用于注册自定义拦截器和异常处理解析器
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

	// 自动注入理论学习和练习的拦截器
	@Autowired
	private PracticeGateWayInterceptor practiceGateWayInterceptor;

	// 自动注入请求拦截器
	@Autowired
	private RequestInterceptor requestInterceptor;

	/*
	 * 添加拦截器
	 * 该方法用于向Spring MVC的拦截器注册表中添加拦截器，以应用于特定的请求路径
	 * 参数: InterceptorRegistry registry - Spring MVC的拦截器注册表
	 * 无返回值
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 添加实践网关拦截器，应用于理论学习和练习的路径
		registry.addInterceptor(practiceGateWayInterceptor).addPathPatterns("/gateway/theory/**",
				"/gateway/exercise/**");
		// 添加请求拦截器，应用于所有请求
		registry.addInterceptor(requestInterceptor);
	}

	/*
	 * 配置处理器异常解析器
	 * 该方法用于自定义Spring MVC的异常处理逻辑，通过添加自定义的异常处理解析器来接管默认的异常处理
	 * 参数: List<HandlerExceptionResolver> resolvers - Spring MVC的处理器异常解析器列表
	 * 无返回值
	 */
	@Override
	public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
		// 清空默认的异常处理解析器，以便使用自定义的解析器
		resolvers.clear();
		// 添加自定义的异常处理解析器
		resolvers.add(new CustomExceptionHandlerResolver());
	}
}
