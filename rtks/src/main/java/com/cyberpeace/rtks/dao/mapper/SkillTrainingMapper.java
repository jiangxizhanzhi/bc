package com.cyberpeace.rtks.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.service.bo.skill.TrainingRaw;

/**
 * 技能训练Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface SkillTrainingMapper {

	/**
	 * 获取RB由业务模板id列表和用户id
	 *
	 * @param userId              用户id
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TrainingRaw> }
	 */
	List<TrainingRaw> getRbByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	/**
	 * 获取团队由业务模板id列表和用户id
	 *
	 * @param userId              用户id
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TrainingRaw> }
	 */
	List<TrainingRaw> getTeamByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	/**
	 * 获取单由业务模板id列表和用户id
	 *
	 * @param userId              用户id
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TrainingRaw> }
	 */
	List<TrainingRaw> getSingleByBusinessTemplateIdsAndUserId(@Param("userId") String userId,
			@Param("businessTemplateIds") Set<String> businessTemplateIds);

	/**
	 * 获取团队模板由BTID
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TrainingRaw> }
	 */
	List<TrainingRaw>
			getTeamTemplateByBTIds(@Param("businessTemplateIds") Set<String> businessTemplateIds);

	/**
	 * 获取RB模板由BTID
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @return {@code List<TrainingRaw> }
	 */
	List<TrainingRaw>
			getRbTemplateByBTIds(@Param("businessTemplateIds") Set<String> businessTemplateIds);
}
