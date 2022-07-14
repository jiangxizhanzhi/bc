package com.cyberpeace.rtks.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.service.bo.skill.MissionRecordBo;

@Mapper
@Repository
public interface TrainingEvaluationMapper {

	List<MissionRecordBo> getTrainingRecordByMissionTemplateIdsAndUserId(
			@Param("missionTemplateIds") Set<String> missionTemplateIds,
			@Param("userId") String userId);
}
