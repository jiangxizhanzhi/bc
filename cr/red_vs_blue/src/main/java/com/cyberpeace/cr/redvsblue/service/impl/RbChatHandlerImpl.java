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

	private final static String SIGN = "_";

	@Autowired
	private CrRoleService crRoleService;


	@Override
	public Integer declareMessageType() {
		return BusinessTemplate.BusinessType.RED_VS_BLUE.getCode();
	}


	@Override
	public List<String> getGroup(String roomId, String groupId) {
		boolean contains = groupId.contains(SIGN);
		if (contains) {
			return getParentGroup(roomId, groupId.replace(roomId + SIGN, ""));
		} else {
			return getSubGroup(groupId);
		}
	}

	public List<String> getParentGroup(String roomId, String parentGroupId) {
		// （红方组|或者蓝方组）+ 所有管理组下的用户
		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(roomId, declareMessageType());
		List<String> userIds = Lists.newArrayList();

		for (CrRole role: rolesByRoom) {
			List<String> roleUserIds = JsonUtil.toJsonObjectArray(role.getUserIds(),
					String.class);

			if (role.getRoleGroup().toString().equals(parentGroupId)){
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

	public List<String> getSubGroup(String roleId) {
		// 红方或蓝方下子组，不包含管理员组
		CrRole crRole = crRoleService.getById(roleId);
		if (crRole == null) {
			return Lists.newArrayList();
		}

		return JsonUtil.toJsonObjectArray(crRole.getUserIds(),
				String.class);
	}

	@Override
	public List<String> getFriends(String roomId, String userId) {
		List<CrRole> rolesByRoom = crRoleService.getRolesByRoom(roomId, declareMessageType());
		List<String> userIds = Lists.newArrayList();
		List<String> allUserIds = Lists.newArrayList();
		boolean isRoleAdmin = Boolean.FALSE;

		for (CrRole role: rolesByRoom) {
			List<String> roleUserIds = JsonUtil.toJsonObjectArray(role.getUserIds(),
					String.class);
			allUserIds.addAll(roleUserIds);

			// 当前用户是管理员，获取所有用户信息
			if (roleAdminGroups.contains(role.getRoleGroup()) && role.getUserIds().contains(userId)) {
				isRoleAdmin = Boolean.TRUE;
			}

			// 包含就加入，当前用户是普通用户，获取管理组所有人员+本组所有人员
			if(role.getUserIds().contains(userId)) {
				userIds.addAll(roleUserIds);
				continue;
			}

			// 管理员全部加入
			if (roleAdminGroups.contains(role.getRoleGroup())) {
				userIds.addAll(roleUserIds);
			}
		}

		return isRoleAdmin ? allUserIds: userIds;
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
