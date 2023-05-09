package com.cyberpeace.config.filter;

import com.cyberpeace.config.auth.StaticResourceFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cyberpeace.config.auth.DebounceFilter;
import com.cyberpeace.config.auth.ExceptionFilter;
import com.cyberpeace.config.auth.MyFilter;

/**
 * 过滤器配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean exceptionFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(101);
		filterRegistrationBean.setFilter(new ExceptionFilter());
		filterRegistrationBean.setName("exceptionFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean myFilter() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(105);
		filterRegistrationBean.setFilter(new MyFilter());
		filterRegistrationBean.setName("myFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}

	@Bean
	public FilterRegistrationBean debounceFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(106);
		filterRegistrationBean.setFilter(new DebounceFilter());
		filterRegistrationBean.setName("deBounceFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
	@Bean
	public FilterRegistrationBean staticResourceFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(107);
		filterRegistrationBean.setFilter(new StaticResourceFilter());
		filterRegistrationBean.setName("staticResourceFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}
