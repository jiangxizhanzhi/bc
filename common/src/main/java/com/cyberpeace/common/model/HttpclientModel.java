/**
 * 这段代码定义了一个名为 `HttpclientModel` 的 Java 类，用于记录和管理 HTTP 客户端请求的相关信息。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **日志记录**：通过 `slf4j` 提供的日志记录功能，记录 HTTP 请求的详细信息，包括调用时间、方法、URL 和响应内容。 
 * 2. **跟踪 ID 管理**：使用 `traceId` 来标识请求的唯一性，并通过工具类 `IDGeneratorUtil` 自动生成跟踪 ID。 
 * 3. **异常处理**：在发生异常时记录详细的调用时间和错误信息。 
 * 4. **响应日志**：根据日志级别（debug 或 info）记录不同的日志内容。 
 * ### 关键点分析： 
 * - **字段**： 
 * - `traceId`：用于唯一标识每次请求。 
 * - `startTime` 和 `callTime`：分别记录请求开始时间和总耗时。 
 * - `responseLogMaxLength`：限制响应日志的最大长度，默认为 2000。 
 * - `url` 和 `method`：分别记录请求的 URL 和 HTTP 方法。 
 * - **构造函数**： 
 * - 自动生成或接受外部传入的 `traceId`。 
 * - 初始化 `startTime` 和 `url`。 
 * - **方法**： 
 * - `doRemoteException(Exception e)`：记录异常日志，包括调用时间和异常信息。 
 * - `addLog(String httpResponse)`：根据日志级别记录请求的响应日志或成功信息。 
 * ### 总结： 
 * 该类是一个典型的日志记录模型，适用于需要详细监控 HTTP 请求的场景。它结合了日志记录、异常处理和性能监控的功能，适合在分布式系统中作为请求上下文管理的一部分。 
**/
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
 * @date 2024/11/08
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
	 * 网址
	 */
	private String url;

	/**
	 * 方法
	 */
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
