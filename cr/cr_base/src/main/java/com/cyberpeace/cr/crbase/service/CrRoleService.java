package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

	/** 获取团队训练的权限人员
	 * @param roomId
	 * @return
	 */
	CrRoleContext getRoles(String roomId);

	/** 获取用户有权限的团队训练列表
	 * @param userId
	 * @return
	 */
	List<String> getRoomIdsByUserId(String userId);

	/** 通过businessInstanceId查询角色用户列表
	 * @param roleQuery
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/** 获取学员在团队训练的角色任务
	 * @param trainingTeamId
	 * @param userId
	 * @return
	 */
	List<MissionSchemeObject> getCrRoleByRoomIdAndUserId(String trainingTeamId, String userId);
}
