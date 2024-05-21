package com.cyberpeace.config.wrapper;

import java.io.*;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SN
 * @date 2024/05/21
 */
public class ContentCacheResponseWrapper extends ServletResponseWrapper {

	private ByteArrayOutputStream buffer = null;

	private ServletOutputStream out = null;

	private PrintWriter writer = null;

	public ContentCacheResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		buffer = new ByteArrayOutputStream();
		out = new WrapperOutputStream(buffer);
		writer = new PrintWriter(new OutputStreamWriter(buffer, StandardCharsets.UTF_8));
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return out;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return writer;
	}

	@Override
	public void flushBuffer() throws IOException {
		if (out != null) {
			out.flush();
		}
		if (writer != null) {
			writer.flush();
		}
	}

	@Override
	public void reset() {
		buffer.reset();
	}

	/**
	 * @Description: 获取response中数据的方法
	 **/
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
