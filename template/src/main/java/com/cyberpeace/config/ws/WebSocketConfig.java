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
 * 本类主要用于配置websocket的相关参数和处理器，以实现基于websocket的即时通讯功能
 * 它通过扫描特定URL模式来初始化websocket处理器，并配置握手拦截器以增强安全性
 *
 * @author yinqingrong
 */
@EnableWebSocket
@Configuration
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

	// 注入自定义的websocket处理器
	@Autowired
	private MyWebSocketHandler handler;

	// 注入自定义的握手拦截器，用于处理websocket连接握手时的JWT验证
	@Autowired
	private JwtHandShake jwtHandShake;

	// 注入专门处理聊天功能的websocket处理器
	@Qualifier("chatWebSocketHandler")
	@Autowired
	private ChatWebSocketHandler chatWebSocketHandler;

	/**
	 * 注册websocket处理器和配置拦截器
	 *
	 * 本方法主要用于注册websocket处理器并配置相应的拦截器和允许的源，以支持websocket连接的建立和安全握手
	 *
	 * @param webSocketHandlerRegistry websocket处理器的注册器，用于注册不同的websocket处理器和配置参数
	 */
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
		// 注册聊天功能的websocket处理器，处理以"/ws/**/chat"开头的websocket请求
		// 添加握手拦截器以进行安全验证，并允许所有源的跨域请求
		webSocketHandlerRegistry.addHandler(chatWebSocketHandler, "/ws/**/chat")
				.addInterceptors(jwtHandShake).setAllowedOrigins("*");

		// 注册通用websocket处理器，处理以"/ws*"开头的websocket请求
		// 同样添加握手拦截器并允许所有源的跨域请求
		webSocketHandlerRegistry.addHandler(handler, "/ws*").addInterceptors(jwtHandShake)
				.setAllowedOrigins("*");

		// 再次注册通用websocket处理器，处理以"/ws/**"开头的所有子路径websocket请求
		// 这种配置方式提供了更广泛的路径匹配，同样添加握手拦截器并允许所有源的跨域请求
		webSocketHandlerRegistry.addHandler(handler, "/ws/**").addInterceptors(jwtHandShake)
				.setAllowedOrigins("*");
	}
}

