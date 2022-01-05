package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.IDGeneratorUtil;

import lombok.Data;

@Data
public class HttpclientModel {

	private static final Logger LOG = LoggerFactory.getLogger("mp");

	private static final String LOG_FORMATTER = "httpclientutil |{}|{}|{}";

	/**
	 * 发送给对端系统的跟踪路由
	 */
	private String traceId;

	/**
	 * 调用开始时间
	 */
	private long startTime;

	/**
	 * 调用耗时
	 */
	private long callTime;

	private String url;

	public HttpclientModel(String traceId, String url) {
		if (StringUtils.isBlank(traceId)) {
			this.traceId = IDGeneratorUtil.getID();
			TraceIdContext.setTraceId(this.traceId);
		} else {
			this.traceId = traceId;
		}

		this.startTime = System.currentTimeMillis();
		this.url = url;
	}

	public void doRemoteException(Exception e) {
		callTime = System.currentTimeMillis() - startTime;
		LOG.error(LOG_FORMATTER, new Object[] { callTime, url, null }, e);

		throw new RemoteServiceException();
	}

	public void addLog(String httpResponse) {
		callTime = System.currentTimeMillis() - startTime;
		LOG.info(LOG_FORMATTER, new Object[] { callTime, url, httpResponse });
	}
}
