package com.cyberpeace.rtks.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.service.bo.skill.TrainingRaw;

@Mapper
@Repository
public interface SkillTrainingMapper {

	List<TrainingRaw> getRbByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	List<TrainingRaw> getTeamByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	List<TrainingRaw> getSingleByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	List<TrainingRaw>
			getTeamTemplateByBTIds(@Param("businessTemplateIds") Set<String> businessTemplateIds);

	List<TrainingRaw>
			getRbTemplateByBTIds(@Param("businessTemplateIds") Set<String> businessTemplateIds);
}
