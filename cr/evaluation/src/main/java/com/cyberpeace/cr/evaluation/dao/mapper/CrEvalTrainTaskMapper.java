package com.cyberpeace.cr.evaluation.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.evaluation.dao.entity.rb.CrEvalTrainTask;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Entity com.cyberpeace.cr.evaluation.dao.entity.EvalIndex
 */
@Mapper
@Repository
public interface CrEvalTrainTaskMapper extends BaseMapper<CrEvalTrainTask> {

	void batchInsert(@Param("list") List<CrEvalTrainTask> list);

	CrEvalTrainTask selectByUserIdAndMissionId(@Param("userId") String userId, @Param("missionId") String missionId);

	List<CrEvalTrainTask> selectByRoomId(@Param("roomId") String roomId);

	List<CrEvalTrainTask> selectByRoomIdAndUserIds(@Param("missionTemplateId")String missionTemplateId,@Param("userIds") List<String> userIds);

	List<CrEvalTrainTask> selectByRoomIdAndUserId(@Param("missionTemplateId")String missionTemplateId,@Param("userId") String userId);

	List<CrEvalTrainTask> selectByRoomIdAndUserIdsAndStatusList(@Param("roomId")String roomId,@Param("userIds") List<String> userIds,
																@Param("statusList") List<Integer> statusList);

}
