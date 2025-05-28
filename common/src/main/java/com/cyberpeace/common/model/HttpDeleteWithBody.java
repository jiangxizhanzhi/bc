/**
 * 这段代码定义了一个自定义的 HTTP 请求类 `HttpDeleteWithBody`，继承自 Apache HttpClient 的 `HttpEntityEnclosingRequestBase` 类。以下是简要分析： 
 * 1. **功能描述**： 
 * - 这是一个用于发送带请求体的 HTTP DELETE 请求的类。 
 * - 它扩展了 `HttpEntityEnclosingRequestBase`，允许在 HTTP DELETE 请求中携带请求体。 
 * 2. **关键特性**： 
 * - 提供了三种构造函数：通过字符串 URI、`URI` 对象和无参构造函数创建实例。 
 * - 重写了 `getMethod()` 方法，返回固定值 `"DELETE"`，表示该请求的方法类型。 
 * 3. **使用场景**： 
 * - 适用于需要在 HTTP DELETE 请求中传递数据的场景，比如删除资源时附带额外信息。 
 * 4. **优点**： 
 * - 封装了 HTTP DELETE 请求的常见用法，提高了代码复用性和可读性。 
 * 5. **潜在改进**： 
 * - 可以添加更多的方法或属性来处理请求头、请求体等细节。 
 * - 考虑增加异常处理逻辑，确保 URI 的合法性和请求的正确性。 
 * 总体而言，这是一个简洁且实用的工具类，适合在需要发送带请求体的 HTTP DELETE 请求时使用。 
**/
package com.cyberpeace.common.model;

import java.net.URI;

import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;

/**
 * 带正文 HTTP 删除
 *
 * @author SN
 * @date 2024/11/08
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
