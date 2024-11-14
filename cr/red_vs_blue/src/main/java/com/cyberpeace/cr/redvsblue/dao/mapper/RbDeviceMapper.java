package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;

/**
 * 红蓝对抗装置Mapper
 *
 * @author SN
 * @date 2024/11/14
 */
@Mapper
@Repository
public interface RbDeviceMapper extends BaseMapper<RbDevice> {

	/**
	 * 查询由房间id和自选装置
	 *
	 * @param roomId 房间id
	 * @param device 装置
	 * @return {@code List<RbDevice> }
	 */
	List<RbDevice> selectByRoomIdAndOptionalDevice(@Param("roomId") String roomId,
			@Param("device") String device);

	/**
	 * 查询由房间id和装置id
	 *
	 * @param roomId   房间id
	 * @param deviceId 装置id
	 * @return {@code RbDevice }
	 */
	RbDevice selectByRoomIdAndDeviceId(@Param("roomId") String roomId,
			@Param("deviceId") String deviceId);

	/**
	 * 删除由房间id
	 *
	 * @param roomId 房间id
	 */
	void deleteByRoomId(@Param("roomId") String roomId);

	/**
	 * 删除由房间id和用户id和装置id
	 *
	 * @param roomId   房间id
	 * @param userId   用户id
	 * @param deviceId 装置id
	 */
	void deleteByRoomIdAndUserIdAndDeviceId(@Param("roomId") String roomId,
			@Param("userId") String userId, @Param("deviceId") String deviceId);
}
