package com.cyberpeace.cr.redvsblue.service.impl;
import com.cyberpeace.common.component.ChatWebSocketHandler;
import com.cyberpeace.common.model.ws.WebSocketConnectionContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component("rbChatHandlerImpl")
public class RbChatHandlerImpl extends ChatWebSocketHandler {

	public final static List<Integer> roleAdminGroups = new ArrayList<>(
			Arrays.asList(CrRole.RbRoleGroupCode.ADMIN.getCode(),
					CrRole.RbRoleGroupCode.REFEREE.getCode(),
					CrRole.RbRoleGroupCode.DIRECTOR.getCode()));

	@Autowired
	private CrRoleService crRoleService;


	public Integer declareMessageType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE.getCode();
	}


	@Override
	public List<String> getGroup(String roomId, String groupId) {
		// （红方组|或者蓝方组）+ 所有管理组下的用户
		String replaceGroupId = groupId.replace(roomId + "_", "");

		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(roomId, declareMessageType());
		List<String> userIds = Lists.newArrayList();

		for (CrRole role: rolesByRoom) {
			List<String> roleUserIds = JsonUtil.toJsonObjectArray(role.getUserIds(),
					String.class);


			if (!role.getRoleGroup().toString().equals(replaceGroupId)){
				userIds.addAll(roleUserIds);
				continue;
			}

			// 管理员全部加入
			if (roleAdminGroups.contains(role.getRoleGroup())) {
				userIds.addAll(roleUserIds);
			}

		}
		return userIds;
	}

	@Override
	public List<String> getFriends(String roomId, String userId) {
		// 当前用户是管理员，获取所有用户信息
		// 当前用户是普通用户，获取管理组所有人员+本组所有人员
		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(roomId, declareMessageType());
		List<String> userIds = Lists.newArrayList();

		for (CrRole role: rolesByRoom) {
			List<String> roleUserIds = JsonUtil.toJsonObjectArray(role.getUserIds(),
					String.class);
			// 包含就加入
			if(role.getUserIds().contains(userId)) {
				userIds.addAll(roleUserIds);
				continue;
			}

			// 管理员全部加入
			if (roleAdminGroups.contains(role.getRoleGroup())) {
				userIds.addAll(roleUserIds);
			}


		}

		return userIds;
	}

	@Override
	public Boolean checkInBusiness(WebSocketConnectionContext webSocketConnectionContext) {
		String userId = webSocketConnectionContext.getUserId();
		String businessId = webSocketConnectionContext.getOriginParams().get("business_id");
		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(businessId, declareMessageType());
		List<String> userIds = Lists.newArrayList();

		for (CrRole role: rolesByRoom) {
			List<String> roleUserIds = JsonUtil.toJsonObjectArray(role.getUserIds(),
					String.class);

			userIds.addAll(roleUserIds);


		}

		return userIds.contains(userId);
	}
}
