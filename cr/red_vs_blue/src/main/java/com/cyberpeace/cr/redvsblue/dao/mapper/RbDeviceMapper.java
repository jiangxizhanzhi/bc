package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;

/**
 * RB装置Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BaseMapper
 * @since 1.0.0
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
	 * @throws
	 * @see String
	 * @see String
	 * @see List<RbDevice>
	 */
	List<RbDevice> selectByRoomIdAndOptionalDevice(@Param("roomId") String roomId,
			@Param("device") String device);

	/**
	 * 查询由房间id和装置id
	 *
	 * @param roomId   房间id
	 * @param deviceId 装置id
	 * @return {@code RbDevice }
	 * @throws
	 * @see String
	 * @see String
	 * @see RbDevice
	 */
	RbDevice selectByRoomIdAndDeviceId(@Param("roomId") String roomId,
			@Param("deviceId") String deviceId);

	/**
	 * 删除由房间id
	 *
	 * @param roomId 房间id
	 * @throws
	 * @see String
	 */
	void deleteByRoomId(@Param("roomId") String roomId);

	/**
	 * 删除由房间id和用户id和装置id
	 *
	 * @param roomId   房间id
	 * @param userId   用户id
	 * @param deviceId 装置id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void deleteByRoomIdAndUserIdAndDeviceId(@Param("roomId") String roomId,
			@Param("userId") String userId, @Param("deviceId") String deviceId);
}
