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
 *
 * @author SN@see Filter
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
public class DebounceFilter implements Filter {

	/**
	 * 缓存
	 *
	 * @see Cache<String, String>
	 */
	Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
			.build();

	/**
	 * 初始化
	 *
	 * @param filterConfig 过滤器配置
	 * @throws @throws ServletException Servlet 异常
	 * @see FilterConfig
	 */
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	/**
	 * 做过滤器
	 *
	 * @param servletRequest  Servlet 请求
	 * @param servletResponse Servlet 响应
	 * @param filterChain     过滤器链
	 * @throws @throws          IOException io异常
	 * @throws ServletException Servlet 异常
	 * @see ServletRequest
	 * @see ServletResponse
	 * @see FilterChain
	 */
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

	/**
	 * 本地缓存
	 *
	 * @param key 钥匙
	 * @throws
	 * @see String
	 */
	public synchronized void localCache(String key){
		if (cache.getIfPresent(key) != null) {
			throw new BusinessException(ErrorCode.OPERATION_TOO_FREQUENT);
		}
		cache.put(key, "whatever");
	}

	/**
	 * 摧毁
	 *
	 * @throws
	 */
	@Override
	public void destroy() {

	}

	/**
	 * 获取SHA256
	 *
	 * @param body 身体
	 * @return {@code String }
	 * @throws
	 * @see byte[]
	 * @see String
	 */
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