package com.cyberpeace.cr.redvsblue.service.impl;

import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.BaseCrSceneUpdateHandler;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.scene.SceneUpdateReq;
import com.cyberpeace.cr.redvsblue.service.RbCommonDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cs.dao.entity.LocalScene;

/**
 * 红蓝前台动态更新角色资产
 *
 * @author SN
 * @date 2023/05/12
 */
@Service
public class RbSceneUpdateHandler extends BaseCrSceneUpdateHandler {

	/**
	 * RB房间Service
	 */
	@Autowired
	private RbRoomService rbRoomService;

	/**
	 * 靶场角色Service
	 */
	@Autowired
	private CrRoleService crRoleService;

	/**
	 * RB常见装置Service
	 */
	@Autowired
	private RbCommonDeviceService rbCommonDeviceService;

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	protected Integer getCode() {
		return LocalScene.BusinessCode.RED_VS_BLUE.getCode();
	}

	/**
	 * 检查权限
	 *
	 * @param roomId 房间id
	 */
	@Override
	protected void checkPermission(String roomId) {
		rbRoomService.checkTeacherPermission(roomId, UserInfoContext.getUserContext().getUserId());
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
		roleList.removeIf(CrRole::isRvbStaff);
		List<CrRole> modified = getAffectedRoles(roleList, nodeId);
		if (!modified.isEmpty()) {
			crRoleService.updateBatchById(modified);
		}
		// 清除常用资产
		rbCommonDeviceService.deleteByDeviceId(nodeId);
	}
}
