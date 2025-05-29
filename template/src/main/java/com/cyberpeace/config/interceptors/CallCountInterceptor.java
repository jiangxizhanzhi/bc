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
/**
 * 该代码定义了一个名为 `CallCountInterceptor` 的 Spring 拦截器类，用于记录带有特定注解的方法的调用次数。以下是代码的主要功能和结构分析： 
 * ### 功能分析： 
 * 1. **注解识别**： 
 * - 通过 `@CallCount` 注解标记需要统计调用次数的方法。 
 * - 拦截器会在请求到达这些方法时，自动记录其调用次数。 
 * 2. **核心逻辑**： 
 * - 在拦截器的 `preHandle` 方法中，判断当前处理器是否为 `HandlerMethod` 类型。 
 * - 如果是，则获取目标方法，并检查该方法是否被 `@CallCount` 注解标记。 
 * - 若标记了 `@CallCount`，则从注解中提取名称参数，并调用 `SpaceCallCountService` 的 `addCount` 方法记录调用次数。 
 * 3. **依赖注入**： 
 * - 使用 `@Autowired` 注入了 `SpaceCallCountService`，用于实际的调用计数操作。 
 * 4. **日志记录**： 
 * - 使用 Lombok 的 `@Slf4j` 注解添加日志功能，方便调试和监控。 
 * ### 总结： 
 * - 该拦截器实现了基于注解的调用计数功能，适用于需要动态统计方法调用频率的场景。 
 * - 它通过 Spring 的 AOP（面向切面编程）机制，在不修改业务代码的情况下实现功能增强。 
 * - 结构清晰，职责单一，符合单一职责原则。 
**/
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

