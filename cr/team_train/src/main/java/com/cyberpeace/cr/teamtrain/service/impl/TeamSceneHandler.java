package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNode;
import com.cyberpeace.cr.crbase.dao.entity.CrRoleNodesData;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrRoleService;
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

	@Override
	public Integer getBusinessType() {
		return LocalScene.BusinessCode.TEAM_TRAINING.getCode();
	}

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

	public void checkSceneOperationPermission(String roomId, String userId) {
		CrRole role = crRoleService.getCrRoleByRoomIdAndUserId(roomId, userId);
		if (!role.getRoleGroup().equals(CrRole.TeamRoleGroupCode.TEACHER.getValue())) {
			log.error("User does not have TeamRoom Teacher Permission！roomId=[{}] userId=[{}]",
					roomId, userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}

	public void checkBeforeStartingScene(String roomId) {
		teamRoomService.checkRoomOpening(roomId);
	}

	public void filterVisibleTopo(JSONObject topoData, CrRoleNodesData nodes) {
		if (nodes == null) {
			return;
		}

		List<String> visibleNodeIds = nodes.getVisibleNodes().stream().map(CrRoleNode::getId)
				.collect(Collectors.toList());
		JSONArray elements = topoData.getJSONObject("topology").getJSONArray("elements");
		int i = 0;
		while (i < elements.size()) {
			if (!visibleNodeIds.contains(elements.getJSONObject(i).getString("id"))) {
				elements.remove(i);
			} else {
				i++;
			}
		}
	}

	@Override
	public String getReferenceMetaData(LocalScene localScene) {
		TeamRoom room = teamRoomService.getById(localScene.getBusinessInstanceId());
		return JsonUtil.toJsonString(new RoomInfo(room.getResourceId(), room.getName()));
	}
}
