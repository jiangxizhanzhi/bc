package com.cyberpeace.config.ws;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.cyberpeace.common.model.WebSocketContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Component
public class JwtHandShake implements HandshakeInterceptor {

	@Autowired
	private RemoteUserService remoteUserService;

	private final String TOKEN = "token";

	private final String ID = "business";

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
			Map<String, Object> map) throws Exception {
		String queryString = serverHttpRequest.getURI().getQuery();

		// 1、解析连接参数
		Map<String, String> parametersMap = parseParameterMap(queryString);

		// 2、权限校验
		String jwtToken = parametersMap.get(TOKEN);
		String businessId = parametersMap.get(ID);
		if (StringUtils.isBlank(jwtToken)) {
			log.error("websocket handshake fail, token is null");
			return false;
		}

		UserInfo userInfo = null;
		try {
			userInfo = remoteUserService.doAuthentication(jwtToken);
		} catch (Exception e) {
			log.error("websocket handshake fail, call remote exception, error:", e);
			return false;
		}

		if (null == userInfo || StringUtils.isBlank(userInfo.getUserId())) {
			log.error("websocket handshake fail, remote user is null");
			return false;
		}

		WebSocketContext context = WebSocketContext.builder().businessId(businessId)
				.userId(userInfo.getUserId()).build();
		map.put("key", context);

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

	}

	private Map<String, String> parseParameterMap(String queryString) {
		Map<String, String> parameterMap = Maps.newHashMap();
		String[] parameters = StringUtils.split(queryString, "&");

		for (String parameter : parameters) {
			String[] paramPair = StringUtils.split(parameter, "=");
			if (paramPair.length == 2) {
				parameterMap.put(paramPair[0], paramPair[1]);
			}
		}
		return parameterMap;
	}
}
