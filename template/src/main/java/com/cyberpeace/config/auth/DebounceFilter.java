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

import lombok.Synchronized;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.compress.utils.IOUtils;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 防抖过滤器
 *
 *
 * @author SN
 * @date 2023/05/06
 */
@Slf4j
public class DebounceFilter implements Filter {

	Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(100, TimeUnit.MILLISECONDS)
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

			// 保持原有顺序解析
			// String bodyStr = new String(body);
			// JSONObject obj = JSONObject.parseObject(s, Feature.OrderedField);
			// String c = JSONObject.toJSONString(obj);

			String requestUri = requestWrapper.getRequestURI();

			// 用 ip 做用户识别不能避免两个用户启动同一个场景 CommonContext.getIp()
			String key = getSha256(body) + "_" + requestUri;
			//localCache(key);
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
 * 内容缓存请求包装
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
class ContentCacheRequestWrapper extends HttpServletRequestWrapper {

	private byte[] body;

	private BufferedReader reader;

	private ServletInputStream inputStream;

	public ContentCacheRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		loadBody(request);
	}

	private void loadBody(HttpServletRequest request) throws IOException {
		body = IOUtils.toByteArray(request.getInputStream());
		inputStream = new RequestCachingInputStream(body);
	}

	public byte[] getBody() {
		return body;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (inputStream != null) {
			return inputStream;
		}
		return super.getInputStream();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
		}
		return reader;
	}

	private static class RequestCachingInputStream extends ServletInputStream {

		private final ByteArrayInputStream inputStream;

		public RequestCachingInputStream(byte[] bytes) {
			inputStream = new ByteArrayInputStream(bytes);
		}

		@Override
		public int read() throws IOException {
			return inputStream.read();
		}
	}
}