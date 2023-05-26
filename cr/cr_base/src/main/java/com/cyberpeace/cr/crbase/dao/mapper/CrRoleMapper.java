package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.role.RoleQuery;
import com.cyberpeace.cr.crbase.service.context.statistics.RoomRoleContext;

/**
 * @Entity com.cyberpeace.cr.teamtrain.dao.entity.Role
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	List<String> getRoomIdsByUserId(String userId);

	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	Boolean batchDeleteByRoomId(@Param("roomId") String roomId);

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

	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**
	 * 根据红方或蓝方自己的角色已经管理组角色
	 * @param roomId
	 * @param roleId
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	CrRole selectByRoomIdAndMissionId(@Param("roomId") String roomId,
			@Param("missionId") String missionId);

	/**
	 * 通过房间id和组查询
	 *
	 * @param roomId 房间id
	 * @param groups 组
	 * @return {@code List<CrRole>}
	 */
	List<CrRole> selectByRoomIdAndGroups(@Param("roomId") String roomId,
			@Param("groups") Collection<Integer> groups);

	List<CrRole> getRolesByRoomId(@Param("roomId") String roomId);
}
