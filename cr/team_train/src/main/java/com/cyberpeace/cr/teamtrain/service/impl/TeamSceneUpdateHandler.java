package com.cyberpeace.cr.teamtrain.service.impl;

import java.util.List;

import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.common.enums.BusinessType;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BaseCrSceneUpdateHandler;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cr.teamtrain.service.TeamCommonDeviceService;
import com.cyberpeace.cr.teamtrain.service.TeamRoomService;

/**
 * 团队场景动态更新
 *
 * @author SN
 * @date 2023/05/12
 */
@Service
public class TeamSceneUpdateHandler extends BaseCrSceneUpdateHandler {

	/**
	 * 靶场岗位Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * 团队房间Service
	 */
	@Autowired
	private TeamRoomService teamRoomService;

	/**
	 * 团队常用装置Service
	 */
	@Autowired
	private TeamCommonDeviceService teamCommonDeviceService;

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	protected Integer getCode() {
		return BusinessCode.TEAM_TRAINING.getCode();
	}

	/**
	 * 检查权限
	 *
	 * @param businessInstanceId 业务实例id
	 */
	@Override
	protected void checkPermission(String businessInstanceId) {
		teamRoomService.checkTeacherPermission(businessInstanceId,
				UserInfoContext.getUserContext().getUserId());
	}

	/**
	 * 更新角色
	 *
	 * @param roomId 房间id
	 * @param req    请求体
	 */
	@Override
	public void updateRoles(String roomId, SceneUpdateReq req) {
		checkPermission(roomId);
		// get roles in room
		List<CrRole> roleList = crRoleService.getRolesByRoom(roomId,
				BusinessType.TRAINING_TEAM.getCode());
		List<CrRole> modified = Lists.newArrayList();
		// 循环匹配，选出修改的role
		for (CrRole role : roleList) {
			filterModified(role, req.getRoleList(), modified);
		}
		// update
		crRoleService.updateBatchById(modified);
	}

	/**
	 * 删除节点
	 *
	 * @param roomId 房间id
	 * @param nodeId 节点id
	 */
	@Override
	public void deleteNode(String roomId, String nodeId) {
		checkPermission(roomId);
		List<CrRole> roleList = crRoleService.getRolesByRoomId(roomId);
		// 更新含有指定节点id的学员的数据
		roleList.removeIf(CrRole::isTeamTeacher);
		List<CrRole> modified = getAffectedRoles(roleList, nodeId);
		if (!modified.isEmpty()) {
			crRoleService.updateBatchById(modified);
		}
		// 更新常用资产
		teamCommonDeviceService.deleteByDeviceId(nodeId);
	}

}
