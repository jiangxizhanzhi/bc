/**
 * 这段代码定义了一个自定义的异常处理器类 `CustomExceptionHandlerResolver`，实现了 Spring 框架中的 `HandlerExceptionResolver` 接口。以下是代码的简要分析： 
 * ### 功能概述 
 * - **作用**：该类用于捕获和处理在 Spring MVC 控制器中抛出的异常。 
 * - **核心方法**：重写了 `resolveException` 方法，用于接收请求、响应、目标对象以及抛出的异常，并返回一个 `ModelAndView` 对象以决定如何处理异常。 
 * ### 方法分析 
 * - **方法签名**： 
 * ```java 
 * public ModelAndView resolveException(HttpServletRequest httpServletRequest, 
 * HttpServletResponse httpServletResponse, 
 * Object o, 
 * Exception e) 
 * ``` 
 * - 参数说明： 
 * - `httpServletRequest` 和 `httpServletResponse`：分别表示当前的 HTTP 请求和响应对象。 
 * - `o`：表示触发异常的目标对象（通常是控制器方法）。 
 * - `e`：表示实际抛出的异常。 
 * - 返回值：`ModelAndView` 对象，用于指定异常处理后的视图或模型数据。 
 * - **实现细节**： 
 * - 当前实现直接返回 `null`，表明异常未被此处理器捕获或处理，而是继续传递给其他异常处理器或默认处理机制。 
 * - 注释提到“异常透传到 filter 统一处理”，意味着异常不会在此类中进行具体处理，而是交由过滤器层处理。 
 * ### 总结 
 * - **优点**：代码结构清晰，符合 Spring 的设计规范。 
 * - **缺点**：当前实现未提供任何具体的异常处理逻辑，仅作为占位符存在。若需要定制化异常处理，需在 `resolveException` 方法中添加具体业务逻辑。 
 * - **适用场景**：适用于需要将异常透传至过滤器或其他全局异常处理机制的场景。 
**/
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
