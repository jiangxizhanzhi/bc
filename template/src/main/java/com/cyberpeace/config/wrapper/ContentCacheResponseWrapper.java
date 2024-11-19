package com.cyberpeace.config.wrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * 内容缓存响应包装器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/05/21
 * @see ServletResponseWrapper
 * @since 1.0.0
 */
public class ContentCacheResponseWrapper extends ServletResponseWrapper {

	/**
	 * 缓冲区
	 *
	 * @see ByteArrayOutputStream
	 */
	private ByteArrayOutputStream buffer = null;

	/**
	 * 外
	 *
	 * @see ServletOutputStream
	 */
	private ServletOutputStream out = null;

	/**
	 * 作家
	 *
	 * @see PrintWriter
	 */
	private PrintWriter writer = null;

	/**
	 * 内容缓存响应包装器
	 *
	 * @param response 响应
	 * @throws @throws IOException io异常
	 * @see HttpServletResponse
	 */
	public ContentCacheResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		buffer = new ByteArrayOutputStream();
		out = new WrapperOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, StandardCharsets.UTF_8));
	}

	/**
	 * 获取输出流
	 *
	 * @return {@code ServletOutputStream }
	 * @throws @throws IOException io异常
	 * @see ServletOutputStream
	 */
	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	/**
	 * 获取作家
	 *
	 * @return {@code PrintWriter }
	 * @throws @throws IOException io异常
	 * @see PrintWriter
	 */
	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	/**
	 * 冲洗缓冲液
	 *
	 * @throws @throws IOException io异常
	 */
	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	/**
	 * 重置
	 *
	 * @throws
	 */
	@Override
	public void reset() {
		buffer.reset();
	}

	/**
	 * 获取响应数据
	 *
	 * @param charset 字符集
	 * @return {@code String }
	 * @throws @throws IOException io异常
	 * @see String
	 * @see String
	 */
	public String getResponseData(String charset) throws IOException {
		// 将out、writer中的数据强制输出到WrapperResponse的buffer里面，否则取不到数据
		flushBuffer();
		try {
			return buffer.toString("UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}

	}

	private static class WrapperOutputStream extends ServletOutputStream {

		private ByteArrayOutputStream bos = null;

		public WrapperOutputStream(ByteArrayOutputStream stream) throws IOException {
			bos = stream;
		}

		// 将指定字节写入输出流bos
		@Override
		public void write(int b) throws IOException {
			bos.write(b);
		}

		public boolean isReady() {
			return false;
		}
	}
}
