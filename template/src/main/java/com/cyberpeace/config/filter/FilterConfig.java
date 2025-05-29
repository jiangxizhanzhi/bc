package com.cyberpeace.config.filter;

import com.cyberpeace.config.auth.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 过滤器配置
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码是一个Spring Boot配置类 `FilterConfig`，用于定义并注册多个自定义过滤器。以下是代码的详细分析： 
 * --- 
 * ### **1. 类的作用** 
 * - `FilterConfig` 是一个 Spring 配置类，通过 `@Configuration` 注解标记，表示这是一个配置类。 
 * - 它的主要功能是为应用程序注册多个过滤器（Filter），并将它们绑定到 Spring 的过滤器链中。 
 * --- 
 * ### **2. 方法概述** 
 * 该类中有 6 个方法，每个方法都使用了 `@Bean` 注解，表示这些方法返回的对象会被 Spring 容器管理为 Bean。每个方法的功能如下： 
 * #### **2.1 `exceptionFilter()`** 
 * - 注册了一个名为 `"exceptionFilter"` 的过滤器。 
 * - 使用 `ExceptionFilter` 实现了异常处理逻辑。 
 * - 设置了过滤器的执行顺序为 `101`（越小优先级越高）。 
 * - 匹配所有 URL (`/*`)。 
 * #### **2.2 `myFilter()`** 
 * - 注册了一个名为 `"myFilter"` 的过滤器。 
 * - 使用 `MyFilter` 实现了自定义逻辑。 
 * - 设置了过滤器的执行顺序为 `105`。 
 * - 匹配所有 URL (`/*`)。 
 * #### **2.3 `debounceFilter()`** 
 * - 注册了一个名为 `"deBounceFilter"` 的过滤器。 
 * - 使用 `DebounceFilter` 实现了防抖逻辑。 
 * - 设置了过滤器的执行顺序为 `106`。 
 * - 匹配所有 URL (`/*`)。 
 * #### **2.4 `staticResourceFilter()`** 
 * - 注册了一个名为 `"staticResourceFilter"` 的过滤器。 
 * - 使用 `StaticResourceFilter` 实现了静态资源处理逻辑。 
 * - 设置了过滤器的执行顺序为 `107`。 
 * - 匹配所有 URL (`/*`)。 
 * #### **2.5 `demonstrationFilter()`** 
 * - 注册了一个名为 `"demonstrationFilter"` 的过滤器。 
 * - 使用 `DemonstrationFilter` 实现了演示逻辑。 
 * - 设置了过滤器的执行顺序为 `100`。 
 * - 匹配所有 URL (`/*`)。 
 * #### **2.6 `demonstrationWhiteFilter()`** 
 * - 注册了一个名为 `"demonstrationWhiteFilter"` 的过滤器。 
 * - 使用 `DemonstrationWhiteFilter` 实现了演示白名单逻辑。 
 * - 设置了过滤器的执行顺序为 `100`。 
 * - 匹配所有 URL (`/*`)。 
 * --- 
 * ### **3. 共同点** 
 * - 每个方法都创建了一个 `FilterRegistrationBean` 对象，并设置了以下属性： 
 * - **过滤器名称**：通过 `setName` 方法指定。 
 * - **过滤器实例**：通过 `setFilter` 方法注入具体的过滤器实现类。 
 * - **执行顺序**：通过 `setOrder` 方法设置。 
 * - **匹配路径**：通过 `addUrlPatterns` 方法设置为 `/*`，即匹配所有请求路径。 
 * - 过滤器的执行顺序由 `setOrder` 决定，数字越小优先级越高。 
 * --- 
 * ### **4. 总结** 
 * - 该类的核心作用是通过 Spring 提供的 `FilterRegistrationBean` 将多个自定义过滤器注册到 Spring 容器中，并将其集成到 Spring 的过滤器链中。 
 * - 每个过滤器都有特定的功能（如异常处理、防抖、静态资源处理等），并且通过 `setOrder` 控制了执行顺序。 
 * - 所有过滤器都匹配所有 URL (`/*`)，这意味着它们会对所有的 HTTP 请求生效。 
 * 这段代码的设计体现了良好的模块化和可扩展性，方便后续添加新的过滤器或调整过滤器的执行顺序。 
**/
@Configuration
public class FilterConfig {

	/**
	 * 例外过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean exceptionFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(101);
		filterRegistrationBean.setFilter(new ExceptionFilter());
		filterRegistrationBean.setName("exceptionFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 我的过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean myFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(105);
		filterRegistrationBean.setFilter(new MyFilter());
		filterRegistrationBean.setName("myFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 防抖过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean debounceFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(106);
		filterRegistrationBean.setFilter(new DebounceFilter());
		filterRegistrationBean.setName("deBounceFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 静态资源过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean staticResourceFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(107);
		filterRegistrationBean.setFilter(new StaticResourceFilter());
		filterRegistrationBean.setName("staticResourceFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 示范过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean demonstrationFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(100);
		filterRegistrationBean.setFilter(new DemonstrationFilter());
		filterRegistrationBean.setName("demonstrationFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	/**
	 * 示范白过滤器
	 *
	 * @return 过滤器注册豆
	 * @throws
	 * @see FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean demonstrationWhiteFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(100);
		filterRegistrationBean.setFilter(new DemonstrationWhiteFilter());
		filterRegistrationBean.setName("demonstrationWhiteFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}

