package com.cyberpeace.config.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.cyberpeace.common.component.MyWebSocketHandler;

/**
 * websocket配置项
 * 
 * @author yinqingrong
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	@Autowired
	private MyWebSocketHandler handler;

	@Autowired
	private JwtHandShake jwtHandShake;

	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		webSocketHandlerRegistry.addHandler(handler, "/ws").addInterceptors(jwtHandShake)
				.setAllowedOrigins("*");
	}
}
