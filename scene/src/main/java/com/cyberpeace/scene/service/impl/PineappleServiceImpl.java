package com.cyberpeace.scene.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.scene.service.PineappleService;
import com.cyberpeace.scene.service.bo.interactive.QQRes;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PineappleServiceImpl implements PineappleService {

	private String QQ_URL;

	private String LOGO_URL;

	private String API_URL;

	private String WS_URL;

	private Cache<String, String> COOKIE = CacheBuilder.newBuilder()
			.expireAfterWrite(30, TimeUnit.MINUTES).build();

	private String sessionKey = "PHPSESSID";

	@PostConstruct
	public void init() {

		String piHost = SystemPropUtil.getProp("PI_HOST");
		String piPort = ConfigUtil.getContextProperty("pi.port");
		String numberPath = ConfigUtil.getContextProperty("pi.qq.path");
		String logoPath = ConfigUtil.getContextProperty("pi.logo.path");
		String apiPath = ConfigUtil.getContextProperty("pi.api.path");
		String wsPort = ConfigUtil.getContextProperty("pi.ws.port");

		QQ_URL = "http://" + piHost + ":" + piPort + numberPath;
		LOGO_URL = "http://" + piHost + ":" + piPort + logoPath;
		API_URL = "http://" + piHost + ":" + piPort + apiPath;
		WS_URL = "ws://" + piHost + ":" + wsPort + "/";

		log.info("pineapple host=[{}], port=[{}]", piHost, piPort);
	}

	@Override
	public List<QQRes> listQQ() {
		List<QQRes> res = Lists.newArrayList();
		String rsp = HttpClientUtil.readPlainText(QQ_URL);
		if (StringUtils.isBlank(rsp)) {
			return res;
		}
		String[] numbers = rsp.split("\n");
		for (String number : numbers) {
			if (StringUtils.isNotBlank(number)) {
				String logoUrl = String.format(LOGO_URL, number);
				QQRes qq = new QQRes(number, logoUrl);
				res.add(qq);
			}
		}
		// 倒序
		Collections.reverse(res);
		return res;
	}

	@Override
	public String activateDWall() {
		if (getDWallStatus()) {
			return WS_URL;
		}
		post(API_URL, RequestBody.ENABLE);
		return WS_URL;
	}

	@Override
	public JSONObject getClients() {
		String rsp = post(API_URL, RequestBody.GET_CLIENTS);
		return JsonUtil.toJsonObject(rsp, JSONObject.class);
	}

	private boolean getDWallStatus() {
		String rsp = post(API_URL, RequestBody.GET_STATUS);
		return rsp.contains(ResponseKeyWord.TRUE);
	}

	private String post(String url, String body) {
		return post(url, body, true);
	}

	private String post(String url, String body, boolean retry) {
		login();
		String rsp = doHttpPost(url, COOKIE.getIfPresent(sessionKey), body);
		if (rsp.contains(ResponseKeyWord.NOT_AUTHENTICATED)) {
			if (retry) {
				// session失效重试
				COOKIE.invalidate(sessionKey);
				return post(url, body, false);
			} else {
				throw new BusinessException(ErrorCode.PINEAPPLE_NOT_AUTHENTICATED);
			}
		}
		return rsp;
	}

	private void login() {
		String sessionId = COOKIE.getIfPresent(sessionKey);
		if (StringUtils.isNotBlank(sessionId)) {
			return;
		}
		String rsp = doHttpPost(API_URL, sessionId,
				String.format(RequestBody.LOGIN, System.currentTimeMillis() / 1000));
		if (!rsp.contains(ResponseKeyWord.TRUE)) {
			COOKIE.invalidate(sessionKey);
			throw new BusinessException(ErrorCode.PINEAPPLE_LOGIN_FAILED);
		}
	}

	private String doHttpPost(String url, String sessionId, String body) {

		// 2、设置请求参数
		StringEntity entity = new StringEntity(body, WebConstant.ENCODING);

		HttpPost post = new HttpPost(url);
		if (StringUtils.isNotBlank(sessionId)) {
			post.setHeader("Cookie", sessionKey + "=" + sessionId);
		}
		post.setHeader("Content-Type", WebConstant.CONTENT_TYPE);
		post.setEntity(entity);

		// 3、执行实际调用、返回序列化后的结果
		HttpResponse response = null;
		String httpResponse = null;
		try {
			// 1、调用远程服务
			response = HttpClients.custom().build().execute(post);

			// 2、解析远程服务的返回值
			httpResponse = EntityUtils.toString(response.getEntity(), WebConstant.ENCODING);

			// 3、记录调用日志
			if (response.getStatusLine().getStatusCode() != HttpStatus.OK.value()) {
				throw new RemoteServiceException(
						String.valueOf(response.getStatusLine().getStatusCode()));
			}
		} catch (Exception e) {
			log.error("response {}", httpResponse);
			log.error("remote execute exception" + e.getMessage(), e);
			throw new RemoteServiceException();
		}
		// 4、解析结果并返回
		String[] rawRes = httpResponse.split("\n", 2);
		String rsp = rawRes[1];
		// 获取sessionId
		if (StringUtils.isBlank(sessionId) && rsp.contains(ResponseKeyWord.TRUE)) {
			for (Header header : response.getHeaders("Set-Cookie")) {
				for (HeaderElement element : header.getElements()) {
					if (element.getName().equals(sessionKey)) {
						COOKIE.put(sessionKey, element.getValue());
						break;
					}
				}
			}
		}
		return rsp;
	}

}

final class Module {

	public static final String D_WALL = "DWall";

}

final class Action {

	public static final String ENABLE = "enable";

	public static final String GET_STATUS = "getStatus";
}

final class RequestBody {

	public static final String LOGIN = "{\"system\":\"authentication\",\"action\":\"login\",\"username\":\"root\",\"password\":\"root\",\"time\":%d}";

	public static final String ENABLE = "{\"module\":\"DWall\",\"action\":\"enable\"}";

	public static final String GET_STATUS = "{\"module\":\"DWall\",\"action\":\"getStatus\"}";

	public static final String GET_CLIENTS = "{\"module\":\"Clients\",\"action\":\"getClientData\"}";

}

final class ResponseKeyWord {

	public static final String NOT_AUTHENTICATED = "Not Authenticated";

	public static final String FALSE = "false";

	public static final String TRUE = "true";

}