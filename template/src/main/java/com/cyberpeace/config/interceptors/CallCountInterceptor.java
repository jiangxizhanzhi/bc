package com.cyberpeace.config.interceptors;

import com.cyberpeace.common.annoations.CallCount;
import com.cyberpeace.space.service.SpaceCallCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * 调用计数拦截器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/12
 * @see HandlerInterceptor
 * @since 1.0.0
 */
@Component
@Slf4j
public class CallCountInterceptor implements HandlerInterceptor {

	/**
	 * 调用计数Service
	 *
	 * @see SpaceCallCountService
	 */
	@Autowired
	private SpaceCallCountService service;

	/**
	 * 预处理
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

		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			CallCount callCount = method.getAnnotation(CallCount.class);
			if (null != callCount) {
				String name = callCount.value();
				service.addCount(name);
			}
		}
		return true;
	}
}
