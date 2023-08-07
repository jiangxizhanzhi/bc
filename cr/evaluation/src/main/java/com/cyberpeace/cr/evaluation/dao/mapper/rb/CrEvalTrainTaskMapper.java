package com.cyberpeace.cr.evaluation.dao.mapper.rb;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTrainTask;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
public interface CrEvalTrainTaskMapper extends BaseMapper<CrEvalTrainTask> {

	CrEvalTrainTask selectByUserIdAndMissionId(@Param("userId") String userId, @Param("missionId") String missionId);

	List<CrEvalTrainTask> selectByRoomId(@Param("roomId") String roomId);

	List<CrEvalTrainTask> selectByRoomIdAndUserIds(@Param("roomId")String roomId,@Param("userIds") List<String> userIds);

	List<CrEvalTrainTask> selectByRoomIdAndUserId(@Param("roomId")String roomId,@Param("userId") String userId);

	List<CrEvalTrainTask> selectByRoomIdAndUserIdsAndStatusList(@Param("roomId")String roomId,@Param("userIds") List<String> userIds,
																@Param("statusList") List<Integer> statusList);

}
