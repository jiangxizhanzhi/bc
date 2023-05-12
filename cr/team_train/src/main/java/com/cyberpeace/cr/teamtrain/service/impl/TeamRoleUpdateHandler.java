package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BaseRoleUpdateHandler;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 团队场景动态更新
 *
 * @author SN
 * @date 2023/05/12
 */
@Service
public class TeamRoleUpdateHandler extends BaseRoleUpdateHandler {

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private TeamRoomService teamRoomService;

	@Override
	protected Integer getCode() {
		return LocalScene.BusinessCode.TEAM_TRAINING.getCode();
	}

	@Override
	protected void checkPermission(String businessInstanceId) {
		teamRoomService.checkTeacherPermission(businessInstanceId,
				UserInfoContext.getUserContext().getUserId());
	}

	@Override
	public void updateRoles(String roomId, SceneUpdateReq req) {
		checkPermission(roomId);
		// get roles in room
		List<CrRole> roleList = crRoleService.getRolesByRoom(roomId,
				BusinessTemplate.BusinessType.TRAINING_TEAM.getCode());
		List<CrRole> modified = Lists.newArrayList();
		// 循环匹配，选出修改的role
		for (CrRole role : roleList) {
			filterModified(role, req.getRoleList(), modified);
		}
		// update
		crRoleService.updateBatchById(modified);
	}
}
