package com.cyberpeace.common.model;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * http delete和请求体
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class HttpDeleteWithBody extends HttpEntityEnclosingRequestBase {

	public static final String METHOD_NAME = "DELETE";

	/**
	 * get方法
	 *
	 * @return {@link String}
	 */
	@Override
	public String getMethod() {
		return METHOD_NAME;
	}

	/**
	 * http delete和请求体
	 *
	 * @param uri uri
	 */
	public HttpDeleteWithBody(final String uri) {
		super();
		setURI(URI.create(uri));
	}

	/**
	 * http delete和请求体
	 *
	 * @param uri uri
	 */
	public HttpDeleteWithBody(final URI uri) {
		super();
		setURI(uri);
	}

	/**
	 * http delete和请求体
	 */
	public HttpDeleteWithBody() {
		super();
	}

}
