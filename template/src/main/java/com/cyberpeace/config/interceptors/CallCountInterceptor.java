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
 * @date 2024/11/12
 */
@Component
@Slf4j
public class CallCountInterceptor implements HandlerInterceptor {

	/**
	 * 调用计数Service
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
	 * @throws Exception 例外
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object handler) throws Exception {

		if (handler instanceof HandlerMethod) {
			Method method = ((HandlerMethod) handler).getMethod();
			CallCount callCount = method.getAnnotation(CallCount.class);
			if (null != callCount) {
				String name = callCount.name();
				service.addCount(name);
			}
		}
		return true;
	}
}
