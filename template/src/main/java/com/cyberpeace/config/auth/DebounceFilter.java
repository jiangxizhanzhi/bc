package com.cyberpeace.config.auth;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.compress.utils.IOUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 防抖
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Filter
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 概述 
 * 该代码实现了一个防抖（Debounce）过滤器 `DebounceFilter`，用于防止短时间内重复提交相同内容的 HTTP 请求。它通过检查请求方法、请求头、请求体（body）以及 URL 来判断请求是否需要防抖处理。 
 * #### 主要功能模块 
 * 1. **防抖逻辑** 
 * - 使用 Guava 的 `Cache` 实现一个本地缓存，用于存储最近的请求。 
 * - 缓存的过期时间为 3 秒钟。 
 * - 如果请求的 `key` 已存在于缓存中，则抛出业务异常 `BusinessException`，提示操作过于频繁。 
 * 2. **请求包装** 
 * - 定义了内部类 `ContentCacheRequestWrapper`，继承自 `HttpServletRequestWrapper`，用于缓存和重写请求的 `body`。 
 * - 在构造函数中，通过 `IOUtils.toByteArray()` 将原始请求的输入流转换为字节数组，并保存到 `body` 中。 
 * - 提供了 `getBody()` 方法返回请求体的字节数组。 
 * - 重写了 `getInputStream()` 和 `getReader()` 方法，确保后续的请求处理可以正确读取缓存的请求体。 
 * 3. **请求特征提取** 
 * - 使用 SHA-256 算法生成请求体的哈希值。 
 * - 构造唯一的 `key`，结合请求路径、请求体和授权令牌（`Authorization`），以确保请求的唯一性。 
 * 4. **过滤器逻辑** 
 * - 判断请求是否为 `POST` 方法且内容类型为 `application/json`。 
 * - 如果满足条件，则使用 `ContentCacheRequestWrapper` 包装请求并计算防抖 `key`。 
 * - 调用 `localCache()` 方法进行防抖检查。 
 * - 如果不满足条件，则直接放行请求。 
 * #### 关键点总结 
 * - **防抖机制**：通过缓存请求特征（如请求体哈希值、路径和令牌）来检测重复请求。 
 * - **请求体缓存**：通过 `ContentCacheRequestWrapper` 解决了多次读取请求体的问题。 
 * - **线程安全**：`localCache()` 方法使用了 `synchronized` 关键字保证多线程环境下的安全性。 
 * - **异常处理**：在生成 SHA-256 哈希值时捕获异常并抛出自定义业务异常。 
 * #### 优点 
 * - 实现了高效的防抖功能，适用于高并发场景。 
 * - 使用了成熟的工具类（如 Guava 的缓存和 Apache Commons 的 IO 工具）简化开发。 
 * - 代码结构清晰，逻辑分层明确。 
 * #### 改进建议 
 * - **缓存清理**：当前缓存仅依赖于时间自动清除，可以考虑增加基于访问频率的淘汰策略。 
 * - **日志记录**：在抛出业务异常时添加详细的日志记录，便于问题排查。 
 * - **性能优化**：如果请求体较大，可以考虑异步处理或批量缓存以提高性能。 
 * #### 总结 
 * 该代码实现了基本的防抖功能，适合用于需要限制重复提交的 API 场景。通过合理的缓存设计和请求包装，有效解决了重复请求的问题，同时保持了代码的可维护性和扩展性。 
**/
@Slf4j
public class DebounceFilter implements Filter {

	Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
			.build();

	private final String post = "POST";

	private final String jsonType = "application/json";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		// 只处理 body 是 json 的 post 请求
		if (httpServletRequest.getMethod().equals(post)
				&& httpServletRequest.getContentType() != null
				&& httpServletRequest.getContentType().contains(jsonType)) {

			ContentCacheRequestWrapper requestWrapper = new ContentCacheRequestWrapper(
					(HttpServletRequest) servletRequest);
			byte[] body = requestWrapper.getBody();

			// 防抖不限制不需要认证的接口
			String token = httpServletRequest.getHeader("Authorization");
			if (token != null) {
				String requestUri = requestWrapper.getRequestURI();

				// 用token+path+body识别请求是否重复，多个用户启动同一个场景的情况由数据库唯一索引来避免
				String key ="_" + requestUri + "_" + token;
				localCache(key);
			}
			filterChain.doFilter(requestWrapper, servletResponse);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}
	public synchronized void localCache(String key){
		if (cache.getIfPresent(key) != null) {
			throw new BusinessException(ErrorCode.OPERATION_TOO_FREQUENT);
		}
		cache.put(key, "whatever");
	}

	@Override
	public void destroy() {

	}

}

/**
 * 内容缓存请求包装器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see HttpServletRequestWrapper
 * @since 1.0.0
 */
class ContentCacheRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 身体
	 *
	 * @see byte[]
	 */
	private byte[] body;

	/**
	 * 读者
	 *
	 * @see BufferedReader
	 */
	private BufferedReader reader;

	/**
	 * 输入流
	 *
	 * @see ServletInputStream
	 */
	private ServletInputStream inputStream;

	/**
	 * 内容缓存请求包装器
	 *
	 * @param request 请求
	 * @throws @throws IOException io异常
	 * @see HttpServletRequest
	 */
	public ContentCacheRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		loadBody(request);
	}

	/**
	 * 加载身体
	 *
	 * @param request 请求
	 * @throws @throws IOException io异常
	 * @see HttpServletRequest
	 */
	private void loadBody(HttpServletRequest request) throws IOException {
		body = IOUtils.toByteArray(request.getInputStream());
		inputStream = new RequestCachingInputStream(body);
	}

	/**
	 * 获取身体
	 *
	 * @return 字节
	 * @throws
	 * @see byte[]
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * 获取输入流
	 *
	 * @return Servlet 输入流
	 * @throws @throws IOException io异常
	 * @see ServletInputStream
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (inputStream != null) {
			return inputStream;
		}
		return super.getInputStream();
	}

	/**
	 * 获取读者
	 *
	 * @return 缓冲读取器
	 * @throws @throws IOException io异常
	 * @see BufferedReader
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
		}
		return reader;
	}

	/**
	 * 请求缓存输入流
	 *
	 * @author SN
	 * @version 1.0.0
	 * @date 2025/05/28
	 * @see ServletInputStream
	 * @since 1.0.0
	 */
	private static class RequestCachingInputStream extends ServletInputStream {

		/**
		 * 输入流
		 *
		 * @see ByteArrayInputStream
		 */
		private final ByteArrayInputStream inputStream;

		/**
		 * 请求缓存输入流
		 *
		 * @param bytes 字节
		 * @throws
		 * @see byte[]
		 */
		public RequestCachingInputStream(byte[] bytes) {
			inputStream = new ByteArrayInputStream(bytes);
		}

		/**
		 * 读
		 *
		 * @return int
		 * @throws @throws IOException io异常
		 * @see int
		 */
		@Override
		public int read() throws IOException {
			return inputStream.read();
		}
	}
}
