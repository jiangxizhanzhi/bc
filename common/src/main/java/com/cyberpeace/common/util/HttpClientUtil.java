package com.cyberpeace.common.util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.BaseRsp;

public class HttpClientUtil {

	private static final Logger LOG = LoggerFactory.getLogger(HttpClientUtil.class);

	private HttpClientUtil() {}

	private static List<Header> defaultHeaders;

	private static final String SUCCESS_POSTFIX = "-000000";

	/**
	 * 所有参数都放在url中都get方法
	 *
	 * @param resultType
	 * @param url
	 * @param <T>
	 * @return
	 */
	public static <T> T rawGet(String url, Class<T> resultType) {
		// 定义输出日志类型
		// 分别为调用耗时、url、返回结果
		String logFormatter = "httpclient rawGet |{}|{}|{}";
		long startTime = System.currentTimeMillis();
		long callTimeCost;

		if (StringUtils.isBlank(TraceIdContext.getTraceId())) {
			TraceIdContext.setTraceId(UUID.randomUUID().toString());
		}

		// 1、生成get请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());

		// 2、生成httpclient对象
		HttpClient httpClient = getClient();

		// 3、执行实际调用
		HttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (Exception e) {
			callTimeCost = System.currentTimeMillis() - startTime;
			LOG.error(logFormatter, new Object[] { callTimeCost, url, null }, e);

			throw new RemoteServiceException();
		}

		// 4、从流中读取返回值
		String httpResponse = null;
		try {
			httpResponse = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			callTimeCost = System.currentTimeMillis() - startTime;
			LOG.error(logFormatter, new Object[] { callTimeCost, url, null }, e);

			throw new RemoteServiceException();
		}

		callTimeCost = System.currentTimeMillis() - startTime;
		LOG.info(logFormatter, new Object[] { callTimeCost, url, httpResponse });

		// 5、返回数据序列化
		T ret = parseHttpResponse(httpResponse, resultType, url);

		return ret;
	}

	/**
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @param resultType   需要序列化的类型
	 * @param url          接口url，出错的时候打印
	 * @param <T>          返回结果类型
	 * @return
	 */
	private static <T> T parseHttpResponse(String httpResponse, Class<T> resultType, String url) {
		if (resultType.equals(String.class)) {
			return (T) httpResponse;
		}

		T result = null;

		try {
			BaseRsp baseRsp = JSON.parseObject(httpResponse, BaseRsp.class);

			if (!isSuccess(baseRsp)) {
				// 只要中台的错误码不是000000，都认为是有问题的
				LOG.error("url=[{}], result=[{}] error, code=[{}]",
						new Object[] { url, httpResponse, baseRsp.getCode() });
				throw new RemoteServiceException();
			}

			String realData = baseRsp.getData().toString();
			result = JSON.parseObject(realData, resultType);

			return result;
		} catch (Exception e) {
			// 遇到不能解析的返回结果，直接报错
			LOG.error("parse httpResponse error, url=[{}], result=[{}], resultType=[{}]",
					new Object[] { url, httpResponse, resultType }, e);

			throw new RemoteServiceException();
		}
	}

	/**
	 * 获取实际执行请求的httpclient对象。
	 * <p>
	 * 后期使用池化
	 *
	 * @return
	 */
	private static HttpClient getClient() {
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(defaultHeader())
				.build();

		return httpClient;
	}

	/**
	 * 默认加载的http请求头
	 *
	 * @return
	 */
	private static List<Header> defaultHeader() {
		if (null != defaultHeaders) {
			return defaultHeaders;
		}

		// 以下代码可能会并发，不会带来大问题
		String mpAuthenticationParam = SystemPropUtil.getProp("MP_SECRET");

		LOG.info("read prop mpAuthenticationParam=[{}]", mpAuthenticationParam);

		String[] ss = StringUtils.split(mpAuthenticationParam, ",");
		BasicHeader clientId = new BasicHeader(WebConstant.CLIENT_ID, ss[0]);

		BasicHeader clientSecret = new BasicHeader(WebConstant.CLIENT_SECRET, ss[1]);

		List<Header> defaultHeader = Arrays.asList(clientId, clientSecret);

		HttpClientUtil.defaultHeaders = defaultHeader;

		return defaultHeader;
	}

	/**
	 * 判断请求码
	 * @param baseRsp
	 * @return
	 */
	private static boolean isSuccess(BaseRsp baseRsp) {
		return StringUtils.endsWith(baseRsp.getCode(), SUCCESS_POSTFIX);
	}

	public static <T> T rawGetByParam(List<BasicNameValuePair> nvps, String url,
			Class<T> resultType) {

		String paramsStr = "";
		if (nvps.size() > 0) {
			paramsStr = "";
			try {
				HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
				paramsStr = EntityUtils.toString(urlEncodedFormEntity);
				url = url + "?" + paramsStr;
			} catch (Exception e) {
				LOG.error("error ", e);
			}
		}
		return rawGet(url, resultType);

	}

}
