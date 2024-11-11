package com.cyberpeace.config.auth;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.compress.utils.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

/**
 * 防抖
 * @author SN
 */
@Slf4j
public class DebounceFilter implements Filter {

	// 创建一个缓存对象，用于存储字符串键值对
	// 设置缓存项在访问后3秒过期，以防止缓存数据长时间未使用导致内存泄漏
	Cache<String, String> cache = CacheBuilder.newBuilder().expireAfterAccess(3, TimeUnit.SECONDS)
	        .build();

	// 定义常量POST，用于标识HTTP的POST方法
	private final String post = "POST";

	// 定义常量jsonType，表示JSON类型的 MIME 类型
	private final String jsonType = "application/json";

	// 初始化过滤器的方法，留空实现以备后续具体实现
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	    // 目前没有初始化工作需要完成，但未来可能需要使用filterConfig进行一些配置
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
	                     FilterChain filterChain) throws IOException, ServletException {
	    // 将ServletRequest转换为HttpServletRequest，以便获取请求方法和内容类型
	    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
	    // 只处理 body 是 json 的 post 请求
	    if (httpServletRequest.getMethod().equals(post)
	            && httpServletRequest.getContentType() != null
	            && httpServletRequest.getContentType().contains(jsonType)) {

	        // 使用ContentCacheRequestWrapper包装请求，以便缓存请求体
	        ContentCacheRequestWrapper requestWrapper = new ContentCacheRequestWrapper(
	                (HttpServletRequest) servletRequest);
	        byte[] body = requestWrapper.getBody();

	        // 防抖不限制不需要认证的接口
	        String token = httpServletRequest.getHeader("Authorization");
	        if (token != null) {
	            String requestUri = requestWrapper.getRequestURI();

	            // 用token+path+body识别请求是否重复，多个用户启动同一个场景的情况由数据库唯一索引来避免
	            String key = getSha256(body) + "_" + requestUri + "_" + token;
	            // 将生成的key存入本地缓存，用于识别和限制重复请求
	            localCache(key);
	        }
	        // 继续过滤链，处理请求
	        filterChain.doFilter(requestWrapper, servletResponse);
	    } else {
	        // 对于不符合条件的请求，直接继续过滤链，不做额外处理
	        filterChain.doFilter(servletRequest, servletResponse);
	    }
	}

	/**
	 * 使用本地缓存机制来限制操作频率
	 * 如果给定的键已经存在于缓存中，则抛出异常表明操作过于频繁
	 * 否则，将键添加到缓存中以记录操作
	 *
	 * @param key 操作的唯一标识符，用于缓存中的查找和存储
	 * @throws BusinessException 当操作过于频繁时抛出此异常，使用特定的错误码
	 */
	public synchronized void localCache(String key){
	    // 检查缓存中是否存在给定的键，如果存在则抛出异常
	    if (cache.getIfPresent(key) != null) {
	        throw new BusinessException(ErrorCode.OPERATION_TOO_FREQUENT);
	    }
	    // 如果缓存中不存在该键，则将其添加到缓存中
	    cache.put(key, "whatever");
	}

	/**
	 * 用于销毁缓存实例的方法
	 * 当前实现为空，但提供了未来可能需要的清理机制的占位符
	 */
	@Override
	public void destroy() {
	    // 当前不执行任何操作，但可在此处添加清理缓存等逻辑
	}

	/**
	 * 计算给定字节数组的SHA-256哈希值
	 *
	 * @param body 要计算哈希值的字节数组
	 * @return 字节数组的SHA-256哈希值的十六进制字符串表示
	 * @throws BusinessException 如果找不到SHA256算法，则抛出业务异常
	 */
	private String getSha256(byte[] body) {
	    try {
	        // 获取SHA256算法的消息摘要实例
	        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
	        // 使用给定的字节数组更新消息摘要
	        messageDigest.update(body);
	        // 计算消息摘要的哈希值，并返回其十六进制字符串表示
	        return Hex.encodeHexString(messageDigest.digest(), true);
	    } catch (NoSuchAlgorithmException e) {
	        // 如果找不到SHA256算法，打印异常堆栈跟踪并抛出业务异常
	        e.printStackTrace();
	        throw new BusinessException(ErrorCode.INTERNAL_ERROR);
	    }
	}
}

/**
 * ContentCacheRequestWrapper类继承自HttpServletRequestWrapper，用于缓存HTTP请求的内容
 * 它通过保存请求体的字节流来避免多次读取请求体时出现的问题
 */
class ContentCacheRequestWrapper extends HttpServletRequestWrapper {

	// 缓存的请求体字节数组
	private byte[] body;

	// 用于读取请求体的BufferedReader
	private BufferedReader reader;

	// 自定义的ServletInputStream实现，用于多次读取请求体
	private ServletInputStream inputStream;

	/**
	 * 构造方法，初始化ContentCacheRequestWrapper实例
	 *
	 * @param request 被包装的HttpServletRequest对象
	 * @throws IOException 如果在加载请求体时发生I/O错误
	 */
	public ContentCacheRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		loadBody(request);
	}

	/**
	 * 加载请求体到byte数组，并初始化自定义的ServletInputStream
	 *
	 * @param request 被包装的HttpServletRequest对象
	 * @throws IOException 如果在读取请求体时发生I/O错误
	 */
	private void loadBody(HttpServletRequest request) throws IOException {
		body = IOUtils.toByteArray(request.getInputStream());
		inputStream = new RequestCachingInputStream(body);
	}

	/**
	 * 获取缓存的请求体字节数组
	 *
	 * @return 请求体的字节数组
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * 重写getInputStream方法，返回自定义的ServletInputStream
	 * 这允许请求体被多次读取
	 *
	 * @return 自定义的ServletInputStream实例
	 * @throws IOException 如果在获取输入流时发生I/O错误
	 */
	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (inputStream != null) {
			return inputStream;
		}
		return super.getInputStream();
	}

	/**
	 * 重写getReader方法，基于inputStream提供一个BufferedReader
	 * 这允许以字符流的形式读取请求体
	 *
	 * @return BufferedReader实例，用于读取请求体
	 * @throws IOException 如果在获取字符流时发生I/O错误
	 */
	@Override
	public BufferedReader getReader() throws IOException {
		if (reader == null) {
			reader = new BufferedReader(new InputStreamReader(inputStream, getCharacterEncoding()));
		}
		return reader;
	}

	/**
	 * RequestCachingInputStream类是ServletInputStream的一个私有静态实现
	 * 它基于缓存的请求体字节数组，允许请求体被多次读取
	 */
	private static class RequestCachingInputStream extends ServletInputStream {

		// 使用ByteArrayInputStream包装缓存的请求体字节数组
		private final ByteArrayInputStream inputStream;

		/**
		 * 构造方法，初始化RequestCachingInputStream实例
		 *
		 * @param bytes 缓存的请求体字节数组
		 */
		public RequestCachingInputStream(byte[] bytes) {
			inputStream = new ByteArrayInputStream(bytes);
		}

		/**
		 * 重写read方法，从缓存的字节数组中读取下一个字节
		 *
		 * @return 下一个字节，或者在流结束时返回-1
		 * @throws IOException 如果在读取时发生I/O错误
		 */
		@Override
		public int read() throws IOException {
			return inputStream.read();
		}
	}
}
