package com.cyberpeace.config.ws;

import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;

import java.util.Map;

/**
 * NoAuthHandShake 类继承自 JwtHandShake，用于处理 WebSocket 握手请求。
 * 它主要负责在握手前验证和解析请求路径和参数，并将必要的信息传递给 WebSocket 连接上下文。
 */
@Slf4j
@Component
public class NoAuthHandShake extends JwtHandShake {

    // 定义一个常量 PATH，用于在参数映射中标识路径
    private final String PATH = "path";


    /**
     * 在握手前处理请求，验证路径有效性并将请求参数传递给 WebSocket 连接上下文。
     *
     * @param serverHttpRequest 服务器 HTTP 请求对象，用于获取请求 URI 和路径信息
     * @param serverHttpResponse 服务器 HTTP 响应对象
     * @param webSocketHandler WebSocket 处理器对象
     * @param map 用于传递握手上下文信息的映射
     * @return boolean 表示握手是否可以继续进行。返回 true 表示握手成功，false 表示握手失败
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) {

        // 获取请求 URI 的查询字符串
        String queryString = serverHttpRequest.getURI().getQuery();

        // 获取请求的路径
        String path = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest()
                .getServletPath();

        // 验证路径是否有效，如果无效则记录错误日志并返回 false
        if (!WsPath.validPath(path)) {
            log.error(
                    "websocket handshake fail, path invalid, should be declare in WsPath.java first");
            return false;
        }

        // 解析查询字符串参数为映射
        Map<String, String> parametersMap = parseParameterMap(queryString);

        // 将路径添加到参数映射中
        parametersMap.put(PATH, path);

        // 构建 WebSocket 连接上下文，并设置解析后的参数映射
        WebSocketConnectionContext context = WebSocketConnectionContext.builder()
                .originParams(parametersMap).build();
        // 设置用户 ID 为空字符串，表示未认证用户
        context.setUserId("");

        // 透传websocket连接的上下文
        map.put("key", context);

        // 返回 true 表示握手成功
        return true;
    }
}