package com.cyberpeace.config.auth;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Hex;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.config.wrapper.ContentCacheRequestWrapper;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

/**
 * 防抖
 * @author SN
 */
@Slf4j
public class DebounceFilter implements Filter {

	Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
			.build();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		// 只处理 body 是 json 的 post 请求
		if (httpServletRequest.getMethod().equals(WebConstant.POST)
				&& httpServletRequest.getContentType() != null
				&& httpServletRequest.getContentType().contains(WebConstant.JSON_TYPE)) {

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