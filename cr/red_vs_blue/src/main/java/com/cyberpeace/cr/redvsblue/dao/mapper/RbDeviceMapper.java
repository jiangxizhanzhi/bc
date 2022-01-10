package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDevice;

@Mapper
@Repository
public interface RbDeviceMapper extends BaseMapper<RbDevice> {

	List<RbDevice> selectByRoomIdAndOptionalDevice(@Param("roomId") String roomId,
			@Param("device") String device);

	RbDevice selectByRoomIdAndDeviceId(@Param("roomId") String roomId,
			@Param("deviceId") String deviceId);

	void deleteByRoomId(@Param("roomId") String roomId);

	void deleteByRoomIdAndUserIdAndDeviceId(@Param("roomId") String roomId,
			@Param("userId") String userId, @Param("deviceId") String deviceId);
}
