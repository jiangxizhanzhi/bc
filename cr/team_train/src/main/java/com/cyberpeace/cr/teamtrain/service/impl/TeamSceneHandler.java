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
import com.cyberpeace.cr.teamtrain.dao.entity.TeamRoom;
import com.cyberpeace.cr.teamtrain.dao.mapper.TeamRoomMapper;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.impl.SceneHandler;
import com.cyberpeace.integratation.model.scene.SceneMetaData;
import com.cyberpeace.integratation.model.scene.SceneOpsContext;
import com.cyberpeace.integratation.model.scene.SceneStartContext;

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

	@Override
	public SceneStartContext getSceneStartContext(LocalScene localScene) {
		return sceneService.makeSceneStartContext(localScene);
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

	public void checkBeforeStartingScene(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
			log.error("user [{}] has no permissison to start scene in room [{}]", userId, roomId);
			throw new RuntimeException();
		}
	}

	@Override
	public SceneMetaData getReferenceMetaData(LocalScene localScene) {
		TeamRoom room = teamRoomService.getById(localScene.getBusinessInstanceId());
		SceneMetaData sceneMetaData = new SceneMetaData();
		sceneMetaData.setSceneName(room.getName());
		sceneMetaData.setBusinessInstanceId(room.getResourceId());
		return sceneMetaData;
	}

	@Override
	public void clearLocalScene(LocalScene localScene) {
		TeamRoom room = teamRoomService.getById(localScene.getBusinessInstanceId());
		room.setLocalSceneId("");
		teamRoomService.updateById(room);
	}

	@Override
	public void setPassThrough(SceneOpsContext sceneOpsContext, LocalScene localScene) {
		sceneOpsContext.getContext().getPassThrough().put("local_scene_id",
				localScene.getResourceId());
	}

	@Override
	public void checkSceneOpsPermission(LocalScene localScene) {
		String roomId = localScene.getBusinessInstanceId();
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getCode())) {
			log.error("user does not have TeamRoom Teacher Permission！roomId=[{}] userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

}
