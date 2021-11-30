package com.cyberpeace.cr.teamtrain.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.SceneService;
import com.cyberpeace.cr.crbase.service.context.scene.RoomInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.impl.SceneHandler;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TeamSceneHandler extends SceneHandler {

	@Autowired
	TeamRoomMapper teamRoomMapper;

	@Autowired
	CrRoleMapper crRoleMapper;

	@Autowired
	CrRoleService crRoleService;

	@Autowired
	LocalSceneService localSceneService;

	@Autowired
	TeamRoomService teamRoomService;

	@Autowired
	SceneService sceneService;

	@Override
	public Integer getBusinessType() {
		return LocalScene.BusinessCode.TEAM_TRAINING.getCode();
	}

	public CrRoleNodesData getNodes(String userId, String roomId, String roleId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
			if (StringUtils.isNotBlank(roleId)) {
				// 教员查看学员角色的战争迷雾
				CrRole fakeRole = crRoleService.getById(roleId);
				if (fakeRole.getRoomId().equals(roomId)) {
					return JsonUtil.toJsonObject(fakeRole.getNodes(), CrRoleNodesData.class);
				}
			} else {
				// 教员所有节点可见
				return null;
			}
		}
		return JsonUtil.toJsonObject(role.getNodes(), CrRoleNodesData.class);
	}

	public void checkSceneOperationPermission(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
			log.error("User does not have TeamRoom Teacher Permission！roomId=[{}] userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	public void checkBeforeStartingScene(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
			log.error("user [{}] has no permissison to start scene in room [{}]", userId, roomId);
			throw new RuntimeException();
		}
	}

	public void checkBeforeTestingScene(String templateId) {
		if (!UserInfoContext.getUserContext().isAdmin()) {
			log.error("current user is not permitted to test template [{}]", templateId);
			throw new RuntimeException();
		}
	}

	@Override
	public String getReferenceMetaData(LocalScene localScene) {
		TeamRoom room = teamRoomService.getById(localScene.getBusinessInstanceId());
		return JsonUtil.toJsonString(new RoomInfo(room.getResourceId(), room.getName()));
	}

}
