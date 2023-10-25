package com.cyberpeace.common.component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import com.cyberpeace.common.model.ws.ChatMessage;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Primary
public class MyWebSocketHandler implements WebSocketHandler {

	/**
	 * 1、key=业务数据生成编号。
	 * 2、value
	 * 2.1、 value的key=该业务场景下的用户id
	 * 2.2、value的value=该业务场景下指定用户的session
	 */
	private Map<String, Map<String, Set<CpWebSession>>> businessSocketSessionMap = Maps
			.newConcurrentMap();

	@Override
	public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
		WebSocketConnectionContext webSocketContext = (WebSocketConnectionContext) webSocketSession
				.getAttributes().get("key");

		businessSocketSessionMap.putIfAbsent(webSocketContext.getIdentifierId(),
				Maps.newConcurrentMap());

		Map<String, Set<CpWebSession>> userSocketSessionMap = businessSocketSessionMap
				.get(webSocketContext.getIdentifierId());

		userSocketSessionMap.putIfAbsent(webSocketContext.getUserId(), Sets.newHashSet());
		userSocketSessionMap.get(webSocketContext.getUserId())
				.add(new CpWebSession(webSocketSession));
	}

	public Map<String, Set<CpWebSession>> getSocketSessionMap(String identifierId) {
		return businessSocketSessionMap.get(identifierId);
	}

	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
		// 不处理前端发送的消息
	}

	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 向用户推送websocket消息
	 * @param message 待推送的websocket消息
	 * @param toAllUser 向该业务的所有用户推送消息。toAllUser目前就实训的共享场景用到
	 */
	public void pushMessage(WebSocketMessageContext message, boolean toAllUser) {
		String businessId = message.getMessageIdentifier().getIdentifierId();
		String userId = message.getMessageIdentifier().getUserId();

		Map<String, Set<CpWebSession>> userSocketSessionMap = businessSocketSessionMap
				.get(businessId);

		if (!toAllUser && StringUtils.isBlank(userId)) {
			log.warn("not toAllUser but userId is null!");
			return;
		}

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			log.warn("businessId=[{}], userId=[{}] send ws message fail! userSessionMap maybe null",
					businessId, userId);
			return;
		}

		// 实际消息发送
		for (Map.Entry<String, Set<CpWebSession>> item : userSocketSessionMap.entrySet()) {
			String sessionUserId = item.getKey();

			if (!toAllUser && !StringUtils.equals(sessionUserId, userId)) {
				continue;
			}

			Set<CpWebSession> sessions = item.getValue();

			pushToSessions(message, sessions, userId, businessId);
		}
	}

	/**
	 * 向用户推送websocket消息
	 * @param message 待推送的websocket消息
	 * @param userIds 向特定用户推送消息。
	 */
	public void pushMessageToUsers(WebSocketMessageContext message, List<String> userIds) {
		String businessId = message.getMessageIdentifier().getIdentifierId();

		Map<String, Set<CpWebSession>> userSocketSessionMap = businessSocketSessionMap
				.get(businessId);

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			log.warn("businessId=[{}], send ws message fail! userSessionMap maybe null",
					businessId);
			return;
		}

		// 实际消息发送
		for (String userId : userIds) {
			Set<CpWebSession> sessions = userSocketSessionMap.get(userId);
			if (sessions != null && !sessions.isEmpty()) {
				pushToSessions(message, sessions, userId, businessId);
			}
		}
	}

	private void pushToSessions(WebSocketMessageContext message, Set<CpWebSession> sessions,
			String userId, String businessId) {
		List<CpWebSession> toBeRemoved = Lists.newArrayList();
		for (CpWebSession userSession : sessions) {
			if (null == userSession) {
				log.warn("businessId=[{}], userId=[{}] send ws message fail! userSession is null!",
						businessId, userId);
				continue;
			}

			if (!userSession.isOpen()) {
				log.warn(
						"businessId=[{}], userId=[{}] send ws message fail! userSession is closed!",
						businessId, userId);
				toBeRemoved.add(userSession);
			} else {
				String jsonMessage = JsonUtil.toJsonString(message.getData());
				TextMessage toSendMessage = new TextMessage(jsonMessage);
				try {
					userSession.sendMessage(toSendMessage);
				} catch (IOException e) {
					log.error("businessId=[{}], userId=[{}], message=[{}] send fail!",
							businessId, userId, jsonMessage);
				}

				if (log.isDebugEnabled()) {
					log.debug("businessId=[{}], userId=[{}], message=[{}] send fail!",
							businessId, userId, jsonMessage);
				}
			}


		}
		// remove closed sessions
		for (CpWebSession userSession : toBeRemoved) {
			removeSession(userSession);
		}
	}

	public void sendChatMessage(String identifierId, String userId, ChatMessage chatMessage)
			throws IOException {

		// 发送chat消息
		Set<CpWebSession> userSessions = getUserSession(identifierId, userId);
		TextMessage toSendMessage = new TextMessage(JsonUtil.toJsonString(chatMessage));
		if (null == userSessions || userSessions.isEmpty()) {
			log.warn("[wsChat]user {} cannot receive the message without a link", userId);
		} else {
			for (CpWebSession userSession : userSessions) {
				userSession.sendMessage(toSendMessage);
			}
		}
	}

	private Set<CpWebSession> getUserSession(String identifierId, String userId) {
		Map<String, Set<CpWebSession>> userSocketSessionMap = getSocketSessionMap(identifierId);

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			log.warn("[wsChat]not find userSocketSessionMap by identifierId{}", identifierId);
			return Sets.newHashSet();
		}

		Set<CpWebSession> webSocketSessions = userSocketSessionMap.get(userId);
		if (null == webSocketSessions || webSocketSessions.isEmpty()) {
			log.warn("[wsChat]not find webSocketSessions by userId{}", userId);
			return Sets.newHashSet();
		}
		return webSocketSessions;
	}

	private void removeSession(CpWebSession webSocketSession) {
		if (webSocketSession.isOpen()) {
			try {
				webSocketSession.close();
			} catch (Exception e) {
				log.error("remove webSocketSession error, e:", e);
			}
		}

		WebSocketConnectionContext connectionContext = (WebSocketConnectionContext) webSocketSession
				.getAttributes().get("key");

		if (null == connectionContext || MapUtils.isEmpty(businessSocketSessionMap)) {
			return;
		}

		String businessId = connectionContext.getIdentifierId();

		Map<String, Set<CpWebSession>> userSocketSessionMap = businessSocketSessionMap
				.get(businessId);

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			return;
		}

		Set<CpWebSession> userSessions = userSocketSessionMap.get(connectionContext.getUserId());
		if (null == userSessions || userSessions.isEmpty()) {
			return;
		}

		for (CpWebSession userSession : userSessions) {
			if (StringUtils.equals(userSession.getId(), webSocketSession.getId())) {
				userSessions.remove(userSession);
				break;
			}
		}

		if (userSessions.isEmpty()) {
			userSocketSessionMap.remove(connectionContext.getUserId());
		}

		log.info("businessId=[{}], userId=[{}] removed from socketSessionMap",
				connectionContext.getIdentifierId(), connectionContext.getUserId());

	}
}
