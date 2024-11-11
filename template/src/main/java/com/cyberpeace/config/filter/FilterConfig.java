package com.cyberpeace.config.filter;

import com.cyberpeace.config.auth.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类，用于注册自定义过滤器
 */
@Configuration
public class FilterConfig {

    /**
     * 注册异常处理过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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
     * 注册自定义过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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
     * 注册去抖动过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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
     * 注册静态资源过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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
     * 注册演示过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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
     * 注册演示白名单过滤器
     *
     * @return FilterRegistrationBean 对象，包含过滤器的配置信息
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

