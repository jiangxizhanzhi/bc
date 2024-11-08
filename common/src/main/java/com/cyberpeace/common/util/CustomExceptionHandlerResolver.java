package com.cyberpeace.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义例外处理器解析 器
 *
 * @author SN
 * @date 2024/11/08
 */
public class CustomExceptionHandlerResolver implements HandlerExceptionResolver {

	/**
	 * 解决异常
	 *
	 * @param httpServletRequest  HTTP Servlet 请求
	 * @param httpServletResponse HTTP Servlet 响应
	 * @param o                   o
	 * @param e                   e
	 * @return {@code ModelAndView }
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, Exception e) {
		// 异常透传到filter统一处理
		return null;
	}

}
