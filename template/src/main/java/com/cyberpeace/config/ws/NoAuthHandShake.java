/**
 * ### 代码分析 
 * #### 1. **功能概述** 
 * 该代码定义了一个名为 `NoAuthHandShake` 的类，继承自 `JwtHandShake`。它的主要功能是在 WebSocket 握手之前进行一些预处理操作，包括路径校验、参数解析以及将上下文信息存储到 `Map` 中以供后续使用。 
 * --- 
 * #### 2. **关键点分析** 
 * - **类的继承与注解**： 
 * - 类 `NoAuthHandShake` 继承自 `JwtHandShake`，表明它可能基于 JWT（JSON Web Token）实现某些功能。 
 * - 使用了 `@Slf4j` 注解，方便日志记录。 
 * - 使用了 `@Component` 注解，表明这是一个 Spring 容器中的 Bean。 
 * - **属性定义**： 
 * - 定义了一个常量 `PATH`，用于标识路径信息。 
 * - **核心方法：`beforeHandshake`** 
 * 1. **获取请求信息**： 
 * - 获取请求路径 (`path`) 和查询字符串 (`queryString`)。 
 * - 确保路径在 `WsPath` 中是合法的。 
 * 2. **参数解析**： 
 * - 将查询字符串解析为键值对形式的 `parametersMap`。 
 * - 将当前路径 (`path`) 添加到 `parametersMap` 中。 
 * 3. **创建 WebSocket 连接上下文**： 
 * - 构建一个 `WebSocketConnectionContext` 对象，并将其与解析后的参数绑定。 
 * - 设置用户 ID 为空字符串，表示未进行身份认证。 
 * 4. **透传上下文**： 
 * - 将构建好的 `WebSocketConnectionContext` 存入 `map`，通过键 `"key"` 提供给后续处理。 
 * 5. **返回值**： 
 * - 如果路径无效，则返回 `false` 并记录错误日志；否则返回 `true`。 
 * --- 
 * #### 3. **设计意图** 
 * - **无身份认证握手**： 
 * - 该类适用于不需要身份认证的 WebSocket 握手场景。 
 * - 在握手之前，仅对路径的有效性进行检查，并为后续的 WebSocket 处理提供必要的上下文信息。 
 * - **灵活性**： 
 * - 通过 `map` 透传上下文信息，使得 WebSocket 处理器可以轻松访问这些数据。 
 * --- 
 * #### 4. **潜在问题或改进建议** 
 * - **日志级别**： 
 * - 当前日志级别为 `ERROR`，但路径无效可能是正常情况（例如客户端尝试访问不存在的路径）。建议改为 `WARN` 或 `INFO`。 
 * - **代码复用性**： 
 * - 当前代码中硬编码了一些逻辑（如 `PATH` 和用户 ID 的默认值），可以考虑将其抽象为配置项或动态参数。 
 * --- 
 * #### 5. **总结** 
 * `NoAuthHandShake` 是一个简单的 WebSocket 握手前置处理器，主要负责路径校验和上下文初始化。代码结构清晰，逻辑简单，适合无身份认证的场景。但可以通过优化日志级别和增强灵活性进一步提升代码质量。 
**/
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
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see JwtHandShake
 * @since 1.0.0
 */
@Slf4j
@Component
public class NoAuthHandShake extends JwtHandShake {

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
     * @throws
     * @see ServerHttpRequest
     * @see ServerHttpResponse
     * @see WebSocketHandler
     * @see Map<String, Object>
     * @see boolean
     */
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