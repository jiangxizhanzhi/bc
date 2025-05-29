package com.cyberpeace.config.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.impl.TraceIdContext;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;

import lombok.extern.slf4j.Slf4j;

/**
 * 我的过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Filter
 * @since 1.0.0
 */
/**
 * ### 分析与总结 
 * #### 代码功能概述： 
 * 该代码实现了一个自定义的 `Filter` 类 `MyFilter`，用于在 HTTP 请求处理过程中添加或获取跟踪信息（如 `traceId` 和 IP 地址），并记录请求耗时。此外，它还根据请求头中的语言信息设置默认语言。 
 * --- 
 * #### 主要功能模块： 
 * 1. **`init` 方法**： 
 * - 重写了 `Filter` 接口的 `init` 方法，但未实现任何逻辑。 
 * - 通常用于初始化过滤器的配置参数。 
 * 2. **`doFilter` 方法**： 
 * - 核心方法，负责拦截请求并执行以下操作： 
 * - 调用 `setTraceId` 方法为当前请求生成或获取唯一的 `traceId`。 
 * - 获取请求路径 (`url`) 并记录请求开始时间。 
 * - 调用 `chain.doFilter` 将请求传递给下一个过滤器或目标资源。 
 * - 计算请求耗时并打印日志。 
 * 3. **`destroy` 方法**： 
 * - 重写了 `Filter` 接口的 `destroy` 方法，但未实现任何逻辑。 
 * - 通常用于释放过滤器占用的资源。 
 * 4. **`setTraceId` 方法**： 
 * - 根据请求头中的 `traceId` 和 `language` 字段设置全局上下文信息： 
 * - 如果请求头中没有提供 `traceId`，则生成一个随机的 UUID。 
 * - 如果语言字段不是英文，则默认设置为中文。 
 * - 将 `traceId` 和语言信息存储到 `TraceIdContext` 和 `CommonContext` 中。 
 * - 调用 `getIpAddr` 方法获取客户端 IP 地址，并将其存储到 `CommonContext` 中。 
 * 5. **`getIpAddr` 方法**： 
 * - 根据 HTTP 请求头中的多个字段（如 `x-forwarded-for`、`Proxy-Client-IP`、`WL-Proxy-Client-IP`）提取客户端的真实 IP 地址。 
 * - 如果无法从头信息中获取 IP，则返回默认值 `127.0.0.1`。 
 * --- 
 * #### 使用的关键技术与工具： 
 * 1. **Lombok 注解**： 
 * - 使用了 `@Slf4j` 注解，简化了日志记录的实现。 
 * 2. **Apache Commons Lang**： 
 * - 使用了 `StringUtils.isBlank` 方法来判断字符串是否为空或空白。 
 * 3. **UUID**： 
 * - 在没有提供 `traceId` 时，生成一个随机的 UUID。 
 * 4. **ThreadLocal**： 
 * - 使用了 `TraceIdContext` 和 `CommonContext` 来存储线程级别的上下文信息。 
 * 5. **日志记录**： 
 * - 使用 SLF4J 记录请求路径及耗时。 
 * --- 
 * #### 优点： 
 * 1. **模块化设计**：每个方法职责清晰，便于维护和扩展。 
 * 2. **灵活的上下文管理**：通过 `TraceIdContext` 和 `CommonContext` 实现线程安全的上下文存储。 
 * 3. **支持分布式追踪**：通过 `traceId` 提供了简单的分布式系统追踪能力。 
 * --- 
 * #### 缺点： 
 * 1. **缺少异常处理**：`doFilter` 方法中未对可能的异常进行详细的捕获和处理。 
 * 2. **默认 IP 地址**：当所有请求头字段均无效时，默认返回 `127.0.0.1`，可能不符合实际需求。 
 * 3. **硬编码语言默认值**：语言默认值 (`WebConstant.LANGUAGE_ZH`) 是硬编码的，扩展性较差。 
 * --- 
 * #### 总结： 
 * 该代码实现了基本的请求拦截和上下文管理功能，适用于需要记录请求耗时、分配唯一标识符以及管理语言和 IP 的场景。如果需要更复杂的逻辑（如异常处理、动态语言支持等），可以在现有基础上进一步完善。 
**/
@Slf4j
public class MyFilter implements Filter {

	/**
	 * 初始化
	 *
	 * @param filterConfig 过滤器配置
	 * @throws @throws ServletException Servlet 异常
	 * @see FilterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	/**
	 * 做过滤器
	 *
	 * @param request  请求
	 * @param response 响应
	 * @param chain    链
	 * @throws @throws          IOException io异常
	 * @throws ServletException Servlet 异常
	 * @see ServletRequest
	 * @see ServletResponse
	 * @see FilterChain
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1、设置traceId
		setTraceId(request);

		long startTime = System.currentTimeMillis();
		String url = ((HttpServletRequest) request).getServletPath();
		chain.doFilter(request, response);
		log.debug("{}|{}", url, (System.currentTimeMillis() - startTime));
	}

	/**
	 * 摧毁
	 *
	 * @throws
	 */
	@Override
	public void destroy() {
		// 什么都不需要做
	}

	/**
	 * 设置跟踪id
	 *
	 * @param request 请求
	 * @throws
	 * @see ServletRequest
	 */
	private void setTraceId(ServletRequest request) {
		String traceId = ((HttpServletRequest) request).getHeader(WebConstant.TRACE_HEADER);

		String language = ((HttpServletRequest) request).getHeader(WebConstant.LANGUAGE);

		if (StringUtils.isBlank(traceId)) {
			traceId = UUID.randomUUID().toString();
		}

		// 不显式指定英文语言，就默认使用中文
		if (!StringUtils.equals(language, WebConstant.LANGUAGE_EN)) {
			language = WebConstant.LANGUAGE_ZH;
		}

		CommonContext.setLanguage(language);
		CommonContext.setIp(getIpAddr(request));

		TraceIdContext.setTraceId(traceId);
	}

	public String getIpAddr(ServletRequest servletRequest) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;

		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		if (StringUtils.isBlank(ip)) {
			ip = "127.0.0.1";
		}
		return ip;
	}

}

