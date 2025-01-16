package com.cyberpeace.iov.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.TraceIdContext;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.HttpclientContext;
import com.cyberpeace.common.model.HttpclientModel;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.iov.model.CanBaseRsp;
import com.google.common.collect.Lists;

public class IovHttpClientUtil {

	private static final Logger LOG = LoggerFactory.getLogger("can");
	private static final String SUCCESS_CODE = "200";
	private static List<Header> defaultHeaders;

	private IovHttpClientUtil() {}

	/**
	 * 执行post请求
	 * @param url 请求的url地址
	 * @param body 输入参数
	 * @return
	 */
	public static JSONArray postJson(String url, Object body) {
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
		return doHttp(post);
	}


	/**
	 * post请求，下载文件
	 * @param url
	 * @param fileName
	 */
	public static void rawPostFile(String url,Object body, String fileName) {

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// 创建 GET 请求
			HttpPost httpPost = new HttpPost(url);
			// 2、设置请求参数
			if(null != body){
				String reqJson = JsonUtil.toJsonString(body, PropertyNamingStrategy.SnakeCase);
				StringEntity reqEntity = new StringEntity(reqJson, WebConstant.ENCODING);
				httpPost.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
				httpPost.setEntity(reqEntity);
			}

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

	public static JSONArray rawPostUploadFile(String url, MultipartFile file) {
		setContext(url);
		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			// 创建 POST 请求
			HttpPost httpPost = new HttpPost(url);

			try {
				// 构建 MultipartEntity
				HttpEntity multipartEntity = MultipartEntityBuilder.create()
						.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
						.addBinaryBody("blf", file.getInputStream(), ContentType.DEFAULT_BINARY, file.getOriginalFilename())
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
				return parseHttpResponse(httpResponse);
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
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
	}

	private static JSONArray doHttp(HttpUriRequest httpRequest) {
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
				throw new RemoteServiceException(e.getMessage());
			} catch (Exception e1) {
				LOG.error("remote error logging Exception", e1);
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
		return parseHttpResponse(httpResponse);
	}

	/**
	 * 序列化http返回结果
	 *
	 * @param httpResponse http返回
	 * @return
	 */
	private static JSONArray parseHttpResponse(String httpResponse) {

		try {
			CanBaseRsp baseRsp = JSON.parseObject(httpResponse, CanBaseRsp.class);

			if (!isSuccess(baseRsp)) {
				LOG.error("url=[{}], result=[{}] error, code=[{}]",
						HttpclientContext.getContext().getUrl(), httpResponse, baseRsp.getCode());
				if (baseRsp.getMsg().get(0).contains("fuzzing")) {
					throw new RemoteServiceException("请先停止模糊测试");
				}
				if (baseRsp.getMsg().get(0).contains("msg")) {
					throw new RemoteServiceException("请先停止指令发送");
				}
				if (baseRsp.getMsg().get(0).contains("replay")) {
					throw new RemoteServiceException("请先停止流量重放");
				}
				throw new RemoteServiceException(baseRsp.getMsg().get(0));
			}
			if (null == baseRsp.getData().get(0)) {
				return new JSONArray();
			}
			return JsonUtil.stringToArray(baseRsp.getData().toString());
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

}
