package com.cyberpeace.bs.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.impl.TraceIdContext;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.bs.constant.BsMessage;
import com.cyberpeace.bs.utils.model.LcResponse;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.LcServiceException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.HttpclientContext;
import com.cyberpeace.common.model.HttpclientModel;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 远程http客户端
 *
 * @author SN
 * @date 2023/05/08
 */
@Slf4j
public class LcHttpClient {

	/**
	 * 远程请求令牌
	 */
	private static String ROOT_TOKEN = null;

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
		// 3、执行实际调用、返回序列化后的结果
		return doHttp(httpGet, resultType);
	}

	public static <T> T post(String url, Object body, Class<T> resultType) {
		// 1、设置请求线程数据
		setContext(url);

		// 2、设置请求参数
		StringEntity entity = new StringEntity(JsonUtil.toJsonString(body), WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		// 设置headers
		post.setHeader(WebConstant.TRACE_HEADER, TraceIdContext.getTraceId());
		post.setHeader(WebConstant.CONTENT_TYPE_NAME, WebConstant.CONTENT_TYPE);
		post.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		post.setEntity(entity);

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
		patch.setHeader(WebConstant.CONTENT_TYPE_NAME, WebConstant.CONTENT_TYPE);
		patch.setHeader(WebConstant.LANGUAGE, CommonContext.getLanguage());
		patch.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		return doHttp(patch, resultType);
	}

	private static <T> T doHttp(HttpUriRequest httpRequest, Class<T> resultType) {
		setAuthorization(httpRequest);
		HttpResponse response = null;
		String httpResponse = null;
		try {
			// 1、调用远程服务
			response = getClient().execute(httpRequest);
			if (response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
				throw new LcServiceException(response.getStatusLine().getStatusCode());
			}

			// 2、解析远程服务的返回值
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);

			// 3、记录调用日志
			HttpclientContext.getContext().addLog(httpRequest, httpResponse, false);
		} catch (Exception e) {
			try {
				HttpclientContext.getContext().doRemoteException(e);
				throw new LcServiceException(e.getMessage());
			} catch (Exception e1) {
				log.error("remote error logging Exception", e1);
			} finally {
				HttpclientContext.removeContext();
			}
		}

		if (StringUtils.isBlank(httpResponse)) {
			HttpclientContext.getContext().addLog(httpRequest, "httpResponse is null", false);
			HttpclientContext.removeContext();
			throw new LcServiceException();
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
				throw new LcServiceException(baseRsp.getMsg());
			}
			if (null == baseRsp.getData()) {
				return null;
			}
			// 特殊处理要求返回类型为string的情形
			String jsonString = BsUtil.handleStringData(baseRsp.getData().toString(), resultType);
			return JsonUtil.toJsonObject(jsonString, resultType);
		} catch (LcServiceException e) {
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
	 * @param rsp 响应
	 * @return
	 */
	private static boolean isSuccess(LcResponse rsp) {
		return rsp.getCode().equals("0");
	}

	/**
	 * 设置令牌
	 *
	 * @param request 请求
	 */
	private static void setAuthorization(HttpUriRequest request) {
		request.setHeader("Authorization", getToken());
	}

	/**
	 * 获取令牌
	 *
	 * @return {@code String}
	 */
	private static String getToken() {
		if (ROOT_TOKEN == null) {
			// 系统配置中读取令牌
			String token = SystemPropUtil.getProp("LC_PERMANENT_TOKEN");
			if (StringUtils.isBlank(token)) {
				throw new BusinessException(BsErrorCode.TOKEN_NOT_CONFIGURED);
			}
			ROOT_TOKEN = token;
		}
		return "Bearer " + ROOT_TOKEN;
	}

	/**
	 * 设置请求上下文
	 * @param url
	 */
	private static void setContext(String url) {
		HttpclientModel httpclientModel = new HttpclientModel(TraceIdContext.getTraceId(), url);
		HttpclientContext.setContext(httpclientModel);
	}

	/**
	 * 上传文件到远程服务
	 *
	 * @param url  url
	 * @param file 文件
	 * @return {@code String}
	 */
	public static String uploadFile2Lc(String url, MultipartFile file) {
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
			log.error("load file error: ", e);
			throw new BusinessException(BsErrorCode.FAILED_TO_LOAD_FILE);
		}
		HttpEntity entity = builder.build();
		// 设置请求体
		httpPost.setEntity(entity);
		String rsp = "";
		try {
			rsp = doHttp(httpPost, String.class);
		} catch (LcServiceException le) {
			log.error("failed to upload file, ", le);
			throw new LcServiceException(BsMessage.INVALID_FILE);
		}
		return rsp;
	}

	/**
	 * 导出行为
	 *
	 * @param response      响应
	 * @param lcBehaviorIds 远程行为id列表
	 * @param url           url
	 */
	public static void exportBehavior(HttpServletResponse response, List<Long> lcBehaviorIds,
			String url) {
		ServletOutputStream out = null;
		InputStream inputStream = null;
		// 创建httpclient对象
		CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpResponse lcRsp = getClosableHttpRspByPost(httpClient, url, lcBehaviorIds);
		if (lcRsp == null) {
			throw new RemoteServiceException();
		}
		try {
			// 拿到响应结果实体
			HttpEntity entity = lcRsp.getEntity();
			if (entity == null) {
				throw new RemoteServiceException("Empty response from lc behavior export");
			}
			// not 200 rsp || not zip file
			if (lcRsp.getStatusLine().getStatusCode() != HttpStatus.SC_OK
					|| !entity.getContentType().toString().contains("application/zip")) {
				String body = EntityUtils.toString(lcRsp.getEntity(), WebConstant.ENCODING);
				log.error("lc remote exception: {}", body);
				throw new RemoteServiceException(getErrorMessage(body));
			}
			// 获取文件流
			inputStream = entity.getContent();
			// 设置文件ContentType类型和文件名
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", String.format(
					"attachment; filename=behavior_export_%d.zip", System.currentTimeMillis()));
			out = response.getOutputStream();
			// input文件流写入out
			int len = 0;
			byte[] buffer = new byte[1024 * 10];
			while ((len = inputStream.read(buffer)) != -1) {
				out.write(buffer, 0, len);
			}
			out.flush();
			// 释放资源
			EntityUtils.consume(entity);
		} catch (RemoteServiceException e1) {
			throw e1;
		} catch (Exception e) {
			log.error("failed to export behavior", e);
			throw new BusinessException(BsErrorCode.FAILED_TO_EXPORT);
		} finally {
			try {
				// 释放资源
				httpClient.close();
				lcRsp.close();
				if (inputStream != null) {
					inputStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 尝试获取错误提示
	 *
	 * @param rsp 响应
	 * @return {@code String}
	 */
	private static String getErrorMessage(String rsp) {
		try {
			LcResponse response = JsonUtil.toJsonObject(rsp, LcResponse.class);
			return response.getMsg();
		} catch (Exception e) {
			return rsp;
		}
	}

	/**
	 * 获取http响应，用于文件流处理
	 *
	 * @param httpClient http客户端
	 * @param url        url
	 * @param body       请求体
	 * @return {@code CloseableHttpResponse}
	 */
	private static <T> CloseableHttpResponse
			getClosableHttpRspByPost(CloseableHttpClient httpClient, String url, T body) {
		CloseableHttpResponse res = null;
		try {
			// 2.创建post方式请求对象
			HttpPost httpPost = new HttpPost(url);
			// 4.设置 json body
			httpPost.addHeader(WebConstant.CONTENT_TYPE_NAME, "application/json");
			httpPost.setEntity(new StringEntity(JsonUtil.toJsonString(body)));
			// set headers
			setAuthorization(httpPost);
			// do request
			res = httpClient.execute(httpPost);
		} catch (Exception e) {
			log.error("failed to get rsp from lc: ", e);
		}
		return res;
	}
}
