package com.cyberpeace.config.filter;

import com.cyberpeace.config.auth.*;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
	public FilterRegistrationBean demonstrationFilter() {

		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		filterRegistrationBean.setOrder(100);
		filterRegistrationBean.setFilter(new DemonstrationFilter());
		filterRegistrationBean.setName("demonstrationFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
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
