/**
 * ### 代码分析 
 * #### 功能描述 
 * 该代码实现了一个基于 JWT 的 WebSocket 握手拦截器 `JwtHandShake`，用于在 WebSocket 连接建立之前对客户端请求进行验证和处理。核心功能包括： 
 * 1. 解析 URL 参数。 
 * 2. 验证 JWT Token。 
 * 3. 自定义权限校验。 
 * 4. 将用户信息和连接上下文存储到握手上下文中。 
 * --- 
 * #### 关键点解析 
 * 1. **类与接口实现** 
 * - 实现了 `HandshakeInterceptor` 接口，提供了两个方法： 
 * - `beforeHandshake`：在握手前执行，完成所有校验和上下文构建。 
 * - `afterHandshake`：在握手完成后执行，此处为空实现。 
 * 2. **JWT 校验流程** 
 * - **路径验证**：通过 `WsPath.validPath` 检查请求路径是否合法。 
 * - **参数解析**：通过 `parseParameterMap` 方法将查询字符串解析为键值对。 
 * - **Token 验证**：从参数中提取 `token`，调用远程服务 `RemoteUserService` 验证 JWT。 
 * - **自定义校验**：通过 `SelfDefinedWsChecker` 接口实现多个自定义校验逻辑，并逐一检查。 
 * 3. **上下文管理** 
 * - 使用 `WebSocketConnectionContext` 存储连接上下文信息（如用户 ID 和原始参数）。 
 * - 将上下文对象存入 `Map<String, Object>` 中，供后续 WebSocket 通信使用。 
 * 4. **工具方法** 
 * - `copyMap`：深拷贝参数映射，避免自定义校验器修改原始数据。 
 * - `mapToString`：将参数映射转换为字符串，便于日志记录。 
 * 5. **日志记录** 
 * - 使用 `slf4j` 记录关键操作的日志，包括握手失败的原因（如路径非法、Token 缺失等）。 
 * 6. **依赖注入** 
 * - 使用 `SpringContextUtil` 获取 `RemoteUserService` 和 `SelfDefinedWsChecker` 等 Bean。 
 * --- 
 * #### 总结 
 * 该代码实现了 WebSocket 握手过程中的安全性和可扩展性，具有以下优点： 
 * - 分层设计，职责清晰（参数解析、Token 验证、自定义校验）。 
 * - 支持灵活扩展，可通过实现 `SelfDefinedWsChecker` 接口添加更多校验逻辑。 
 * - 日志记录完善，便于排查问题。 
 * 潜在改进点： 
 * - 可以考虑将部分通用逻辑（如参数解析、日志格式化）封装为工具类，提高代码复用性。 
**/
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

	/**
	 * 令 牌
	 *
	 * @see String
	 */
	private final String TOKEN = "token";

	/**
	 * 路径
	 *
	 * @see String
	 */
	private final String PATH = "path";

	/**
	 * 握手前
	 *
	 * @param serverHttpRequest  服务器 HTTP 请求
	 * @param serverHttpResponse 服务器 HTTP 响应
	 * @param webSocketHandler   WebSocket处理器
	 * @param map                映射
	 * @return boolean
	 * @throws @throws Exception 例外
	 * @see ServerHttpRequest
	 * @see ServerHttpResponse
	 * @see WebSocketHandler
	 * @see Map<String, Object>
	 * @see boolean
	 */
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
	 *
	 * @param requestParams
	 * @param servletPath
	 * @param userId
	 * @return
	 * @throws
	 * @see Map<String, String>
	 * @see String
	 * @see String
	 * @see boolean
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

	/**
	 * 握手后
	 *
	 * @param serverHttpRequest  服务器 HTTP 请求
	 * @param serverHttpResponse 服务器 HTTP 响应
	 * @param webSocketHandler   WebSocket处理器
	 * @param e                  e
	 * @throws
	 * @see ServerHttpRequest
	 * @see ServerHttpResponse
	 * @see WebSocketHandler
	 * @see Exception
	 */
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

	/**
	 * 复制映射
	 *
	 * @param origin 起源
	 * @return map<string 、 string>
	 * @throws
	 * @see Map<String, String>
	 * @see Map<String, String>
	 */
	private Map<String, String> copyMap(Map<String, String> origin) {
		Map<String, String> copy = Maps.newTreeMap();

		for (Map.Entry<String, String> item : origin.entrySet()) {
			copy.put(item.getKey(), item.getValue());
		}

		return copy;
	}

	/**
	 * 映射自字符串
	 *
	 * @param params 参数
	 * @return 字符串
	 * @throws
	 * @see Map<String, String>
	 * @see String
	 */
	private String mapToString(Map<String, String> params) {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> item : params.entrySet()) {
			stringBuilder.append(item.getKey()).append("=").append(item.getValue()).append("&");
		}

		return stringBuilder.toString();
	}
}
