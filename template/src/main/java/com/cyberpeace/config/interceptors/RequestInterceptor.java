package com.cyberpeace.config.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cyberpeace.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 请求拦截器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see HandlerInterceptor
 * @since 1.0.0
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

	/**
	 * 记录
	 *
	 * @see Logger
	 */
	private static final Logger logger = LoggerFactory.getLogger("audit");

	/**
	 * 请求日志formatter
	 *
	 * @see String
	 */
	private static final String REQUEST_LOG_FORMATTER = "bc_request_info|{}|{}|{}|{}";

	/**
	 * 请求参数日志formatter
	 *
	 * @see String
	 */
	private static final String REQUEST_PARAM_LOG_FORMATTER = "bc_request_param|{}";

	/**
	 * 前置handle
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  处理器
	 * @return boolean
	 * @throws @throws Exception 例外
	 * @see HttpServletRequest
	 * @see HttpServletResponse
	 * @see Object
	 * @see boolean
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

	/**
	 * 完成后
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param handler  处理器
	 * @param ex       前任
	 * @throws @throws Exception 例外
	 * @see HttpServletRequest
	 * @see HttpServletResponse
	 * @see Object
	 * @see Exception
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object handler, Exception ex) throws Exception {

		// 计算接口响应时间
		long startTime = (long) request.getAttribute("startTime");
		long endTime = System.currentTimeMillis();

		// 记录接口访问日志
		logger.info(REQUEST_LOG_FORMATTER, endTime - startTime, response.getStatus(),
				request.getMethod(), request.getRequestURI());

		// 记录接口调用参数
		logger.debug(REQUEST_PARAM_LOG_FORMATTER, JsonUtil.toJsonString(request.getParameterMap()));
	}

}
