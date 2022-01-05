package com.cyberpeace.common.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.ws.*;
import com.cyberpeace.common.util.JsonUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
public class ChatWebSocketHandler implements WebSocketHandler {

	private static Map<String, Map<String, Set<WebSocketSession>>> businessSocketSessionMap = Maps
			.newConcurrentMap();


	private static Map<Integer, ChatWebSocketHandler> register = Maps
			.newHashMap();

	public Integer declareMessageType(){
		return -1;
	};

	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			throw new Error("[wsChat]declareMessageType=[" + declareMessageType() + "] repeat");
		}

		register.put(declareMessageType(), this);
	}

	public  List<String> getGroup(String roomId, String groupId){
		return null;
	};

	public  List<String> getFriends(String roomId, String userId){
		return null;
	};


	public  Boolean checkInBusiness(WebSocketConnectionContext webSocketContext){
		return true;
	};


	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		WebSocketConnectionContext webSocketContext = (WebSocketConnectionContext) session
				.getAttributes().get("key");

		businessSocketSessionMap.putIfAbsent(webSocketContext.getIdentifierId(),
				Maps.newConcurrentMap());

		Map<String, Set<WebSocketSession>> userSocketSessionMap = businessSocketSessionMap
				.get(webSocketContext.getIdentifierId());

		userSocketSessionMap.putIfAbsent(webSocketContext.getUserId(), Sets.newHashSet());
		userSocketSessionMap.get(webSocketContext.getUserId()).add(session);
		String businessType = webSocketContext.getOriginParams().get("business_id");
		if (businessType == null) {
			log.error("[wsChat]current link parameter business_id could not be found");
			throw new Error(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		ChatWebSocketHandler handler = getHandler(webSocketContext);
		if (!handler.checkInBusiness(webSocketContext)){
			log.error("[wsChat]current user {} is not in the chat service {}", webSocketContext.getUserId(),
					webSocketContext.getOriginParams());
			throw new Error(ErrorCode.METHOD_ARG_NOT_VALID);
		};

	}

	private ChatWebSocketHandler getHandler(WebSocketConnectionContext webSocketConnectionContext) {
		String businessType = webSocketConnectionContext.getOriginParams().get("business_type");

		if (businessType == null || !register.containsKey(Integer.valueOf(businessType))) {
			log.error("[wsChat]businessType=[{}] cannot find type handler", businessType);
			throw new Error("businessType=[{}] cannot find type handler");
		}
		return register.get(Integer.valueOf(businessType));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
			throws Exception {

		// 当前用户，解析消息内容
		WebSocketConnectionContext connectionContext = (WebSocketConnectionContext)session.getAttributes().get("key");
		String businessId = connectionContext.getOriginParams().get("business_id");

		String payload = (String) message.getPayload();
		JSONObject chatData = JSON.parseObject(payload);
		ChatContent chatContent= JSONObject.toJavaObject((JSON) chatData.get("data"), ChatContent.class);
		ChatMessage chatMessage = contentToMessage(chatContent);

		ChatWebSocketHandler handler = getHandler(connectionContext);


		if (chatContent.isGroup()) {
			// 群聊
			List<String> userIds = handler.getGroup(businessId, chatContent.getGroupId());
			for (String userId: userIds) {
				sendMessage(connectionContext.getIdentifierId(), userId, chatMessage);
			}
		} else {
			// 私聊， 在好友列表里面找到私聊对象
			List<String> friends = handler.getFriends(businessId, chatContent.getMineId());
			String reverseId = chatContent.getReceiverId();
			if (!friends.contains(reverseId)){
				log.error("[wsChat]recipient {} is not in the user’s friend list {}", reverseId, friends);
				throw new Error(ErrorCode.USER_NOT_IN_LIST);
			}
			sendMessage(connectionContext.getIdentifierId(), reverseId, chatMessage);
		}
	}

	public ChatMessage contentToMessage(ChatContent chatContent) {
		ChatMineContent mine = chatContent.getMine();
		ChatToContent toContent = chatContent.getTo();
		String receiverId = mine.getId();
		if (chatContent.isGroup()) {
			receiverId = toContent.getId();
		}

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(mine.getUsername());
		chatMessage.setAvatar(mine.getAvatar());
		chatMessage.setId(receiverId);
		chatMessage.setType(toContent.getType());
		chatMessage.setContent(mine.getContent());
		chatMessage.setFromid(mine.getId());
		chatMessage.setTimestamp();

		return chatMessage;
	}

	public void sendMessage(String identifierId, String userId, ChatMessage chatMessage) throws IOException {
		// 发送消息

		Set<WebSocketSession> userSessions = getUserSession(identifierId, userId);
		TextMessage toSendMessage = new TextMessage(JsonUtil.toJsonString(chatMessage));
		if (userSessions == null) {
			log.warn("[wsChat]user {} cannot receive the message without a link", userId);
		} else {
			for (WebSocketSession userSession: userSessions){
				userSession.sendMessage(toSendMessage);
			}
		}
	}

	public Set<WebSocketSession> getUserSession(String identifierId, String userId) {
		Map<String, Set<WebSocketSession>> userSocketSessionMap = businessSocketSessionMap
				.get(identifierId);
		if (MapUtils.isEmpty(userSocketSessionMap)) {
			log.warn("[wsChat]not find userSocketSessionMap by identifierId{}", identifierId);
			return null;
		}

		Set<WebSocketSession> webSocketSessions = userSocketSessionMap.get(userId);
		if (null == webSocketSessions || webSocketSessions.isEmpty()) {
			log.warn("[wsChat]not find webSocketSessions by userId{}", userId);
			return null;
		}
		return webSocketSessions;
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		removeSession(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus)
			throws Exception {
		removeSession(session);
	}

	@Override
	public boolean supportsPartialMessages() {
		return false;
	}

	private void removeSession(WebSocketSession webSocketSession) {
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

		Map<String, Set<WebSocketSession>> userSocketSessionMap = businessSocketSessionMap
				.get(businessId);

		if (MapUtils.isEmpty(userSocketSessionMap)) {
			return;
		}

		Set<WebSocketSession> userSessions = userSocketSessionMap
				.get(connectionContext.getUserId());
		if (null == userSessions || userSessions.isEmpty()) {
			return;
		}

		for (WebSocketSession userSession : userSessions) {
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


