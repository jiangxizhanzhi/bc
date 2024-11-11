package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.SkillMissionRelation;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionRes;
import com.cyberpeace.rtks.service.bo.skill.SkillAndMission;

/**
 * 技能任务关系Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface SkillMissionRelationMapper extends BaseMapper<SkillMissionRelation> {

	/**
	 * 插入批量
	 *
	 * @param relations 关系
	 */
	void insertBatch(@Param("relations") List<SkillMissionRelation> relations);

	/**
	 * 批量删除由任务模板id列表
	 *
	 * @param missionTemplateIds 任务模板id列表
	 */
	void batchDeleteByMissionTemplateIds(
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量删除由技能和任务
	 *
	 * @param skillId            技能id
	 * @param missionTemplateIds 任务模板id列表
	 */
	void batchDeleteBySkillAndMissions(@Param("skillId") String skillId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量查询由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<String> }
	 */
	List<String> batchSelectBySkillId(@Param("skillId") String skillId);

	/**
	 * 批量查询任务结果
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionRes> }
	 */
	List<MissionRes>
			batchSelectMissionRes(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量查询任务结果由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<MissionRes> }
	 */
	List<MissionRes> batchSelectMissionResBySkillId(@Param("skillId") String skillId);

	/**
	 * 批量查询
	 *
	 * @param resourceId 资源id
	 * @return {@code List<SkillMissionRelation> }
	 */
	List<SkillMissionRelation> batchSelect(@Param("resourceId") String resourceId);

	/**
	 * 清除由技能id列表
	 *
	 * @param skillIds 技能id列表
	 */
	void clearBySkillIds(@Param("skillIds") List<String> skillIds);

	/**
	 * 列表由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<IdWeight> }
	 */
	List<IdWeight> listBySkillId(@Param("skillId") String skillId);

	/**
	 * 全部清除
	 */
	void clearAll();

	/**
	 * 批量查询由技能id列表
	 *
	 * @param skillIds 技能id列表
	 * @return {@code List<SkillAndMission> }
	 */
	List<SkillAndMission> batchSelectBySkillIds(@Param("skillIds") List<String> skillIds);
}
