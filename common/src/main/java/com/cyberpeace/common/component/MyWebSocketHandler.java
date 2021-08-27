package com.cyberpeace.common.component;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import com.cyberpeace.common.model.WebSocketContext;
import com.cyberpeace.common.util.JsonUtil;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

/**
 * @author yinqingrong
 */
@Slf4j
@Component
public class MyWebSocketHandler implements WebSocketHandler {

	private Map<String, WebSocketSession> userSocketSessionMap = Maps.newConcurrentMap();

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		WebSocketContext webSocketContext = (WebSocketContext) webSocketSession.getAttributes()
				.get("key");

		userSocketSessionMap.putIfAbsent(webSocketContext.toString(), webSocketSession);
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
		// 不处理前端发送的消息
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		removeSession(webSocketSession);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
		removeSession(webSocketSession);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	public void pushMessage(WebSocketContext webSocketContext, Object obj) {
		WebSocketSession session = userSocketSessionMap.get(webSocketContext.toString());

		if (session == null || session.isOpen()) {
			log.error("key=[{}] session not found!");
		}

		String jsonMessage = JsonUtil.toJsonString(obj);
		TextMessage toSendMessage = new TextMessage(jsonMessage);
		try {
			session.sendMessage(toSendMessage);
		} catch (IOException e) {
			log.error("key=[{}] send message fail, message=[{}]", webSocketContext.toString(),
					jsonMessage);
		}

		if (log.isDebugEnabled()) {
			log.debug("key=[{}], message=[{}]", webSocketContext.toString(), jsonMessage);
		}
	}

	private void removeSession(WebSocketSession webSocketSession) throws Exception {
		WebSocketContext webSocketContext = (WebSocketContext) webSocketSession.getAttributes()
				.get("key");

		if (webSocketSession.isOpen()) {
			webSocketSession.close();
		}

		Iterator<Map.Entry<String, WebSocketSession>> it = userSocketSessionMap.entrySet()
				.iterator();
		// 移除Socket会话
		while (it.hasNext()) {
			Map.Entry<String, WebSocketSession> entry = it.next();
			if (entry.getValue().getId().equals(webSocketSession.getId())) {
				userSocketSessionMap.remove(entry.getKey());

				log.info("key=[{}] removed from userSocketSessionMap", webSocketContext.toString());
				break;
			}
		}
	}
}
