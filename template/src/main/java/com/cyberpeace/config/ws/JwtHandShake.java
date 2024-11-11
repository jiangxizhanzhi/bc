package com.cyberpeace.config.ws;

import com.cyberpeace.common.component.SelfDefinedWsChecker;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author yinqingrong
 */
@Slf4j
@Component
public class JwtHandShake implements HandshakeInterceptor {

    // 定义token常量，用于后续的认证操作
	private final String TOKEN = "token";

    // 定义path常量，用于指定资源路径
	private final String PATH = "path";


	/**
	 * 在WebSocket握手之前执行的处理方法
	 *
	 * @param serverHttpRequest 客户端请求对象
	 * @param serverHttpResponse 服务端响应对象
	 * @param webSocketHandler WebSocket处理器
	 * @param map 用于存储握手过程中产生的属性
	 * @return 如果握手成功则返回true，否则返回false
	 * @throws Exception 如果握手过程中发生异常则抛出
	 */
	@Override
	public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
			ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
			Map<String, Object> map) throws Exception {
		// 获取请求的查询字符串
		String queryString = serverHttpRequest.getURI().getQuery();

		// 获取请求的路径
		String path = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest()
				.getServletPath();

		// 获取客户端IP地址
		String clientIp = CommonContext.getIp();
		// 记录握手请求的日志信息
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

		// 获取远程用户服务实例
		RemoteUserService remoteUserService = SpringContextUtil
				.getBeanByClass(RemoteUserService.class);

		try {
			// 远程调用用户服务进行身份验证
			userInfo = remoteUserService.doAuthentication(jwtToken);
		} catch (Exception e) {
			log.error("websocket handshake fail, path {} call remote exception, error:", path, e);
			return false;
		}

		if (null == userInfo || StringUtils.isBlank(userInfo.getUserId())) {
			log.error("websocket handshake fail,path {} remote user is null", path);
			return false;
		}

		// 移除令牌参数
		parametersMap.remove(TOKEN);

		// 3、进行自定义校验
		if (!selfDefinedCheck(parametersMap, path, userInfo.getUserId())) {
			return false;
		}

		// 4、path参数最后透传，不然会传递到自定义
		parametersMap.put(PATH, path);

		// 构建WebSocket连接上下文对象
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
	 * @param requestParams 请求参数
	 * @param servletPath 请求路径
	 * @param userId 用户ID
	 * @return 如果所有校验器都通过校验则返回true，否则返回false
	 */
	private boolean selfDefinedCheck(Map<String, String> requestParams, String servletPath,
			String userId) {
		// 获取所有自定义校验器
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
	 * 在WebSocket握手完成后调用的方法
	 * 该方法可以用于在握手成功后执行一些定制的操作，例如日志记录或资源清理
	 *
	 * @param serverHttpRequest  服务器HTTP请求对象，可用于获取请求相关信息
	 * @param serverHttpResponse 服务器HTTP响应对象，可用于定制响应内容
	 * @param webSocketHandler   处理WebSocket消息的处理器
	 * @param e                  如果握手过程中发生异常，该参数将提供异常信息
	 */
	@Override
	public void afterHandshake(ServerHttpRequest serverHttpRequest,
	        ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Exception e) {

	}

	/**
	 * 解析查询字符串参数并将其转换为映射
	 * 该方法接收一个查询字符串，如"a=1&b=2"，并返回一个映射，键为参数名，值为参数值
	 *
	 * @param queryString 查询字符串，通常来自URL的参数部分
	 * @return 包含查询参数的映射，键为参数名，值为参数值
	 */
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
	 * 复制一个字符串到字符串的映射
	 * 为了防止原始地图在后续操作中被修改，从而影响原始数据的完整性
	 *
	 *
	 * @param origin 原始的映射，包含需要被复制的键值对
	 * @return 返回一个新的映射，包含与原始映射相同键值对
	 */
	private Map<String, String> copyMap(Map<String, String> origin) {
	    // 创建一个新的TreeMap实例，用于存储复制的键值对
	    Map<String, String> copy = Maps.newTreeMap();

	    // 遍历原始映射中的每一个键值对，并将其复制到新的映射中
	    for (Map.Entry<String, String> item : origin.entrySet()) {
	        copy.put(item.getKey(), item.getValue());
	    }

	    // 返回复制后的映射
	    return copy;
	}

	/**
	 * 将一个字符串到字符串的映射转换为字符串
	 * 这在需要将参数拼接成查询字符串（比如构造URL）时非常有用
	 * 为什么需要这个转换？在很多场景下，需要将参数以“key=value&key=value”形式拼接成字符串，例如构造HTTP请求的查询参数
	 *
	 * @param params 映射，包含需要被转换为字符串的键值对
	 * @return 返回一个字符串，包含所有键值对，格式为“key=value&key=value...”
	 */
	private String mapToString(Map<String, String> params) {
	    // 创建StringBuilder实例，用于拼接字符串
	    StringBuilder stringBuilder = new StringBuilder();
	    // 遍历映射中的每一个键值对，将其按照“key=value&”的格式拼接到字符串中
	    for (Map.Entry<String, String> item : params.entrySet()) {
	        stringBuilder.append(item.getKey()).append("=").append(item.getValue()).append("&");
	    }

	    // 返回拼接后的字符串
	    return stringBuilder.toString();
	}
}
