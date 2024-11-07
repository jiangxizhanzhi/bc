package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.cyberpeace.common.util.IDGeneratorUtil;

import lombok.Data;

/**
 * httpclient 模型
 *
 * @author SN
 * @date 2024/11/07
 */
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

	private String method;

	/**
	 * httpclient 模型
	 *
	 * @param traceId 跟踪id
	 * @param url     网址
	 */
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

	/**
	 * 执行远程异常
	 *
	 * @param e e
	 */
	public void doRemoteException(Exception e) {
		callTime = System.currentTimeMillis() - startTime;
		LOG.error(LOG_FORMATTER, new Object[] { callTime, method, url, null }, e);
	}

	/**
	 * 新增日志
	 *
	 * @param httpResponse HTTP 响应
	 */
	public void addLog(String httpResponse) {
		callTime = System.currentTimeMillis() - startTime;
		// debug的时候才输出接口返回， 正常情况下输出调用时间，结果可以忽略
		if (LOG.isDebugEnabled()) {
			LOG.debug(LOG_FORMATTER, new Object[] { callTime, method, url, httpResponse });
		}
		if (LOG.isInfoEnabled()) {
			LOG.info(LOG_FORMATTER, new Object[] { callTime, method, url, "success" });
		}
	}
}
