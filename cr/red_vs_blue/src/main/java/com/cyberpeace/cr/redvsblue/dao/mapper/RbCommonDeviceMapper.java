package com.cyberpeace.cr.redvsblue.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RbCommonDeviceMapper extends BaseMapper<RbCommonDevice> {
    List<RbCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    void deleteByResourceId(@Param("resourceId") String resourceId);
}
