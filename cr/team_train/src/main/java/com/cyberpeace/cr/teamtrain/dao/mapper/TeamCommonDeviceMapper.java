package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamCommonDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamCommonDeviceMapper extends BaseMapper<TeamCommonDevice> {

    List<TeamCommonDevice> selectByCollectUserAndRoomId(@Param("roomId") String roomId, @Param("collectUserId") String collectUserId);

    void deleteByResourceId(@Param("resourceId") String resourceId);
}
