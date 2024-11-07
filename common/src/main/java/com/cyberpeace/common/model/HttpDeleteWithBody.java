package com.cyberpeace.common.model;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * 带正文 HTTP 删除
 *
 * @author SN
 * @date 2024/11/07
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

	/**
	 * 方法名称
	 */
	public static final String METHOD_NAME = "DELETE";

	/**
	 * 获取方法
	 *
	 * @return {@code String }
	 */
	@Override
	public String getMethod() {
		return METHOD_NAME;
	}

	/**
	 * 带正文 HTTP 删除
	 *
	 * @param uri URI
	 */
	public HttpDeleteWithBody(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	/**
	 * 带正文 HTTP 删除
	 *
	 * @param uri URI
	 */
	public HttpDeleteWithBody(final URI uri) {
		super();
		setURI(uri);
	}

	/**
	 * 带正文 HTTP 删除
	 */
	public HttpDeleteWithBody() {
		super();
	}

}
