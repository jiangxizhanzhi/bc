package com.cyberpeace.bs.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.impl.TraceIdContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.bs.utils.model.LcResponse;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.HttpclientContext;
import com.cyberpeace.common.model.HttpclientModel;
import com.cyberpeace.common.util.JsonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author SN
 */
@Slf4j
public class LcHttpClient {

	private static final String ROOT_TOKEN = "ce327da70ad66c48c209bc9d00cd287b992a86ed";

	/**
	 * 所有参数都放在url中都get方法
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
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		setAuthorization(httpGet);
		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	public static <T> T post(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);
		setAuthorization(post);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(post, resultType);
	}

	public static <T> T patch(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);
		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);
		HttpPatch patch = new HttpPatch(url);
		patch.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		patch.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		patch.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		patch.setEntity(entity);
		setAuthorization(patch);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(patch, resultType);
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
			HttpclientContext.getContext().addLog(httpResponse);
		} catch (Exception e) {
			try {
				HttpclientContext.getContext().doRemoteException(e);
			} catch (Exception e1) {
				log.error("remote error logging Exception", e1);
			} finally {
				HttpclientContext.removeContext();
				throw new RemoteServiceException();
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
			LcResponse baseRsp = JSON.parseObject(httpResponse, LcResponse.class);

			if (!isSuccess(baseRsp)) {
				// 只要lc的状态码不是0，都认为是有问题的
				log.error("url=[{}], result=[{}] error, code=[{}]",
						HttpclientContext.getContext().getUrl(), httpResponse, baseRsp.getCode());
				throw new RemoteServiceException(baseRsp.getMsg());
			}
			if (null == baseRsp.getData()) {
				return null;
			}
			return JsonUtil.toJsonObject(baseRsp.getData().toString(), resultType);
		} catch (RemoteServiceException e) {
			throw e;
		} catch (Exception e) {
			// 遇到不能解析的返回结果，直接报错
			log.error("parse httpResponse error, url=[{}], result=[{}], resultType=[{}]",
					HttpclientContext.getContext().getUrl(), httpResponse, resultType);
			throw e;
		} finally {
			HttpclientContext.removeContext();
		}
	}

	/**
	 * 获取实际执行请求的httpclient对象。
	 * <p>
	 * 后期使用池化
	 * @return
	 */
	private static HttpClient getClient() {
		CloseableHttpClient httpClient = HttpClients.custom().build();

		return httpClient;
	}

	/**
	 * 判断请求码
	 * @param rsp
	 * @return
	 */
	private static boolean isSuccess(LcResponse rsp) {
		return rsp.getCode().equals("0");
	}

	private static void setAuthorization(HttpUriRequest request) {
		String token = "Bearer " + ROOT_TOKEN;
		request.setHeader("Authorization", token);
	}

	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
	}
}
