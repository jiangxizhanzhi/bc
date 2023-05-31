package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeamDeviceMapper extends BaseMapper<TeamTrainDevice> {
    TeamTrainDevice selectByRoomIdAndDeviceId(@Param("roomId") String roomId, @Param("deviceId") String deviceId);

    void deleteByRoomIdAndDeviceIdAndCreateUserId(@Param("roomId") String roomId,
                                                  @Param("deviceId") String deviceId,
                                                  @Param("createUserId") String createUserId);

    List<TeamTrainDevice> selectAllByRoomIdAndDeviceId(@Param("roomId") String roomId,
                                                       @Param("deviceId") String deviceId);
}
