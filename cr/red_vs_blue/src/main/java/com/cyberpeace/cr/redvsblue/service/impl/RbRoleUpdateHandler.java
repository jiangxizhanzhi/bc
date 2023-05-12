package com.cyberpeace.cr.redvsblue.service.impl;

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
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 红蓝前台动态更新角色资产
 *
 * @author SN
 * @date 2023/05/12
 */
@Service
public class RbRoleUpdateHandler extends BaseRoleUpdateHandler {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Override
	protected Integer getCode() {
		return LocalScene.BusinessCode.RED_VS_BLUE.getCode();
	}

	@Override
	protected void checkPermission(String roomId) {
		rbRoomService.checkTeacherPermission(roomId, UserInfoContext.getUserContext().getUserId());
	}

	@Override
	public void updateRoles(String roomId, SceneUpdateReq req) {
		checkPermission(roomId);
		// get roles in room
		List<CrRole> roleList = crRoleService.getRolesByRoom(roomId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		List<CrRole> modified = Lists.newArrayList();
		// 循环匹配，选出修改的role
		for (CrRole role : roleList) {
			if (role.isRedStudent()) {
				filterModified(role, req.getRedRoles(), modified);
			} else if (role.isBlueStudent()) {
				filterModified(role, req.getBlueRoles(), modified);
			}
		}
		// update
		crRoleService.updateBatchById(modified);
	}
}
