package com.cyberpeace.cr.redvsblue.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;

public interface RbCommonDeviceMapper extends BaseMapper<RbCommonDevice> {
    List<RbCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    List<RbCommonDevice> selectAllDevices();

    void insertList(@Param("commonDeviceList") List<RbCommonDevice> commonDeviceList);

    void deleteByResourceId(@Param("resourceId") String resourceId);

	void deleteByDeviceId(@Param("deviceId") String deviceId);

    void deleteByRoomId(@Param("roomId") String roomId);
}
