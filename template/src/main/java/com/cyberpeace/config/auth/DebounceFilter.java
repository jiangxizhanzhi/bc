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
				String key = getSha256(body) + "_" + requestUri + "_" + token;
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

	private String getSha256(byte[] body) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			messageDigest.update(body);
			return Hex.encodeHexString(messageDigest.digest(), true);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new BusinessException(ErrorCode.INTERNAL_ERROR);
		}
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