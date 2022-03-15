package com.cyberpeace.cs.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.ChatException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.*;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatWebSocketHandler extends MyWebSocketHandler {

	private static Map<Integer, ChatWebSocketHandler> register = Maps.newHashMap();

	public Integer declareMessageType() {
		return -1;
	};

	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			log.error("[wsChat]declareMessageType=[" + declareMessageType() + "] repeat");
			throw new ChatException(ErrorCode.TYPE_ERROR);
		}

		register.put(declareMessageType(), this);
	}

	public List<String> getGroup(String businessId, String groupId) {
		log.error("[wsChat] Subclass implementation getGroup");
		throw new ChatException(ErrorCode.SUB_NOT_IMPLEMENT);
	};

	public List<String> getFriends(String businessId, String userId) {
		log.error("[wsChat] Subclass implementation getFriends");
		throw new ChatException(ErrorCode.SUB_NOT_IMPLEMENT);

	};

	public Boolean checkInBusiness(WebSocketConnectionContext webSocketContext) {
		return true;
	};

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		WebSocketConnectionContext webSocketContext = (WebSocketConnectionContext) session
				.getAttributes().get("key");
		String businessType = webSocketContext.getOriginParams().get("business_id");
		if (businessType == null) {
			log.error("[wsChat]current link parameter business_id could not be found");
			throw new ChatException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		ChatWebSocketHandler handler = getHandler(webSocketContext);
		if (!handler.checkInBusiness(webSocketContext)) {
			log.error("[wsChat]current user {} is not in the chat service {}",
					webSocketContext.getUserId(), webSocketContext.getOriginParams());
			throw new ChatException(ErrorCode.METHOD_ARG_NOT_VALID);
		} ;

		super.afterConnectionEstablished(session);

	}

	private ChatWebSocketHandler getHandler(WebSocketConnectionContext webSocketConnectionContext) {
		String businessType = webSocketConnectionContext.getOriginParams().get("business_type");

		if (businessType == null || !register.containsKey(Integer.valueOf(businessType))) {
			log.error("[wsChat]businessType=[{}] cannot find type handler", businessType);
			throw new ChatException(ErrorCode.TYPE_ERROR);
		}
		return register.get(Integer.valueOf(businessType));
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message)
			throws Exception {

		// 当前用户，解析消息内容
		WebSocketConnectionContext connectionContext = (WebSocketConnectionContext) session
				.getAttributes().get("key");
		String businessId = connectionContext.getOriginParams().get("business_id");
		// 从 session 获取发送者信息
		String senderId = connectionContext.getUserId();
		UserInfo senderInfo = SpringContextUtil.getBeanByClass(RemoteUserService.class)
				.getById(senderId);

		String payload = (String) message.getPayload();
		JSONObject chatData = JSON.parseObject(payload);
		ChatContent chatContent = JSONObject.toJavaObject((JSON) chatData.get("data"),
				ChatContent.class);
		ChatMessage chatMessage = contentToMessage(chatContent, senderInfo.getName());

		ChatWebSocketHandler handler = getHandler(connectionContext);

		if (chatContent.isGroup()) {
			// 群聊
			List<String> userIds = handler.getGroup(businessId, chatContent.getGroupId());
			for (String userId : userIds) {
				// 不发送给自己
				if (!userId.equals(chatContent.getMineId())) {
					sendChatMessage(connectionContext.getIdentifierId(), userId, chatMessage);
				}
			}
		} else {
			// 私聊， 在好友列表里面找到私聊对象
			List<String> friends = handler.getFriends(businessId, chatContent.getMineId());
			String reverseId = chatContent.getReceiverId();
			if (!friends.contains(reverseId)) {
				log.error("[wsChat]recipient {} is not in the user friend list {}", reverseId,
						friends);
				throw new ChatException(ErrorCode.USER_NOT_IN_LIST);
			}
			sendChatMessage(connectionContext.getIdentifierId(), reverseId, chatMessage);
		}
	}

	public ChatMessage contentToMessage(ChatContent chatContent, String senderName) {
		ChatMineContent mine = chatContent.getMine();
		ChatToContent toContent = chatContent.getTo();
		String receiverId = mine.getId();
		if (chatContent.isGroup()) {
			receiverId = toContent.getId();
		}

		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setUsername(senderName);
		chatMessage.setAvatar(mine.getAvatar());
		chatMessage.setId(receiverId);
		chatMessage.setType(toContent.getType());
		chatMessage.setContent(mine.getContent());
		chatMessage.setFromid(mine.getId());
		chatMessage.setTimestamp();
		chatMessage.setTime(toContent.getTime());

		return chatMessage;
	}
}
