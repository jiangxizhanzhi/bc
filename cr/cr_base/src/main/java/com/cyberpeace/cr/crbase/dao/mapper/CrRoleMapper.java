package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;

/**cr角色映射器
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.Role
 @author cyberpeace
 @date 2023/05/06
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

	/**
	 * 通过房间id获取cr角色
	 *
	 * @param roleQuery 查询角色
	 * @return {@link List}<{@link CrRole}>
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/**
	 * 通过用户id获取房间id
	 *
	 * @param userId 用户id
	 * @return {@link List}<{@link String}>
	 */
	List<String> getRoomIdsByUserId(String userId);

	/**
	 * 通过房间id和用户id获取cr角色
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@link CrRole}
	 */
	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 批量删除
	 *
	 * @param roomId 房间id
	 * @return {@link Boolean}
	 */
	Boolean batchDeleteByRoomId(@Param("roomId") String roomId);

	/**
	 * 通过用户id和类型获取房间id
	 *
	 * @param userId 用户id
	 * @param code   代码
	 * @return {@link List}<{@link String}>
	 */
	List<String> getRoomIdsByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 根据user_id查询某类型训练的房间和角色
	 * @param userId
	 * @param code
	 * @return
	 */
	List<RoomRoleContext> getRoomIdsAndRoleGroupByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 获取角色通过房间
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @return {@link List}<{@link CrRole}>
	 */
	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**
	 * 根据红方或蓝方自己的角色已经管理组角色
	 * @param roomId
	 * @param roleId
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	/**
	 * 通过房间id和任务id获取
	 *
	 * @param roomId    房间id
	 * @param missionId 任务id
	 * @return {@link CrRole}
	 */
	CrRole selectByRoomIdAndMissionId(@Param("roomId") String roomId,
			@Param("missionId") String missionId);
}
