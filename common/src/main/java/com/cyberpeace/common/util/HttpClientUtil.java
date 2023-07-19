package com.cyberpeace.common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.*;
import com.google.common.collect.Lists;

public class HttpClientUtil {

	private static final Logger LOG = LoggerFactory.getLogger("mp");

	private HttpClientUtil() {}

	private static List<Header> defaultHeaders;

	private static final String SUCCESS_POSTFIX = "-000000";

	/**
	 * 执行post请求
	 * @param url 请求的url地址
	 * @param body 输入参数
	 * @param resultType 返回的数据类型
	 * @param <T>
	 * @return
	 */
	public static <T> T post(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
    post.setHeader(WebConstant.TRACE_HEADER, ThreadContext.get("logId"));
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(post, resultType);
	}

	public static <T> T delete(String url, Object body, Class<T> resultType) {
		// 1、设置请；线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
		delete.setHeader(WebConstant.TRACE_HEADER, ThreadContext.get("logId"));
		delete.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		delete.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		delete.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(delete, resultType);
	}

	/**
	 * 请求地址中包含特殊字符的可使用该方法 比如空格 * ｜
	 * @param resultType
	 * @param url
	 * @param <T>
	 * @return
	 */
	public static <T> T rawSpecialGet(String url, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、生成get请求
		URL httpUrl;
		URI uri;
		try {
			httpUrl = new URL(url);
			uri = new URI(httpUrl.getProtocol(), null, httpUrl.getHost(), httpUrl.getPort(),
					httpUrl.getPath(), httpUrl.getQuery(), null);
		} catch (MalformedURLException | URISyntaxException e) {
			LOG.error("URL contains special characters, encoding conversion failed！", e);
			throw new RemoteServiceException();
		}

		HttpGet httpGet = new HttpGet(uri);
		httpGet.setHeader(WebConstant.TRACE_HEADER, ThreadContext.get("logId"));
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());

		String jwt = CommonContext.getAuthorization();
		if (StringUtils.isNotBlank(jwt)) {
			httpGet.setHeader(WebConstant.AUTHORIZATION_HEADER, "JWT " + jwt);
		}

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	/**
	 * 所有参数都放在url中都get方法
	 *
	 * @param resultType
	 * @param url
	 * @param <T>
	 * @return
	 */
	public static <T> T rawGet(String url, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、生成get请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(WebConstant.TRACE_HEADER, ThreadContext.get("logId"));
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		String authorization = CommonContext.getAuthorization();
		if (null != authorization) { // 调用竞赛接口需要
			httpGet.setHeader(WebConstant.AUTHORIZATION_HEADER, "JWT " + authorization);
		}
		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(ThreadContext.get("logId"), url);
		HttpclientContext.setContext(httpclientModel);
	}

	private static <T> T doHttp(HttpUriRequest httpRequest, Class<T> resultType) {
		HttpResponse response = null;
		String httpResponse = null;
		HttpclientContext.getContext().setMethod(httpRequest.getMethod());
		try {
			// 1、调用远程服务
			response = getClient().execute(httpRequest);

			// 2、解析远程服务的返回值
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);

			// 3、记录调用日志
			HttpclientContext.getContext().addLog(httpResponse);
		} catch (Exception e) {
			try {
				HttpclientContext.getContext().doRemoteException(e);
			} catch (Exception e1) {
				LOG.error("remote error logging Exception", e1);
				throw new RemoteServiceException();
			} finally {
				HttpclientContext.removeContext();
			}
		}

		if (StringUtils.isBlank(httpResponse)) {
			HttpclientContext.getContext().addLog("httpResponse is null");
			HttpclientContext.removeContext();
			throw new RemoteServiceException();
		}

		// 4、解析结果并返回
		return parseHttpResponse(httpResponse, resultType);
	}

	/**
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @param resultType   需要序列化的类型
	 * @param <T>          返回结果类型
	 * @return
	 */
	private static <T> T parseHttpResponse(String httpResponse, Class<T> resultType) {

		try {
			BaseRsp<Object> baseRsp = JSON.parseObject(httpResponse, BaseRsp.class);

			if (!isSuccess(baseRsp)) {
				// 只要中台的错误码不是000000，都认为是有问题的
				LOG.error("url=[{}], result=[{}] error, code=[{}]",
						HttpclientContext.getContext().getUrl(), httpResponse, baseRsp.getCode());
				throw new RemoteServiceException(baseRsp.getMessage());
			}
			if (null == baseRsp.getData()) {
				return null;
			}
			return JsonUtil.toJsonObject(baseRsp.getData().toString(), resultType);
		} catch (RemoteServiceException e) {
			throw e;
		} catch (Exception e) {
			// 遇到不能解析的返回结果，直接报错
			LOG.error("parse httpResponse error, url=[{}], result=[{}], resultType=[{}]",
					HttpclientContext.getContext().getUrl(), httpResponse, resultType);
			throw new RemoteServiceException();
		} finally {
			HttpclientContext.removeContext();
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

		return HttpClients.custom().setDefaultHeaders(defaultHeader()).build();
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
	private static boolean isSuccess(BaseRsp<Object> baseRsp) {
		return StringUtils.endsWith(baseRsp.getCode(), SUCCESS_POSTFIX);
	}

	/**
	 * 附带参数get查询
	 * @param obj 对象参数 根据对象字段和值去查询 过滤掉空值字段
	 * @param url 接口地址
	 * @param resultType
	 * @param <T>
	 * @return
	 */
	public static <T> T rawGetByParam(Object obj, String url, Class<T> resultType) {

		SortedMap<String, Object> treeMap = ObjUtil.obj2TreemapExcludeEmpty(obj);
		List<BasicNameValuePair> nvps = Lists.newArrayList();
		treeMap.forEach((key, value) -> nvps
				.add(new BasicNameValuePair(key, JsonUtil.toJsonString(value))));

		try {
			HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
			String paramsStr = EntityUtils.toString(urlEncodedFormEntity);
			url = url + "?" + paramsStr;
		} catch (Exception e) {
			LOG.error("rawGetByParam: Request param format failed! params={}, url=[{}]",
					JsonUtil.toJsonString(treeMap), url, e);
			throw new RemoteServiceException();
		}
		return rawGet(url, resultType);

	}

}
