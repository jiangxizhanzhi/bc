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
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码定义了一个Spring MVC拦截器`RequestInterceptor`，用于拦截HTTP请求并记录相关日志信息。其主要功能包括： 
 * 1. 在请求处理前记录开始时间。 
 * 2. 在请求处理完成后计算接口响应时间，并记录请求日志和参数。 
 * --- 
 * #### 关键点分析 
 * 1. **类与注解** 
 * - 类`RequestInterceptor`实现了`HandlerInterceptor`接口，这是Spring MVC中定义拦截器的标准方式。 
 * - 使用了`@Component`注解，将其注册为Spring容器中的一个Bean。 
 * 2. **日志配置** 
 * - 使用了`slf4j`日志框架，日志记录器名为`"audit"`，专门用于审计日志。 
 * - 定义了两个日志格式化字符串： 
 * - `REQUEST_LOG_FORMATTER`：用于记录接口的访问日志（如响应时间、状态码、请求方法、请求路径）。 
 * - `REQUEST_PARAM_LOG_FORMATTER`：用于记录请求参数。 
 * 3. **前置处理 (`preHandle`)** 
 * - 方法`preHandle`在请求被实际处理之前执行。 
 * - 在请求中设置了一个属性`startTime`，记录当前时间戳，用于后续计算接口响应时间。 
 * 4. **后置处理 (`afterCompletion`)** 
 * - 方法`afterCompletion`在请求处理完成后执行。 
 * - 从请求中获取`startTime`，结合当前时间计算接口响应时间。 
 * - 记录接口访问日志，包含响应时间、状态码、请求方法和请求路径。 
 * - 使用`JsonUtil`将请求参数转换为JSON字符串并记录。 
 * 5. **依赖与工具** 
 * - 使用`JsonUtil`工具类将请求参数以JSON格式记录，便于调试和分析。 
 * - 日志级别分别为`INFO`（记录接口访问日志）和`DEBUG`（记录请求参数），可根据需要调整。 
 * --- 
 * #### 总结 
 * 该拦截器的主要作用是通过记录接口访问时间和请求参数，提供详细的请求日志，便于后续性能监控和问题排查。代码结构清晰，逻辑简单明了，符合Spring MVC拦截器的设计规范。 
**/
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

