package com.cyberpeace.common.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.exception.evaluate.RemoteException;

public class LocalClient {

	private static final Logger log = LoggerFactory.getLogger("httpclient");

	static int timeout = 600 * 1000;

	public static <T> T postWithForm(List<BasicNameValuePair> nvps, String url,
			Class<T> resultType) {
		HttpClient client = getHttpClient(timeout);
		HttpPost httppost = new HttpPost(url);

		UrlEncodedFormEntity entity = null;
		try {
			entity = new UrlEncodedFormEntity(nvps, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("UnsupportedEncodingException ", e);
		}

		httppost.setEntity(entity);

		// 如果入参过大，截短打印
		String toPrintParams = "";
		try {
			String jsonParmas = JSON.toJSONString(nvps);
			if (StringUtils.length(jsonParmas) < 2000) {
				toPrintParams = jsonParmas;
			}
		} catch (Exception e) {
			// 什么都不干
		}

		long start = System.currentTimeMillis();

		log.info("call url=[{}],  params=[{}]", new Object[] { url, toPrintParams });

		try {
			CloseableHttpResponse response = (CloseableHttpResponse) client.execute(httppost);
			String message = EntityUtils.toString(response.getEntity(), "UTF-8");

			String toPrintResult = "";
			if (StringUtils.length(message) <= 4000) {
				toPrintResult = message;
			}

			// 先校验http返回码，只有200才认为正常
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode < 200 || statusCode >= 400) {
				log.error(
						"call url=[{}], params=[{}] error, httpCode=[{}], message=[{}], timeCost[{}]",
						new Object[] { url, toPrintParams, statusCode, toPrintResult,
								(System.currentTimeMillis() - start) });
				throw new RemoteException();
			}

			log.info("call url=[{}],  params=[{}], result=[{}], timeCost[{}]", new Object[] { url,
					toPrintParams, toPrintResult, (System.currentTimeMillis() - start) });

			// 字符串形式不用json
			if (resultType.equals(String.class)) {
				String result = (String) message;
				if (StringUtils.startsWith(result, "\"") && StringUtils.endsWith(result, "\"")) {
					result = StringUtils.substring(message, 1, result.length() - 1);
				}
				return (T) result;
			}

			T result = JSON.parseObject(message, resultType);
			return result;
		} catch (IOException e) {
			log.info("call url=[{}],  , timeCost[{}]=[{}] error", url,
					(System.currentTimeMillis() - start), e);
			throw new RemoteException();
		}

	}

	/**
	 * get方法 ,默认utf-8格式
	 *
	 * @return
	 */
	public static <T> T get(List<BasicNameValuePair> nvps, String url, Class<T> resultType) {
		long startTime = System.currentTimeMillis();

		if (StringUtils.isBlank(TraceIdContext.getTraceId())) {
			TraceIdContext.setTraceId(UUID.randomUUID().toString());
		}

		// 1、创建一个client对象
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 3、格式化参数,构造httpget
		HttpGet httpGet = null;
		String paramsStr = "";
		if (nvps.size() > 0) {
			paramsStr = "";
			try {
				HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
				paramsStr = EntityUtils.toString(urlEncodedFormEntity);
			} catch (IOException e) {
				log.error("error ", e);
			}
			// 4、构造HttpGet
			httpGet = new HttpGet(url + "?" + paramsStr);
		} else {
			httpGet = new HttpGet(url);
		}
		// 设置头部信息?
		// 5、获取的请求结果
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet);
		} catch (IOException e) {
			log.error("call url=[{}], params=[{}] error", url, paramsStr, e);
		}
		if (null == response) {
			return null;
		}
		// 6、解析返回信息
		String jsonString = null;
		try {
			jsonString = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (IOException e) {
			log.error("get response entity, IOException:", e);
		}

		// 校验http返回码，只有200才认为正常
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != 200) {
			log.info("call url=[{}], params=[{}] error, httpCode=[{}]",
					new Object[] { url, paramsStr, statusCode });
			return null;
		}

		int callTime = (int) ((System.currentTimeMillis()) - startTime);

		log.info("httpGet {}|{}|{}|{}", new Object[] { callTime, url, paramsStr, jsonString });

		// 字符串形式不用json
		if (resultType.equals(String.class)) {
			return (T) jsonString;
		}

		T result = null;
		try {
			result = JSON.parseObject(jsonString, resultType);
		} catch (Exception e) {
			log.error("parse object error, result=[{}]", jsonString, e);
			return null;
		}

		return result;
	}

	private static HttpClient getHttpClient(Integer timeOut) {
		// 创建Http请求配置参数
		RequestConfig requestConfig = RequestConfig.custom()
				// 获取连接超时时间
				.setConnectionRequestTimeout(timeOut)
				// 请求超时时间
				.setConnectTimeout(timeOut)
				// 响应超时时间
				.setSocketTimeout(timeOut).build();

		// 创建httpClient
		return HttpClients.custom().setDefaultRequestConfig(requestConfig).build();
	}

}