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
 * 我 Web 套接字处理程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WebSocketHandler
 * @since 1.0.0
 */
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个自定义的 `WebSocketHandler`，用于管理 WebSocket 连接和消息推送。主要功能包括： 
 * 1. 管理 WebSocket 会话（Session）。 
 * 2. 根据业务标识符（identifierId）和用户标识符（userId）存储和检索会话。 
 * 3. 支持向单个用户或所有用户推送消息。 
 * 4. 处理连接建立、关闭以及错误事件。 
 * --- 
 * #### 主要类与结构 
 * 1. **`businessSocketSessionMap`** 
 * - 类型：`Map<String, Map<String, Set<CpWebSession>>>` 
 * - 功能：用于存储 WebSocket 会话。外层键为业务标识符（identifierId），内层键为用户标识符（userId），值为用户对应的会话集合（`Set<CpWebSession>`）。 
 * 2. **`CpWebSession`** 
 * - 表示单个 WebSocket 会话对象，包含会话的基本信息和方法。 
 * 3. **`WebSocketMessageContext`** 
 * - 表示 WebSocket 消息上下文，包含消息内容和相关标识符（如业务ID、用户ID等）。 
 * 4. **`JsonUtil`** 
 * - 用于将 Java 对象序列化为 JSON 字符串。 
 * 5. **日志记录** 
 * - 使用 `lombok.extern.slf4j.Slf4j` 提供的日志记录功能。 
 * --- 
 * #### 方法解析 
 * 1. **`afterConnectionEstablished`** 
 * - 当新的 WebSocket 连接建立时触发。 
 * - 将新会话添加到 `businessSocketSessionMap` 中，按业务标识符和用户标识符组织。 
 * 2. **`handleMessage`** 
 * - 未处理前端发送的消息（注释中说明不处理）。 
 * 3. **`handleTransportError` 和 `afterConnectionClosed`** 
 * - 在连接发生错误或关闭时调用，移除失效的会话。 
 * 4. **`pushMessage` 和 `pushMessageToUsers`** 
 * - 分别支持向所有用户或指定用户推送消息。 
 * - 遍历 `businessSocketSessionMap`，找到目标用户并发送消息。 
 * 5. **`sendChatMessage`** 
 * - 专门用于发送聊天消息。 
 * - 根据业务标识符和用户标识符获取会话集合，并逐一发送消息。 
 * 6. **`getUserSession`** 
 * - 根据业务标识符和用户标识符查找对应的会话集合。 
 * 7. **`removeSession`** 
 * - 移除失效的会话。 
 * - 更新 `businessSocketSessionMap`，确保数据一致性。 
 * --- 
 * #### 特点与优势 
 * 1. **分层存储** 
 * - 使用多级嵌套的 `Map` 结构，便于根据业务标识符和用户标识符快速定位会话。 
 * 2. **线程安全** 
 * - 使用了 `Maps.newConcurrentMap()` 和 `Sets.newHashSet()`，确保并发环境下的安全性。 
 * 3. **灵活的消息推送机制** 
 * - 支持向单个用户或所有用户推送消息，适应不同的业务需求。 
 * 4. **日志记录** 
 * - 提供详细的日志记录，便于排查问题。 
 * --- 
 * #### 缺点与改进建议 
 * 1. **内存占用** 
 * - `businessSocketSessionMap` 存储了大量会话数据，可能在高并发情况下占用较多内存。 
 * 2. **异常处理** 
 * - 某些地方仅记录警告或错误日志，但未采取进一步措施（如重试机制）。 
 * 3. **代码复用性** 
 * - 若多个地方需要类似的功能，可考虑提取通用逻辑。 
 * --- 
 * #### 总结 
 * 该代码实现了完整的 WebSocket 会话管理和消息推送功能，设计合理且具有良好的扩展性。通过分层存储和线程安全措施，能够满足大多数业务场景的需求。但在高并发环境下需注意内存使用情况，并进一步优化异常处理逻辑。 
**/
@Slf4j
@Component
@Primary
public class MyWebSocketHandler implements WebSocketHandler {

	/**
	 * 1、key=业务数据生成编号。
	 * 2、value
	 * 2.1、 value的key=该业务场景下的用户id
	 * 2.2、value的value=该业务场景下指定用户的session
	 *
	 * @see Map<String, Map<String, Set<CpWebSession>>>
	 */
	private Map<String, Map<String, Set<CpWebSession>>> businessSocketSessionMap = Maps
			.newConcurrentMap();

	/**
	 * 建立连接后
	 *
	 * @param webSocketSession Web 套接字会话
	 * @throws Exception 例外
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
	 * 获取插座会期地图
	 *
	 * @param identifierId 标识符id
	 * @return {@code Map<String, Set<CpWebSession>> }
	 */
	public Map<String, Set<CpWebSession>> getSocketSessionMap(String identifierId) {
		return businessSocketSessionMap.get(identifierId);
	}

	/**
	 * handle message （处理消息）
	 *
	 * @param webSocketSession Web 套接字会话
	 * @param webSocketMessage Web 套接字消息
	 * @throws Exception 例外
	 */
	@Override
	public void handleMessage(WebSocketSession webSocketSession,
			WebSocketMessage<?> webSocketMessage) throws Exception {
		// 不处理前端发送的消息
	}

	/**
	 * 处理传输错误
	 *
	 * @param webSocketSession Web 套接字会话
	 * @param throwable        可投掷
	 * @throws Exception 例外
	 */
	@Override
	public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	/**
	 * 连接关闭后
	 *
	 * @param webSocketSession Web 套接字会话
	 * @param closeStatus      关闭状态
	 * @throws Exception 例外
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus)
			throws Exception {
		removeSession(new CpWebSession(webSocketSession));
	}

	/**
	 * 支持部分消息
	 *
	 * @return boolean
	 */
	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	/**
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
	 * 推送到会话
	 *
	 * @param message    消息
	 * @param sessions   会话
	 * @param userId     用户id
	 * @param businessId 业务id
	 */
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
				continue;
			}

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
	 * @param chatMessage  聊天消息
	 * @throws IOException io异常
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
	 * 获取用户会期
	 *
	 * @param identifierId 标识符id
	 * @param userId       用户id
	 * @return {@code Set<CpWebSession> }
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
	 * @param webSocketSession Web 套接字会话
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

