package com.cyberpeace.common.util;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.*;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * HTTP 客户端实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
public class HttpClientUtil {

	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger("mp");

	/**
	 * HTTP 客户端实用程序
	 */
	private HttpClientUtil() {}

	/**
	 * 默认标头
	 */
	private static List<Header> defaultHeaders;

	/**
	 * 成功后缀
	 */
	private static final String SUCCESS_POSTFIX = "-000000";

	/**
	 * 执行post请求
	 *
	 * @param url        请求的url地址
	 * @param body       输入参数
	 * @param resultType 返回的数据类型
	 * @return
	 */
	public static <T> T post(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		if (CommonContext.getCyberToken() != null) {
			post.setHeader(WebConstant.AUTHORIZATION_HEADER, "Bearer " + CommonContext.getCyberToken());
		}
		post.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(post, resultType);
	}

	public static String post(String url, Object body) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(post);
	}


	/**
	 * 删除
	 *
	 * @param url        网址
	 * @param body       身体
	 * @param resultType 结果类型
	 * @return {@code T }
	 */
	public static <T> T delete(String url, Object body, Class<T> resultType) {
		// 1、设置请；线程数据
		setContext(url);

		// 2、设置请求参数
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
	 * 请求地址中包含特殊字符的可使用该方法 比如空格 * ｜
	 *
	 * @param url
	 * @param resultType
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
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		if (CommonContext.getCyberToken() != null) {
			httpGet.setHeader(WebConstant.AUTHORIZATION_HEADER, "Bearer " + CommonContext.getCyberToken());
		}

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	/**
	 * 所有参数都放在url中都get方法
	 *
	 * @param url
	 * @param resultType
	 * @return
	 */
	public static <T> T rawGet(String url, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、生成get请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		if (CommonContext.getCyberToken() != null) {
			httpGet.setHeader(WebConstant.AUTHORIZATION_HEADER, "Bearer " + CommonContext.getCyberToken());
		}
		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	/**
	 * 上传文件到远程服务
	 *
	 * @param url  url
	 * @param file 文件
	 * @return {@code String}
	 */
	public static <T> T uploadFile2Mp(String url, MultipartFile file, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);
		// 获取文件名
		String filename = file.getOriginalFilename();
		HttpPost httpPost = new HttpPost(url);
		// 创建请求体
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		try {
			builder.addBinaryBody("file", file.getBytes(),
					ContentType.create("multipart/form-data"), filename);
		} catch (IOException e) {
			LOG.error("load file error: ", e);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		}
		HttpEntity entity = builder.build();
		// 设置请求体
		httpPost.setEntity(entity);
		T rsp;
		try {
			rsp = doHttp(httpPost, resultType);
		} catch (RemoteServiceException rse){
			LOG.error("upload file failed, ", rse);
			throw rse;
		} catch (Exception le) {
			LOG.error("failed to upload file, ", le);
			throw new RemoteServiceException(ErrorCode.FILE_UPLOAD_FAIL);
		}
		return rsp;
	}

	/**
	 * 上传文件
	 *
	 * @param url        网址
	 * @param file       文件
	 * @param resultType 结果类型
	 * @return {@code T }
	 * @throws
	 * @see String
	 * @see MultipartFile
	 * @see Class<T>
	 * @see T
	 */
	public static <T> T rawPostUploadFile(String url, MultipartFile file, Class<T> resultType) {
		setContext(url);
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// 创建 POST 请求
			HttpPost httpPost = new HttpPost(url);

			try {
				// 构建 MultipartEntity
				HttpEntity multipartEntity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addBinaryBody("file", file.getInputStream(), ContentType.DEFAULT_BINARY, file.getOriginalFilename())
						.build();
				httpPost.setEntity(multipartEntity);

				CloseableHttpResponse response = httpClient.execute(httpPost);
				// 检查响应状态码
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					LOG.error("rawPostUploadFile response code error, statusCode is {}", statusCode);
					throw new RemoteServiceException("上传文件请求异常");
				}

				// 2、解析远程服务的返回值
				String httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);
				// 4、解析结果并返回
				return parseHttpResponse(httpResponse, resultType);
			} catch (IOException e) {
				LOG.error("rawPostUploadFile error url ={}, fileName={}", url, file.getOriginalFilename(), e);
				throw new RemoteServiceException(e.getMessage());
			}
		} catch (IOException e) {
			LOG.error("rawPostUploadFile error", e);
			throw new RuntimeException(e);
		}finally {
			HttpclientContext.removeContext();
		}
	}

	/**
	 * 设置请求上下文
	 *
	 * @param url 网址
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
	}

	/**
	 * 执行 HTTP
	 *
	 * @param httpRequest HTTP 请求
	 * @param resultType  结果类型
	 * @return {@code T }
	 */
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
	 * 执行 HTTP
	 *
	 * @param httpRequest HTTP 请求
	 * @return {@code T }
	 */
	private static String doHttp(HttpUriRequest httpRequest) {
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
		return parseHttpResponse(httpResponse);
	}

	/**
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @param resultType   需要序列化的类型
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
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @return
	 */
	private static String parseHttpResponse(String httpResponse) {

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
			return baseRsp.getData().toString();
		} catch (RemoteServiceException e) {
			throw e;
		} catch (Exception e) {
			// 遇到不能解析的返回结果，直接报错
			LOG.error("parse httpResponse error, url=[{}], result=[{}]",
					HttpclientContext.getContext().getUrl(), httpResponse);
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
	 *
	 * @param baseRsp
	 * @return
	 */
	private static boolean isSuccess(BaseRsp baseRsp) {
		return StringUtils.endsWith(baseRsp.getCode(), SUCCESS_POSTFIX) || StringUtils.endsWith(baseRsp.getCode(), "200");
	}

	/**
	 * 附带参数get查询
	 *
	 * @param obj        对象参数 根据对象字段和值去查询 过滤掉空值字段
	 * @param url        接口地址
	 * @param resultType
	 * @return
	 */
	public static <T> T rawGetByParam(Object obj, String url, Class<T> resultType) {

		TreeMap<String, Object> treeMap = ObjUtil.obj2TreemapExcludeEmpty(obj);
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
		return rawSpecialGet(url, resultType);

	}

}
