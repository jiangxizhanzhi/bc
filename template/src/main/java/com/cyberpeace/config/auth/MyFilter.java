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
 * @date 2024/11/19
 * @see Filter
 * @since 1.0.0
 */
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

	/**
	 * 获取ip地址地址
	 *
	 * @param servletRequest Servlet 请求
	 * @return {@code String }
	 * @throws
	 * @see ServletRequest
	 * @see String
	 */
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
