package com.cyberpeace.config.auth;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.impl.TraceIdContext;

import com.cyberpeace.common.constant.WebConstant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 1、设置traceId
		setTraceId(request);

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	private void setTraceId(ServletRequest request) {
		String traceId = ((HttpServletRequest) request).getHeader(WebConstant.TRACE_HEADER);

		if (StringUtils.isBlank(traceId)) {
			traceId = UUID.randomUUID().toString();
		}

		TraceIdContext.setTraceId(traceId);
	}

}
