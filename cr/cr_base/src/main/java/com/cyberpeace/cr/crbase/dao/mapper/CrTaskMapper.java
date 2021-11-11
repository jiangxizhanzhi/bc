package com.cyberpeace.cr.crbase.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;

@Mapper
@Repository
public interface CrTaskMapper extends BaseMapper<CrTask> {

    CrTask selectByUserIdAndMissionId(@Param("user_id") String userId,
                                      @Param("mission_id") String missionId);

    List<CrTask> selectByRoomIdAndUserId(String roomId, String userId);

    Integer countByRoomIdAndUserId(String roomId, String userId);

    List<CrTask> selectByRoomIdAndStatusList(String roomId, int[] statusList);

    Integer countByRoomIdAndStatusList(String roomId, int[] statusList);

    Integer countByRoomIdAndUserIdAndStatusList(String roomId, String userId, int[] statusList);

    List<CrTask> selectByRoomIdAndUserIdAndStatusList(String roomId, String userId,
                                                      int[] statusList);

    List<CrTask> selectByRoomId(String roomId);

    List<CrTask> selectByRole(String roleId);

    List<CrTask> selectByRoles(List<String> roleIds);

    List<CrTask> selectByRoomIdAndMissionIds(String roomId, List<String> missionIds);

    List<CrTask> selectReportedByUserIdsAndMissionIds(@Param("roomId") String roomId,
                                                      @Param("userIds") List<String> userIds, @Param("missionIds") List<String> missionIds);

    Integer countUnreportedByRoomIdAndUserIds(String roomId, List<String> userIds);

    List<CrTask> selectByMissionId(String missionId);

    List<String> selectIdsByRoomIdAndUserIds(String roomId, List<String> userIds);

    List<String> selectIdsByRoomIdAndUserId(String roomId, String userId);
}
