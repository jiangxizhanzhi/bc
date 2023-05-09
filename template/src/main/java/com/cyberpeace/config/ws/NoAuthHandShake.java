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
 * 无身份验证握手
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
@Component
public class NoAuthHandShake extends JwtHandShake {

    private final String PATH = "path";

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest,
                                   ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler,
                                   Map<String, Object> map) {

        String queryString = serverHttpRequest.getURI().getQuery();

        String path = ((ServletServerHttpRequest) serverHttpRequest).getServletRequest()
                .getServletPath();

        if (!WsPath.validPath(path)) {
            log.error(
                    "websocket handshake fail, path invalid, should be declare in WsPath.java first");
            return false;
        }

        Map<String, String> parametersMap = parseParameterMap(queryString);

        parametersMap.put(PATH, path);

        WebSocketConnectionContext context = WebSocketConnectionContext.builder()
                .originParams(parametersMap).build();
        context.setUserId("");

        // 透传websocket连接的上下文
        map.put("key", context);

        return true;
    }
}