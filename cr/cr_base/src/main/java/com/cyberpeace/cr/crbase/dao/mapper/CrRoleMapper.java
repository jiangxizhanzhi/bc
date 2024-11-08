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
 * 靶场岗位Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
@Repository
public interface CrRoleMapper extends BaseMapper<CrRole> {

	/**
	 * 查询靶场岗位由房间id
	 *
	 * @param roleQuery 岗位查询
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> selectCrRoleByRoomId(RoleQuery roleQuery);

	/**
	 * 获取房间id列表由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<String> }
	 */
	List<String> getRoomIdsByUserId(String userId);

	/**
	 * 获取靶场岗位由房间id和用户id
	 *
	 * @param roomId 房间id
	 * @param userId 用户id
	 * @return {@code CrRole }
	 */
	CrRole getCrRoleByRoomIdAndUserId(String roomId, String userId);

	/**
	 * 批量删除由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code Boolean }
	 */
	Boolean batchDeleteByRoomId(@Param("roomId") String roomId);

	/**
	 * 获取房间id列表由用户id和类型
	 *
	 * @param userId 用户id
	 * @param code   代号
	 * @return {@code List<String> }
	 */
	List<String> getRoomIdsByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 根据user_id查询某类型训练的房间和角色
	 *
	 * @param userId
	 * @param code
	 * @return
	 */
	List<RoomRoleContext> getRoomIdsAndRoleGroupByUserIdAndType(@Param("userId") String userId,
			@Param("code") Integer code);

	/**
	 * 获取角色由房间
	 *
	 * @param roomId       房间id
	 * @param businessType 业务类型
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> getRolesByRoom(String roomId, Integer businessType);

	/**
	 * 根据红方或蓝方自己的角色已经管理组角色
	 *
	 * @param roomId
	 * @param roleId
	 * @return
	 */
	List<CrRole> selectCrRoleByRoomIdAndRoleId(String roomId, String roleId);

	/**
	 * 查询由房间id和任务id
	 *
	 * @param roomId    房间id
	 * @param missionId 任务id
	 * @return {@code CrRole }
	 */
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

	/**
	 * 获取角色由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> getRolesByRoomId(@Param("roomId") String roomId);

	/**
	 * 恢复节点由房间id
	 *
	 * @param roomId 房间id
	 */
	void recoverNodesByRoomId(@Param("roomId") String roomId);

	/**
	 * 查询批量由房间id列表
	 *
	 * @param roomIdList 房间id列表
	 * @return {@code List<CrRole> }
	 */
	List<CrRole> selectBatchByRoomIds(@Param("roomIdList") List<String> roomIdList);
}
