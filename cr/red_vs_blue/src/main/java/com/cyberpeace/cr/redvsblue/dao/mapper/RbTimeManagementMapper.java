package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbTimeManagement;

/**
 * RB时间管理Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface RbTimeManagementMapper extends BaseMapper<RbTimeManagement> {

	/**
	 * 查询由房间id
	 *
	 * @param roomId 房间id
	 * @return {@code RbTimeManagement }
	 * @throws
	 * @see String
	 * @see RbTimeManagement
	 */
	RbTimeManagement selectByRoomId(String roomId);

	/**
	 * 查询房间id列表由状态
	 *
	 * @param status 状态
	 * @return {@code List<String> }
	 * @throws
	 * @see Integer
	 * @see List<String>
	 */
	List<String> selectRoomIdsByStatus(Integer status);

	/**
	 * 查询都
	 *
	 * @return {@code List<RbTimeManagement> }
	 * @throws
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> selectAll();

	/**
	 * 查询由状态
	 *
	 * @param status 状态
	 * @return {@code List<RbTimeManagement> }
	 * @throws
	 * @see Integer
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> selectByStatus(@Param("status") Integer status);

	/**
	 * 查询排除状态
	 *
	 * @param status 状态
	 * @return {@code List<RbTimeManagement> }
	 * @throws
	 * @see Integer
	 * @see List<RbTimeManagement>
	 */
	List<RbTimeManagement> selectExcludeStatus(@Param("status") Integer status);
}
