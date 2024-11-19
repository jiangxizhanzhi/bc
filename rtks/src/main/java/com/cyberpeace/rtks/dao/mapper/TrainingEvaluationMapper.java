package com.cyberpeace.rtks.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.service.bo.skill.MissionRecordBo;

/**
 * 训练评估Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Mapper
@Repository
public interface TrainingEvaluationMapper {

	/**
	 * 获取训练记录由任务模板id列表和用户id
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @param userId             用户id
	 * @return {@code List<MissionRecordBo> }
	 * @throws
	 * @see Set<String>
	 * @see String
	 * @see List<MissionRecordBo>
	 */
	List<MissionRecordBo> getTrainingRecordByMissionTemplateIdsAndUserId(
			@Param("missionTemplateIds") Set<String> missionTemplateIds,
			@Param("userId") String userId);
}
