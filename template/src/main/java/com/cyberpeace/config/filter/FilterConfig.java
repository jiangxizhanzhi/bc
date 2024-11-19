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
 * @date 2024/11/19
 * @since 1.0.0
 */
@Configuration
public class FilterConfig {

	/**
	 * 例外过滤器
	 *
	 * @return {@code FilterRegistrationBean }
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
	 * @return {@code FilterRegistrationBean }
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
	 * @return {@code FilterRegistrationBean }
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
	 * 示范过滤器
	 *
	 * @return {@code FilterRegistrationBean }
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
	 * @return {@code FilterRegistrationBean }
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
