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
 * @author yinqingrong@see WebMvcConfigurerAdapter
 * @version 1.0.0
 * @date 2024/11/19
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
