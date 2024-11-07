package com.cyberpeace.cr.teamtrain.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.teamtrain.dao.entity.TeamTrainDevice;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 团队装置Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
public interface TeamDeviceMapper extends BaseMapper<TeamTrainDevice> {
    /**
     * 查询由房间id和装置id
     *
     * @param roomId   房间id
     * @param deviceId 装置id
     * @return {@code TeamTrainDevice }
     */
    TeamTrainDevice selectByRoomIdAndDeviceId(@Param("roomId") String roomId, @Param("deviceId") String deviceId);

    /**
     * 删除由房间id和装置id和创造用户id
     *
     * @param roomId       房间id
     * @param deviceId     装置id
     * @param createUserId 创造用户id
     */
    void deleteByRoomIdAndDeviceIdAndCreateUserId(@Param("roomId") String roomId,
                                                  @Param("deviceId") String deviceId,
                                                  @Param("createUserId") String createUserId);

    /**
     * 查询都由房间id和装置id
     *
     * @param roomId   房间id
     * @param deviceId 装置id
     * @return {@code List<TeamTrainDevice> }
     */
    List<TeamTrainDevice> selectAllByRoomIdAndDeviceId(@Param("roomId") String roomId,
                                                       @Param("deviceId") String deviceId);
}
