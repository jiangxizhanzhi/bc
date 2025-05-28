package com.cyberpeace.config.ws;

import java.util.Map;

import com.cyberpeace.common.model.CommonContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.cyberpeace.common.component.SelfDefinedWsChecker;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * JWT 握手
 *
 * @author yinqingrong
 * @version 1.0.0
 * @date 2025/05/28
 * @see HandshakeInterceptor
 * @since 1.0.0
 */
@Slf4j
@Component
public class JwtHandShake implements HandshakeInterceptor {

	private final String TOKEN = "token";

	private final String PATH = "path";

	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
			Map<String, Object> map) throws Exception {
		String queryString = serverHttpRequest.getURI().getQuery();

		String path = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest()
				.getServletPath();

		String clientIp = CommonContext.getIp();
		log.info("ws queryString is {}, ws path is {}, client ip is {}", queryString, path, clientIp);
		// ws路径统一定义，方便后期维护
		if (!WsPath.validPath(path)) {
			log.error("websocket handshake fail, path is {} invalid,"
					+ " should be declare in WsPath.java first", path);
			return false;
		}

		// 1、解析连接参数
		Map<String, String> parametersMap = parseParameterMap(queryString);

		// 2、权限校验
		String jwtToken = parametersMap.get(TOKEN);

		if (StringUtils.isBlank(jwtToken)) {
			log.error("websocket handshake fail,path is {} token is null", path);
			return false;
		}

		UserInfo userInfo = null;

		RemoteUserService remoteUserService = SpringContextUtil
				.getBeanByClass(RemoteUserService.class);

		try {
			userInfo = remoteUserService.doAuthentication(jwtToken);
		} catch (Exception e) {
			log.error("websocket handshake fail, path {} call remote exception, error:", path, e);
			return false;
		}

		if (null == userInfo || StringUtils.isBlank(userInfo.getUserId())) {
			log.error("websocket handshake fail,path {} remote user is null", path);
			return false;
		}

		parametersMap.remove(TOKEN);

		// 3、进行自定义校验
		if (!selfDefinedCheck(parametersMap, path, userInfo.getUserId())) {
			return false;
		}

		// 4、path参数最后透传，不然会传递到自定义
		parametersMap.put(PATH, path);

		WebSocketConnectionContext context = WebSocketConnectionContext.builder()
				.originParams(parametersMap).build();
		context.setUserId(userInfo.getUserId());

		// 透传websocket连接的上下文
		map.put("key", context);

		return true;
	}

	/**
	 * 逐个遍历自定义校验器
	 * @param requestParams
	 * @param servletPath
	 * @param userId
	 * @return
	 */
	private boolean selfDefinedCheck(Map<String, String> requestParams, String servletPath,
			String userId) {
		Map<String, SelfDefinedWsChecker> checkers = SpringContextUtil
				.getBeansOfType(SelfDefinedWsChecker.class);

		for (SelfDefinedWsChecker checker : checkers.values()) {
			// requestParams进行拷贝，防止有checker改变其数据
			boolean checkResult = checker.doSelfDefinedCheck(copyMap(requestParams), servletPath,
					userId);

			if (!checkResult) {
				log.error(
						"websocket handshake fail, due to checker=[{}], params=[{}], path=[{}], userId=[{}]",
						new Object[] { checker.getClass().getSimpleName(),
								mapToString(requestParams), servletPath, userId });

				return false;
			}
		}

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

	}

	public Map<String, String> parseParameterMap(String queryString) {
		Map<String, String> parameterMap = Maps.newTreeMap();
		String[] parameters = StringUtils.split(queryString, "&");

		for (String parameter : parameters) {
			String[] paramPair = StringUtils.split(parameter, "=");
			if (paramPair.length == 2) {
				parameterMap.put(paramPair[0], paramPair[1]);
			}
		}
		return parameterMap;
	}

	private Map<String, String> copyMap(Map<String, String> origin) {
		Map<String, String> copy = Maps.newTreeMap();

		for (Map.Entry<String, String> item : origin.entrySet()) {
			copy.put(item.getKey(), item.getValue());
		}

		return copy;
	}

	private String mapToString(Map<String, String> params) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> item : params.entrySet()) {
			stringBuilder.append(item.getKey()).append("=").append(item.getValue()).append("&");
		}

		return stringBuilder.toString();
	}
}
