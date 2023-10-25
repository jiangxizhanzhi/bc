package com.cyberpeace.rtks.dao.mapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionRes;
import com.cyberpeace.rtks.service.bo.skill.SkillAndMission;

@Mapper
@Repository
public interface SkillMissionRelationMapper extends BaseMapper<SkillMissionRelation> {

	void insertBatch(@Param("relations") List<SkillMissionRelation> relations);

	void batchDeleteByMissionTemplateIds(
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	void batchDeleteBySkillAndMissions(@Param("skillId") String skillId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	List<String> batchSelectBySkillId(@Param("skillId") String skillId);

	List<MissionRes>
			batchSelectMissionRes(@Param("missionTemplateIds") List<String> missionTemplateIds);

	List<MissionRes> batchSelectMissionResBySkillId(@Param("skillId") String skillId);

	List<MissionRes> batchSelectMissionResNameBySkillIds(@Param("skillIds") Collection<String> skillIds);

	List<SkillMissionRelation> batchSelect(@Param("resourceId") String resourceId);

	void clearBySkillIds(@Param("skillIds") List<String> skillIds);

	List<IdWeight> listBySkillId(@Param("skillId") String skillId);

	void clearAll();

	List<SkillAndMission> batchSelectBySkillIds(@Param("skillIds") List<String> skillIds);
}
