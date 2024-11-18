package com.cyberpeace.common.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
 * 自定义异常处理程序解析程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see HandlerExceptionResolver
 * @since 1.0.0
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
	 * @throws
	 * @see HttpServletRequest
	 * @see HttpServletResponse
	 * @see Object
	 * @see Exception
	 * @see ModelAndView
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Object o, Exception e) {
		// 异常透传到filter统一处理
		return null;
	}

}
