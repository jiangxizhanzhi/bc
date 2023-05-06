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

/**
 * httpclient模型
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class HttpclientModel {

	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger("mp");

	/**
	 * 日志格式化程序
	 */
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

	/**
	 * 响应日志最大长度
	 */
	private Integer responseLogMaxLength = 2000;

	/**
	 * url
	 */
	private String url;

	/**
	 * httpclient模型
	 *
	 * @param traceId 跟踪id
	 * @param url     url
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
	 * 做远程异常
	 *
	 * @param e e
	 */
	public void doRemoteException(Exception e) {
		callTime = System.currentTimeMillis() - startTime;
		LOG.error(LOG_FORMATTER, new Object[] { callTime, url, null }, e);
	}

	/**
	 * 添加日志
	 *
	 * @param httpRequest  http请求
	 * @param httpResponse http响应
	 * @param verbose      详细
	 */
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
