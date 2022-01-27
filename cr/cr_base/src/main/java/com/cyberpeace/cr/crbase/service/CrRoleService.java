package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.MissionContext;
import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;
import com.cyberpeace.cr.crbase.service.context.UserData;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;

/**
 *
 */
public interface CrRoleService extends IService<CrRole> {

	CrRole getById(String roleId);

	/** 获取团队训练的权限人员
	 * @param roomId
	 * @return
	 */
	CrRoleContext<MissionContext> getRoles(String roomId, Integer roleCode);

	/**
	 * 获取训练分组信息
	 * @param roomId
	 * @param businessType
	 * @return
	 */
	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**获取学员在团队训练的角色任务列表(目前只去掉了答案)
	 * @param crRole 
	 * @return
	 */
	List<MissionSchemeObject<MissionContext>> getBaseMissionByRole(CrRole crRole);

	/** 获取用户有权限的团队训练ID列表
	 * @param userId
	 * @return
	 */
	List<String> getRoomIdsByUserId(String userId);

	/** 通过roomId查询角色用户列表
	 * @param roleQuery
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/** 获取学员在团队训练的角色
	 * @param roomId
	 * @param userId
	 * @return
	 */
	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	/** 获取学员在训练的角色任务列表
	 * @param roomId
	 * @param userId
	 * @return
	 */
	List<MissionSchemeObject<MissionContext>> getCrRoleMissionByRoomIdAndUserId(String roomId,
			String userId);

	/** 计算当前角色的任务数量
	 * @param crRole
	 * @return
	 */
	Integer getMissionCount(CrRole crRole);

	/** 将用户id替换为用户名字
	 * @param userIds
	 * @return
	 */
	String getUserNamesByRoleUsers(String userIds);

	/**获取团队训练实战任务统计信息
	 * @param roomId
	 * @param studentId
	 * @return (已完成，未下发，已下发)
	 */
	TaskCounter getTaskCounter(String roomId, String studentId);

	List<UserData> getUserDatas(String userIds);

	List<CrRole> selectRbStudentsRoleByRoomId(String roomId);

	/**
	 * 删除训练房间后删除训练角色
	 * @param roomId
	 * @return
	 */
	Boolean clearRoomRoles(String roomId);

	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	List<Integer> getRadar(String roomId, String userId);

	/** 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 * @param missionsScheme
	 * @return
	 */
	String getNewRoomScheme(String missionsScheme, String roomId);

	List<CrRole> selectRbStudentsRoleByRoomIdAndRoleGroup(String roomId, int roleGroup);

    void checkPermission(String roomId, String userId);
}
