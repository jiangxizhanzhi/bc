package com.cyberpeace.iov.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;

import com.cyberpeace.common.util.FileUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.HttpDeleteWithBody;
import com.cyberpeace.common.model.HttpclientContext;
import com.cyberpeace.common.model.HttpclientModel;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.cyberpeace.iov.model.CanBaseRsp;
import com.google.common.collect.Lists;
import org.springframework.web.multipart.MultipartFile;

public class IovHttpClientUtil {

	private static final Logger LOG = LoggerFactory.getLogger("can");
	private static final String SUCCESS_CODE = "200";
	private static List<Header> defaultHeaders;

	private IovHttpClientUtil() {}

	/**
	 * 执行post请求
	 * @param url 请求的url地址
	 * @param body 输入参数
	 * @param resultType 返回的数据类型
	 * @param <T>
	 * @return
	 */
	public static <T> T postJson(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		JSONObject object = new JSONObject();
		if (null != body) {
			object.put("params", body);
		}
		String reqJson = JsonUtil.toJsonString(object, PropertyNamingStrategy.SnakeCase);
		StringEntity entity = new StringEntity(reqJson, WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
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
		delete.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
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
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
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
		httpGet.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		httpGet.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		String authorization = CommonContext.getAuthorization();
		if (null != authorization) { // 调用竞赛接口需要
			httpGet.setHeader(WebConstant.AUTHORIZATION_HEADER, "JWT " + authorization);
		}
		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}


	/**
	 * post请求，下载文件
	 * @param url
	 * @param fileName
	 */
	public static void rawPostFile(String url, String fileName) {

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// 创建 GET 请求
			HttpPost httpPost = new HttpPost(url);

			try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
				// 检查响应状态码
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					LOG.error("rawPostFile response code error, statusCode is {}", statusCode);
					throw new RemoteServiceException("第三方请求异常");
				}

				// 获取响应实体
				HttpEntity entity = response.getEntity();

				// 将文件流保存到本地文件
				saveToFile(entity, fileName);
			}
		} catch (IOException e) {
			LOG.error("rawPostFile error url ={}, fileName={}", url, fileName, e);
			throw new RemoteServiceException(e.getMessage());
		}
	}
	private static void saveToFile(HttpEntity entity, String fileName) throws IOException {
		LOG.info("开始下载文件到本地文件: {}", fileName);
		final File file = new File(fileName);
		final File parentFile = file.getParentFile();
		FileUtil.createPath(parentFile);
		final boolean created  = file.createNewFile();
		if (!created) {
			LOG.warn("文件已存在, {}", file.getAbsolutePath());
		}
		try (FileOutputStream fos = new FileOutputStream(file)) {
			byte[] buffer = new byte[4096];
			int bytesRead;

			while ((bytesRead = entity.getContent().read(buffer)) != -1) {
				fos.write(buffer, 0, bytesRead);
			}
		}
		LOG.info("结束下载文件到本地文件: {}", fileName);
	}

	public static <T> T rawPostUploadFile(String url, MultipartFile file, Class<T> resultType) {
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// 创建 POST 请求
			HttpPost httpPost = new HttpPost(url);

			try {
				// 构建 MultipartEntity
				HttpEntity multipartEntity = MultipartEntityBuilder.create()
						.addPart("file", new FileBody(new File(file.getOriginalFilename()), Files.probeContentType(Paths.get(file.getOriginalFilename()))))
						.build();
				httpPost.setEntity(multipartEntity);

				CloseableHttpResponse response = httpClient.execute(httpPost);
				// 检查响应状态码
				int statusCode = response.getStatusLine().getStatusCode();
				if (statusCode != 200) {
					LOG.error("rawPostUploadFile response code error, statusCode is {}", statusCode);
					throw new RemoteServiceException("第三方请求异常");
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
        }
    }
	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
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
			} finally {
				HttpclientContext.removeContext();
				throw new RemoteServiceException(e.getMessage());
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
			CanBaseRsp baseRsp = JSON.parseObject(httpResponse, CanBaseRsp.class);

			if (!isSuccess(baseRsp)) {
				LOG.error("url=[{}], result=[{}] error, code=[{}]",
						HttpclientContext.getContext().getUrl(), httpResponse, baseRsp.getCode());
				throw new RemoteServiceException(baseRsp.getMsg().get(0));
			}
			if (null == baseRsp.getData().get(0)) {
				return null;
			}
			return JsonUtil.toJsonObjectArray(baseRsp.getData().toString(), resultType).get(0);
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
		RequestConfig requestConfig = RequestConfig.custom()
				.setConnectTimeout(10000) // 连接超时时间 10 秒
				.setSocketTimeout(1000 * 60 * 5)  // 数据读取超时时间 10 秒
				.build();

		return HttpClients.custom()
				.setDefaultRequestConfig(requestConfig)
				.setDefaultHeaders(defaultHeader()).build();
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

		List<Header> defaultHeader = Lists.newArrayList();

		return defaultHeader;
	}

	/**
	 * 判断请求码
	 * @param baseRsp
	 * @return
	 */
	private static boolean isSuccess(CanBaseRsp baseRsp) {
		return baseRsp.getCode().contains(SUCCESS_CODE);
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
		return rawGet(url, resultType);

	}
}
