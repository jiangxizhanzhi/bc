/**
 * ### 代码分析 
 * #### 功能描述 
 * 该代码是一个基于 Spring 框架的 WebSocket 配置类，用于定义和注册 WebSocket 处理器。通过 `@EnableWebSocket` 和 `@Configuration` 注解，它启用了 WebSocket 功能，并实现了 `WebSocketConfigurer` 接口以完成 WebSocket 处理器的注册。 
 * --- 
 * #### 主要组成部分 
 * 1. **类定义** 
 * - 类名：`WebSocketConfig` 
 * - 继承自 `WebMvcConfigurerAdapter` 并实现 `WebSocketConfigurer` 接口。 
 * - 使用了 `@Configuration` 和 `@EnableWebSocket` 注解： 
 * - `@Configuration` 表示这是一个 Spring 配置类。 
 * - `@EnableWebSocket` 启用 WebSocket 支持。 
 * 2. **依赖注入** 
 * - `MyWebSocketHandler handler`：处理通用 WebSocket 请求。 
 * - `JwtHandShake jwtHandShake`：用于 JWT 认证握手。 
 * - `ChatWebSocketHandler chatWebSocketHandler`：专门处理聊天相关的 WebSocket 请求。 
 * - 使用了 `@Qualifier("chatWebSocketHandler")` 注解，明确指定注入的 Bean 名称为 `chatWebSocketHandler`。 
 * 3. **核心方法** 
 * - `registerWebSocketHandlers(WebSocketHandlerRegistry registry)`：实现接口方法，用于注册多个 WebSocket 处理器。 
 * - 第一个处理器 `chat` 专门处理聊天请求。
 * - 第二个处理器 `/ws*` 和第三个处理器 `/ws/**` 处理其他通用 WebSocket 请求。 
 * - 所有处理器都添加了 JWT 认证拦截器 (`jwtHandShake`) 并允许所有来源 (`setAllowedOrigins("*")`)。 
 * --- 
 * #### 代码特点 
 * - **模块化设计**：通过不同的路径（如 `/wschat` 和 `/ws*`）区分不同的 WebSocket 请求类型。
 * - **JWT 认证支持**：使用 `JwtHandShake` 对所有 WebSocket 连接进行认证。 
 * - **灵活性**：通过注解注入不同的处理器，方便扩展或替换具体实现。 
 * --- 
 * #### 总结 
 * 该代码实现了 WebSocket 的基础配置，包括处理器注册、路径映射以及 JWT 认证拦截功能。适合用于需要支持多种 WebSocket 请求场景的项目，例如聊天室或实时通信服务。 
**/
package com.cyberpeace.config.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;

/**
 * websocket配置项
 *
 * @author yinqingrong
 * @version 1.0.0
 * @date 2025/05/28
 * @see WebMvcConfigurerAdapter
 * @see WebSocketConfigurer
 * @since 1.0.0
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	/**
	 * 处理器
	 *
	 * @see MyWebSocketHandler
	 */
	@Autowired
	private MyWebSocketHandler handler;

	/**
	 * JWT 握手
	 *
	 * @see JwtHandShake
	 */
	@Autowired
	private JwtHandShake jwtHandShake;

	/**
	 * 聊天WebSocket处理器
	 *
	 * @see ChatWebSocketHandler
	 */
	@Qualifier("chatWebSocketHandler")
	@Autowired
	private ChatWebSocketHandler chatWebSocketHandler;

	/**
	 * 注册WebSocket处理器
	 *
	 * @param webSocketHandlerRegistry WebSocket处理器注册表
	 * @throws
	 * @see WebSocketHandlerRegistry
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(chatWebSocketHandler, "/ws/**/chat")
				.addInterceptors(jwtHandShake).setAllowedOrigins("*");
		webSocketHandlerRegistry.addHandler(handler, "/ws*").addInterceptors(jwtHandShake)
				.setAllowedOrigins("*");
		webSocketHandlerRegistry.addHandler(handler, "/ws/**").addInterceptors(jwtHandShake)
				.setAllowedOrigins("*");
	}
}
