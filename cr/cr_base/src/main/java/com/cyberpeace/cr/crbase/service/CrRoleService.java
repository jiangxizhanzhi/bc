package com.cyberpeace.cr.crbase.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.room.DashboardInfo;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.*;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleContext;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;

/**
 * 靶场角色Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface CrRoleService extends IService<CrRole> {

	/**
	 * 由id查询角色
	 *
	 * @param roleId 角色id
	 * @return 角色
	 */
	CrRole getById(String roleId);

	/**
	 * 获取团队训练的权限人员
	 *
	 * @param roomId    训练实例id
	 * @param roleGroup 角色组
	 * @return 角色相关数据
	 */
	CrRoleContext<MissionContext> getRoles(String roomId, CrRole.RoleGroup roleGroup);

	/**
	 * 获取训练分组信息
	 *
	 * @param roomId       训练实例id
	 * @param businessType 业务类型
	 * @return 角色列表
	 */
	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**
	 * 获取学员在团队训练的角色任务列表(目前只去掉了答案)
	 *
	 * @param crRole 角色
	 * @return 角色任务列表
	 */
	List<MissionSchemeObject<MissionContext>> getBaseMissionByRole(CrRole crRole);

	/**
	 * 获取用户有权限的团队训练ID列表
	 *
	 * @param userId 用户id
	 * @return 有权限的团队训练ID列表
	 */
	List<String> getRoomIdsByUserId(String userId);

	/**
	 * 通过roomId查询角色用户列表
	 *
	 * @param roleQuery 查询体
	 * @return 角色列表
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/**
	 * 获取学员在团队训练的角色
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return 角色
	 */
	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 获取房间全部角色
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> getRolesByRoomId(String roomId);

	/**
	 * 获取学员在训练的角色任务列表
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return 角色任务列表
	 */
	List<MissionSchemeObject<MissionContext>> getCrRoleMissionByRoomIdAndUserId(String roomId,
			String userId);


	/**
	 * 获取靶场角色任务由房间id和靶场角色
	 *
	 * @param roomId  房间id
	 * @param crRoles 靶场角色
	 * @return {@code Map<String,List<MissionSchemeObject<MissionContext>>> }
	 */
	Map<String,List<MissionSchemeObject<MissionContext>>> getCrRoleMissionByRoomIdAndCrRoles(String roomId,
																				List<CrRole> crRoles);

	/**
	 * 计算当前角色的任务数量
	 *
	 * @param crRole 角色
	 * @return 任务数量
	 */
	Integer getMissionCount(CrRole crRole);

	/**
	 * 将用户id替换为用户名字
	 *
	 * @param userList 用户id列表
	 * @return 用户名列表
	 */
	String getUserNamesByRoleUsers(List<String> userList);

	/**
	 * 获取团队训练实战任务统计信息
	 *
	 * @param roomId    训练实例id
	 * @param studentId 学员id
	 * @return (已完成 ， 未下发 ， 已下发)
	 */
	TaskCounter getTaskCounter(String roomId, String studentId);

	/**
	 * 获取用户信息
	 *
	 * @param userIdList 用户id列表
	 * @return 用户信息列表
	 */
	List<UserData> getUserDatas(List<String> userIdList);

	/**
	 * 获取红蓝实例的学员角色列表
	 *
	 * @param roomId 训练实例id
	 * @return 角色列表
	 */
	List<CrRole> selectRbStudentsRoleByRoomId(String roomId);

	/**
	 * 删除训练房间后删除训练角色
	 *
	 * @param roomId 训练实例id
	 */
	void clearRoomRoles(String roomId);

	/**
	 * 根据登录用户的角色来查询其能看到的分组角色
	 *
	 * @param roomId 训练实例id
	 * @param roleId 角色id
	 * @return 其能看到的分组角色列表
	 */
	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	/**
	 * 获取某用户在单个训练中的雷达图
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return 雷达数值
	 */
	List<Integer> getRadar(String roomId, String userId);

	/**
	 * 根据模板的任务id，复制任务并获取新的id，返回新的json字符串
	 *
	 * @param missionsScheme 任务编排json字符串
	 * @param roomId         训练实例id
	 * @return 新的任务编排json字符串
	 */
	String getNewRoomScheme(String missionsScheme, String roomId);

	/**
	 * 根据红蓝实例id和角色组查询学员角色
	 *
	 * @param roomId    训练实例id
	 * @param roleGroup 角色组
	 * @return 角色列表
	 */
	List<CrRole> selectRbStudentsRoleByRoomIdAndRoleGroup(String roomId, int roleGroup);

	/**
	 * 查询用户是否有接入训练实例权限
	 *
	 * @param roomId 训练实例id
	 * @param userId 用户id
	 * @return
	 */
	CrRole checkPermission(String roomId, String userId);

	/**
	 * 通过房间和组获取角色
	 *
	 * @param roomId 房间id
	 * @param groups 组
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> getRolesByRoomAndGroups(String roomId, Collection<Integer> groups);

	/**
	 * 获取训练任务释放信息列表
	 *
	 * @param roomId 房间id
	 * @param code   代号
	 * @return {@code List<TrainTaskReleaseInfoRsp> }
	 */
	List<TrainTaskReleaseInfoRsp> getTrainTaskReleaseInfoList(String roomId, Integer code);

	/**
	 * 获取训练手动
	 *
	 * @param roomId 房间id
	 * @param code   代号
	 * @return {@code List<TrainTaskReleaseInfoRsp> }
	 */
	List<TrainTaskReleaseInfoRsp> getTrainManual(String roomId, Integer code);

	/**
	 * 组合角色 const
	 *
	 * @param dashboardInfo 仪表板信息
	 */
	void combineRoleConst(DashboardInfo dashboardInfo);

	/**
	 * 获取用户角色由房间id和角色组
	 *
	 * @param roomId 房间id
	 * @param groups 组
	 * @return {@code List<UserRole> }
	 */
	List<UserRole> getUserRolesByRoomIdAndRoleGroups(String roomId, List<Integer> groups);

	/**
	 * 获取房间中所有用户和对应的角色map
	 *
	 * @param roomId 房间id
	 * @return {@code Map<String, CrRole>}
	 */
	Map<String, CrRole> getUserRoleMapByRoomId(String roomId);

	/**
	 * 恢复角色节点配置到创建房间时的快照
	 *
	 * @param roomId 房间id
	 */
	void recoverNodesConfig(String roomId);

	/**
	 * 通过房间id列表获取全部角色
	 *
	 * @param roomIdList 房间id列表
	 * @return {@code Map<String, List<CrRole>>}
	 */
	Map<String, List<CrRole>> getRoomRoleMapByRoomIdList(List<String> roomIdList);

	/**
	 * 获取批量通过房间id列表
	 *
	 * @param roomIdList 房间id列表
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> getBatchByRoomIds(List<String> roomIdList);
}
