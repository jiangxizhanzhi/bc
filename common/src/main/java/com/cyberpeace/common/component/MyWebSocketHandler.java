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

/**
 * 我网络套接字处理程序
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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

	/**
	 * 连接建立之后
	 *
	 * @param webSocketSession web socket会话
	 * @throws Exception 异常
	 */
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

	/**
	 * 获取套接字会话映射
	 *
	 * @param identifierId 标识符id
	 * @return {@link Map}<{@link String}, {@link Set}<{@link CpWebSession}>>
	 */
	public Map<String, Set<CpWebSession>> getSocketSessionMap(String identifierId) {
		return businessSocketSessionMap.get(identifierId);
	}

	/**
	 * 处理消息
	 *
	 * @param webSocketSession web socket会话
	 * @param webSocketMessage 网络套接字信息
	 * @throws Exception 异常
	 */
	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
		// 不处理前端发送的消息
	}

	/**
	 * 处理运输错误
	 *
	 * @param webSocketSession web socket会话
	 * @param throwable        throwable
	 * @throws Exception 异常
	 */
	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	/**
	 * 连接关闭后
	 *
	 * @param webSocketSession web socket会话
	 * @param closeStatus      关闭状态
	 * @throws Exception 异常
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	/**
	 * 支持部分信息
	 *
	 * @return boolean
	 */
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
	 * 推送消息
	 * 向用户推送websocket消息
	 *
	 * @param message   待推送的websocket消息
	 * @param toAllUser 向该业务的所有用户推送消息。toAllUser目前就实训的共享场景用到
	 */
	public void pushMessage(WebSocketMessageContext message, boolean toAllUser) {
		String businessId = message.getMessageIdentifier().getIdentifierId();
		String userId = message.getMessageIdentifier().getUserId();

		Map<String, Set<CpWebSession>> userSocketSessionMap = businessSocketSessionMap
				.get(businessId);

		// 指定某些人
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
	 * 把信息给用户
	 * 向用户推送websocket消息
	 *
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

	/**
	 * 推送会话
	 *
	 * @param message    消息
	 * @param sessions   会话
	 * @param userId     用户id
	 * @param businessId 业务标识
	 */
	protected void pushToSessions(WebSocketMessageContext message, Set<CpWebSession> sessions,
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
				continue;
			}

			String jsonMessage = JsonUtil.toJsonString(message.getData());
			TextMessage toSendMessage = new TextMessage(jsonMessage);
			try {
				userSession.sendMessage(toSendMessage);
			} catch (IOException e) {
				log.error("businessId=[{}], userId=[{}], message=[{}] send fail!", businessId,
						userId, jsonMessage);
			}

			if (log.isDebugEnabled()) {
				log.debug("businessId=[{}], userId=[{}], message=[{}] send fail!", businessId,
						userId, jsonMessage);
			}
		}
		// remove closed sessions
		for (CpWebSession userSession : toBeRemoved) {
			removeSession(userSession);
		}
	}

	/**
	 * 发送聊天消息
	 *
	 * @param identifierId 标识符id
	 * @param userId       用户id
	 * @param chatMessage  聊天信息
	 * @throws IOException ioexception
	 */
	public void sendChatMessage(String identifierId, String userId, ChatMessage chatMessage)
			throws IOException {

		// 发送chat消息
		Set<CpWebSession> userSessions = getUserSession(identifierId, userId);
		TextMessage toSendMessage = new TextMessage(JsonUtil.toJsonString(chatMessage));
		if (userSessions == null) {
			log.warn("[wsChat]user {} cannot receive the message without a link", userId);
		} else {
			for (CpWebSession userSession : userSessions) {
				userSession.sendMessage(toSendMessage);
			}
		}
	}

	/**
	 * 和
	 */
	private final String AND = "&";

	/**
	 * 推送消息指示板
	 *
	 * @param message     消息
	 * @param dashboardId 仪表板id
	 */
	public <T> void pushMessageToDashboard(WebSocketMessageContext message, String dashboardId) {
		String path = message.getMessageIdentifier().getPath();
		businessSocketSessionMap.forEach((key, value) -> {
			if (match(key, dashboardId, path)) {
				value.forEach((userId, sessionSet) -> {
					pushToSessions(message, sessionSet, userId, dashboardId + AND + path);
				});
			}
		});
	}

	/**
	 * 匹配
	 *
	 * @param key         关键
	 * @param dashboardId 仪表板id
	 * @param path        路径
	 * @return {@link Boolean}
	 */
	private Boolean match(String key, String dashboardId, String path) {
		if (key.contains(path + AND) || key.contains(AND + path)) {
			return key.contains(dashboardId + AND) || key.contains(AND + dashboardId);
		}
		return false;
	}

	/**
	 * 得到用户会话
	 *
	 * @param identifierId 标识符id
	 * @param userId       用户id
	 * @return {@link Set}<{@link CpWebSession}>
	 */
	private Set<CpWebSession> getUserSession(String identifierId, String userId) {
		Map<String, Set<CpWebSession>> userSocketSessionMap = getSocketSessionMap(identifierId);

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			log.warn("[wsChat]not find userSocketSessionMap by identifierId{}", identifierId);
			return null;
		}

		Set<CpWebSession> webSocketSessions = userSocketSessionMap.get(userId);
		if (null == webSocketSessions || webSocketSessions.isEmpty()) {
			log.warn("[wsChat]not find webSocketSessions by userId{}", userId);
			return null;
		}
		return webSocketSessions;
	}

	/**
	 * 删除会话
	 *
	 * @param webSocketSession web socket会话
	 */
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
