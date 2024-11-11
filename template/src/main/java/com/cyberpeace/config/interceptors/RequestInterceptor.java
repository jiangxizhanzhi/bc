package com.cyberpeace.config.interceptors;

import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 请求拦截器，用于记录请求相关信息
 */
@Slf4j
@Component
public class RequestInterceptor implements HandlerInterceptor {

    // 定义日志记录器
	private static final Logger logger = LoggerFactory.getLogger("audit");

    // 定义请求日志格式
	private static final String REQUEST_LOG_FORMATTER = "bc_request_info|{}|{}|{}|{}";

    // 定义请求参数日志格式
	private static final String REQUEST_PARAM_LOG_FORMATTER = "bc_request_param|{}";

    /**
     * 在请求处理之前进行拦截
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理请求的对象
     * @return true 放行请求，false 中断请求
     * @throws Exception 抛出异常
     */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {
		// 记录请求开始时间
		request.setAttribute("startTime", System.currentTimeMillis());
		return true;
	}

    /**
     * 请求处理完成后进行拦截
     *
     * @param request  请求对象
     * @param response 响应对象
     * @param handler  处理请求的对象
     * @param ex       异常信息
     * @throws Exception 抛出异常
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

