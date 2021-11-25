package com.cyberpeace.cr.teamtrain.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.dao.entity.LocalSceneManager;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.SceneHandler;
import com.cyberpeace.cr.crbase.service.context.scene.RoomInfo;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

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
	TeamRoomService teamRoomService;

	@Override
	public Integer getAppId() {
		return LocalSceneManager.AppId.TRAINING_TEAM.getCode();
	}

	@Override
	public String getRoomIdBySceneManagerId(String sceneManagerId) {
		TeamRoom room = teamRoomService.getBySceneMangerId(sceneManagerId);
		return room.getResourceId();
	}

	@Override
	public RoomInfo getRoomInfoBySceneManagerId(String sceneManagerId) {
		TeamRoom room = teamRoomService.getBySceneMangerId(sceneManagerId);
		return new RoomInfo(room.getResourceId(), room.getName());
	}

	@Override
	public CrRoleNodesData getNodes(String userId, String roomId, String roleId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getValue())) {
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

	@Override
	public void checkSceneOperationPermission(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getValue())) {
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	@Override
	public void checkBeforeStartingScene(String roomId) {
		teamRoomService.checkRoomOpening(roomId);
	}
}
