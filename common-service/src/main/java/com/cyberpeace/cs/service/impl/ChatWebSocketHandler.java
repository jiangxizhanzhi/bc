package com.cyberpeace.cs.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.exception.ChatException;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.ws.*;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.service.context.ChatWsMessageIdentifier;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatWebSocketHandler extends MyWebSocketHandler {

	private static Map<Integer, ChatWebSocketHandler> register = Maps.newHashMap();

	public Integer declareMessageType() {
		return CHAT_TYPE.COMMON.type;
	}

	@PostConstruct
	public void init() {
		if (register.containsKey(declareMessageType())) {
			log.error("[wsChat]declareMessageType=[" + declareMessageType() + "] repeat");
			throw new ChatException(ErrorCode.TYPE_ERROR);
		}

		register.put(declareMessageType(), this);
	}

	/**
	 * 获取组用户编号
	 * @param businessId 业务编号 做扩展使用
	 * @param chatContent
	 * @return
	 */
	public List<String> getGroup(String businessId, ChatContent chatContent) {
		return chatContent.getTo().getMembers();
	}

	/**
	 * 获取好友编号 是否适配群发
	 * @param businessId 业务编号 做扩展使用
	 * @param chatContent
	 * @return
	 */
	public List<String> getFriends(String businessId, ChatContent chatContent) {
		String toUserId = chatContent.getTo().getId();
		if (StringUtils.isBlank(toUserId)) {
			return chatContent.getTo().getMembers();
		}

		return Lists.newArrayList(toUserId);
	}

	/**
	 * 数据校验
	 * @param webSocketContext
	 * @return
	 */
	public Boolean checkInBusiness(WebSocketConnectionContext webSocketContext) {
		return true;
	}

	/**
	 * 是否发送给自己
	 * @return
	 */
	public Boolean sendSelf() {
		return false;
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		WebSocketConnectionContext webSocketContext = (WebSocketConnectionContext) session
				.getAttributes().get(JsonKeyConstant.KEY);
		String businessType = webSocketContext.getOriginParams().get(JsonKeyConstant.BUSINESS_ID);
		if (businessType == null) {
			log.error("[wsChat]current link parameter business_id could not be found");
			throw new ChatException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		ChatWebSocketHandler handler = getHandler(webSocketContext);
		if (!handler.checkInBusiness(webSocketContext)) {
			log.error("[wsChat]current user {} is not in the chat service {}",
					webSocketContext.getUserId(), webSocketContext.getOriginParams());
			throw new ChatException(ErrorCode.METHOD_ARG_NOT_VALID);
		}

		super.afterConnectionEstablished(session);

	}

	private ChatWebSocketHandler getHandler(WebSocketConnectionContext webSocketConnectionContext) {
		String businessType = webSocketConnectionContext.getOriginParams()
				.get(JsonKeyConstant.BUSINESS_TYPE);

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
				.getAttributes().get(JsonKeyConstant.KEY);
		String businessId = connectionContext.getOriginParams().get(JsonKeyConstant.BUSINESS_ID);

		// 从 session 获取发送者信息
		String senderId = connectionContext.getUserId();
		UserInfo senderInfo = SpringContextUtil.getBeanByClass(RemoteUserService.class)
				.getById(senderId);

		// 信息数据转换
		String payload = (String) message.getPayload();
		JSONObject chatData = JSON.parseObject(payload);
		ChatContent chatContent = JSONObject.toJavaObject((JSON) chatData.get(JsonKeyConstant.DATA),
				ChatContent.class);
		ChatMessage chatMessage = contentToMessage(chatContent, senderInfo.getName());

		// 消息发送
		ChatWebSocketHandler handler = getHandler(connectionContext);
		List<String> userIds = chatContent.isGroup() ? handler.getGroup(businessId, chatContent)
				: handler.getFriends(businessId, chatContent);

		for (String userId : userIds) {
			if (!userId.equals(chatContent.getMineId())) {
				sendChatMessage(connectionContext.getIdentifierId(), userId, chatMessage);
				continue;
			}

			// 是否发送给自己
			if (sendSelf()) {
				sendChatMessage(connectionContext.getIdentifierId(), userId, chatMessage);
			}
		}

	}

	/**
	 * 好友消息推送 默认推送给好友
	 * @param identifier
	 * @param mine
	 * @param message
	 */
	public void pushChatMessage(ChatWsMessageIdentifier identifier, UserInfo mine, String message,
			String role) {
		ChatMessage chatMessage = convertToChatMessage(mine, message, MESSAGE_TYPE.FRIEND, role);
		doPushChatMessage(identifier, chatMessage, Lists.newArrayList(identifier.getUserId()));
	}

	/**
	 * 可选择推送消息类型
	 * @param identifier 里面包含发送给谁
	 * @param mine
	 * @param message
	 * @param messageType
	 * @param role
	 */
	public void pushChatMessage(ChatWsMessageIdentifier identifier, UserInfo mine, String message,
			MESSAGE_TYPE messageType, String role) {
		ChatMessage chatMessage = convertToChatMessage(mine, message, messageType, role);
		doPushChatMessage(identifier, chatMessage, Lists.newArrayList(identifier.getUserId()));
	}

	/**
	 * 前端推送信息转换为消息
	 * @param chatContent
	 * @param senderName
	 * @return
	 */
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

	/**
	 * 消息发送
	 * @param identifier
	 * @param chatMessage
	 * @param userIds
	 */
	private void doPushChatMessage(WsMessageIdentifier identifier, ChatMessage chatMessage,
			List<String> userIds) {
		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(chatMessage).build();
		pushMessageToUsers(messageContext, userIds);
	}

	/**
	 * 根据发送者和接受者进行消息组装
	 * @param mine
	 * @param message
	 * @param messageType
	 * @return
	 */
	private ChatMessage convertToChatMessage(UserInfo mine, String message,
			MESSAGE_TYPE messageType, String role) {

		HelpChatMessage chatMessage = new HelpChatMessage();
		chatMessage.setUsername(mine.getName());
		chatMessage.setAvatar(mine.getAvatarUrl());
		chatMessage.setId(mine.getUserId());
		chatMessage.setType(messageType.desc);
		chatMessage.setContent(message);
		chatMessage.setFromid(mine.getUserId());
		chatMessage.setTimestamp(System.currentTimeMillis());
		chatMessage.setTime(TimeUtil.format1(new Date()));
		chatMessage.setRole(role);

		return chatMessage;
	}

	public enum MESSAGE_TYPE implements GenericEnum {

		/** 信息类型*/
		FRIEND(0, "friend"), GROUP(1, "group");

		private Integer type;

		@Getter
		private String desc;

		MESSAGE_TYPE(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		@Override
		public Integer getCode() {
			return type;
		}
	}

	public enum CHAT_TYPE implements GenericEnum {

		/** 聊天室类型*/
		RED_VS_BLUE(4, "红蓝对抗"),
		COMMON(0, "通用"),
		CLASSROOM(1, "课程表"),
		TEAM_TRAIN(3, "团队训练");

		private Integer type;

		@Getter
		private String desc;

		CHAT_TYPE(int type, String desc) {
			this.type = type;
			this.desc = desc;
		}

		@Override
		public Integer getCode() {
			return type;
		}
	}
}
