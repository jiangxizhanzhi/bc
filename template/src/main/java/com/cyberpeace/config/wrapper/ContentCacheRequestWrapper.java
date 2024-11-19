package com.cyberpeace.config.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.compress.utils.IOUtils;

/**
 * 内容缓存请求包装器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see HttpServletRequestWrapper
 * @since 1.0.0
 */
public class ContentCacheRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 请求体
	 *
	 * @see byte[]
	 */
	private byte[] body;

	/**
	 * 读取器
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
	 * @return {@code byte[] }
	 * @throws
	 * @see byte[]
	 */
	public byte[] getBody() {
		return body;
	}

	/**
	 * 获取输入流
	 *
	 * @return {@code ServletInputStream }
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
	 * @return {@code BufferedReader }
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
