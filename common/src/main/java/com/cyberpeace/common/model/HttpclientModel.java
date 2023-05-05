package com.cyberpeace.common.model;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.cyberpeace.common.util.IDGeneratorUtil;

import lombok.Data;

@Data
public class HttpclientModel {

	private static final Logger LOG = LoggerFactory.getLogger("mp");

	private static final String LOG_FORMATTER = "httpclientutil |{}|{}|{}|{}";

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

	private Integer responseLogMaxLength = 2000;

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
	}

	public void addLog(HttpUriRequest httpRequest, String httpResponse, Boolean verbose) {
		callTime = System.currentTimeMillis() - startTime;
		// debug的时候才输出接口返回， 正常情况下输出调用时间，结果可以忽略
		if (LOG.isDebugEnabled() || verbose) {
			String request = null;
			try {
				request = httpRequest.getMethod().equals("POST")
						? EntityUtils.toString(((HttpPost) httpRequest).getEntity())
						: null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			LOG.info(LOG_FORMATTER, new Object[] { callTime, url, request, httpResponse });
		} else if (LOG.isInfoEnabled()) {
			LOG.info(LOG_FORMATTER, new Object[] { callTime, url, null, "success" });
		}
	}
}
