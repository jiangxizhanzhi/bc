package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cs.service.impl.ChatWebSocketHandler;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * RB聊天处理器实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ChatWebSocketHandler
 * @since 1.0.0
 */
@Slf4j
@Component("rbChatHandlerImpl")
public class RbChatHandlerImpl extends ChatWebSocketHandler {

	/**
	 * 靶场角色Service
	 *
	 * @see CrRoleService
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE.getCode();
	}

	/**
	 * 检查在业务
	 *
	 * @param webSocketConnectionContext Web 套接字连接上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see WebSocketConnectionContext
	 * @see Boolean
	 */
	@Override
	public Boolean checkInBusiness(WebSocketConnectionContext webSocketConnectionContext) {
		String userId = webSocketConnectionContext.getUserId();
		String businessId = webSocketConnectionContext.getOriginParams()
				.get(JsonKeyConstant.BUSINESS_ID);

		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(businessId, declareMessageType());
		List<String> userIds = Lists.newArrayList();

		for (CrRole role : rolesByRoom) {
			List<String> roleUserIds = role.getUserIdList();
			userIds.addAll(roleUserIds);
		}

		return userIds.contains(userId);
	}
}
