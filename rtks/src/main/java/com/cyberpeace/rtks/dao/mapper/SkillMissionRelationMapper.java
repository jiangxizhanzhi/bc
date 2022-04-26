package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;

@Mapper
@Repository
public interface SkillMissionRelationMapper extends BaseMapper<SkillMissionRelation> {

	void insertBatch(@Param("relations") List<SkillMissionRelation> relations);

	void batchDeleteByMissionTemplateIds(
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	void batchDeleteBySkillAndMissions(@Param("skillId") String skillId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);
}
