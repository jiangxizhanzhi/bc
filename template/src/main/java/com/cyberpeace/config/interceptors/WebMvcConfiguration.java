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
/**
 * 该代码定义了一个Spring MVC配置类 `WebMvcConfiguration`，用于自定义拦截器和异常处理器。以下是代码的主要功能和结构分析： 
 * 1. **类的功能**： 
 * - 实现了 `WebMvcConfigurer` 接口，允许开发者自定义Spring MVC的行为。 
 * - 配置了多个拦截器（Interceptors）和自定义的异常处理器。 
 * 2. **成员变量**： 
 * - `practiceGateWayInterceptor`：用于拦截与练习网关相关的请求路径。 
 * - `requestInterceptor`：通用的请求拦截器。 
 * - `callCountInterceptor`：用于记录调用次数的拦截器。 
 * 3. **方法实现**： 
 * - `addInterceptors`：注册多个拦截器，并为每个拦截器指定拦截的路径模式。 
 * - `practiceGateWayInterceptor` 拦截 `/gateway/theory/**` 和 `/gateway/exercise/**` 的请求。 
 * - `requestInterceptor` 默认拦截所有请求。 
 * - `callCountInterceptor` 拦截 `/gateway/**` 和 `/space/**` 的请求。 
 * - `configureHandlerExceptionResolvers`：清空默认的异常处理器列表，并添加自定义的异常处理器 `CustomExceptionHandlerResolver`。 
 * 4. **注解**： 
 * - `@Configuration`：标识这是一个Spring配置类。 
 * - `@Autowired`：自动注入拦截器和异常处理器。 
 * 总结：该类通过自定义拦截器和异常处理器增强了Spring MVC的应用程序，提供了更细粒度的请求控制和统一的异常处理机制。 
**/
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
		registry.addInterceptor(callCountInterceptor).addPathPatterns("/**");
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

