package com.cyberpeace.common.util;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

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
import com.cyberpeace.common.model.*;
import com.google.common.collect.Lists;

public class HttpClientUtil {

	private static final Logger LOG = LoggerFactory.getLogger("mp");

	private HttpClientUtil() {}

	private static List<Header> defaultHeaders;

	private static final String SUCCESS_POSTFIX = "-000000";

	private static volatile Boolean verbose = false;

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
		setContext(url, null);

		// 2、执行实际调用、返回序列化后的结果
		return doHttp(getHttpPost(url, body), resultType);
	}

	public static <T> T delete(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url, null);

		// 2、构建删除请求
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpDeleteWithBody delete = new HttpDeleteWithBody(url);
		delete.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		delete.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		delete.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		delete.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(delete, resultType);
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
		// 使用拼接好参数的url调用接口
		return rawGet(convertRawUrl(obj, url), resultType);

	}

	private static HttpGet getHttpGet(String url) {
		// 1、构建请求
		HttpGet httpGet = new HttpGet(url);

		// 2、 配置请求头基本信息
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());

		// 3、 配置用户权限信息
		setAuthorization(httpGet);

		return httpGet;
	}

	private static HttpPost getHttpPost(String url, Object body) {
		// 1、构建请求
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);

		return post;
	}

	public static String convertRawUrl(Object obj, String url) {
		// 1、将请求体转换为键值对
		TreeMap<String, Object> treeMap = ObjUtil.obj2TreemapExcludeEmpty(obj);
		List<BasicNameValuePair> nvps = Lists.newArrayList();
		treeMap.forEach((key, value) -> nvps
				.add(new BasicNameValuePair(key, JsonUtil.toJsonString(value))));

		// 2、将请求参数拼接到url上
		try {
			HttpEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nvps, WebConstant.ENCODING);
			String paramsStr = EntityUtils.toString(urlEncodedFormEntity);
			url = url + "?" + paramsStr;
		} catch (Exception e) {
			LOG.error("rawGetByParam: Request param format failed! params={}, url=[{}]",
					JsonUtil.toJsonString(treeMap), url, e);
			throw new RemoteServiceException();
		}
		return url;
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
		setContext(url, null);

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
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());

		String jwt;
		if (StringUtils.isNotBlank(jwt = CommonContext.getAuthorization())) {
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
		setContext(url, null);

		// 2、执行实际调用、返回序列化后的结果
		return doHttp(getHttpGet(url), resultType);
	}

	public static <T> T tokenGet(String url, Class<T> resultType, String field, String token) {

		// 1、设置请求线程数据
		setContext(url, null);

		// 2、生成get请求
		HttpGet httpGet = getHttpGet(url);
		httpGet.setHeader(field, token);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	/**
	 * 使用get请求运维系统接口
	 */
	public static <T> T ywGet(String url, Class<T> resultType, String errorMsg) {
		// 1、设置请求线程数据
		setContext(url, errorMsg);

		// 2、生成get请求
		HttpGet httpGet = getHttpGet(url);
		httpGet.setHeader(WebConstant.TOKEN, WebConstant.YW_TOKEN);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(getHttpGet(url), resultType);
	}

	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url, String errorMsg) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
		HttpclientContext.setErrorMsg(errorMsg);
	}

	private static <T> T doHttp(HttpUriRequest httpRequest, Class<T> resultType) {
		HttpResponse response = null;
		String httpResponse = null;
		try {
			// 1、调用远程服务
			response = getClient().execute(httpRequest);

			// 2、解析远程服务的返回值
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);

			// 3、记录调用日志
			HttpclientContext.getContext().addLog(httpRequest, httpResponse, verbose);

		} catch (Exception e) {
			try {
				// 4、如果接口报错（未返回返回体），打印错误日志
				HttpclientContext.getContext().doRemoteException(e);
			} catch (Exception e1) {
				LOG.error("remote error logging Exception", e1);
			} finally {

				// 5、抛出远程服务异常
				HttpclientContext.removeContext();
				throw new RemoteServiceException(HttpclientContext.getErrorMsg());
			}
		}

		if (StringUtils.isBlank(httpResponse)) {
			HttpclientContext.getContext().addLog(httpRequest, "httpResponse is null", verbose);
			HttpclientContext.removeContext();
			throw new RemoteServiceException(HttpclientContext.getErrorMsg());
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
			BaseRsp baseRsp = JSON.parseObject(httpResponse, BaseRsp.class);

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
		CloseableHttpClient httpClient = HttpClients.custom().setDefaultHeaders(defaultHeader())
				.build();

		return httpClient;
	}

	/**
	 * 设置请求的权限校验信息
	 *
	 * @return
	 */
	private static void setAuthorization(HttpUriRequest request) {
		String authorization = CommonContext.getAuthorization();
		// 如果存在token信息，则写入请求头
		if (null != authorization) {
			request.setHeader(WebConstant.AUTHORIZATION_HEADER, "JWT " + authorization);
		}
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
		return StringUtils.endsWith(baseRsp.getCode(), SUCCESS_POSTFIX)
				// 兼容外部平台
				|| baseRsp.getCode().equals("0") || baseRsp.getCode().equals("200");
	}

	/**
	 * 修改是否打印请求详情
	 */
	public static boolean changeMode(Boolean mode) {
		verbose = mode;
		return true;
	}

}
