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

/**
 * 技能任务关系Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface SkillMissionRelationMapper extends BaseMapper<SkillMissionRelation> {

	/**
	 * 插入批量
	 *
	 * @param relations 关系
	 * @throws
	 * @see List<SkillMissionRelation>
	 */
	void insertBatch(@Param("relations") List<SkillMissionRelation> relations);

	/**
	 * 批量删除由任务模板id列表
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDeleteByMissionTemplateIds(
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量删除由技能和任务
	 *
	 * @param skillId            技能id
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void batchDeleteBySkillAndMissions(@Param("skillId") String skillId,
			@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量查询由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> batchSelectBySkillId(@Param("skillId") String skillId);

	/**
	 * 批量查询任务结果
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionRes> }
	 * @throws
	 * @see List<String>
	 * @see List<MissionRes>
	 */
	List<MissionRes>
			batchSelectMissionRes(@Param("missionTemplateIds") List<String> missionTemplateIds);

	/**
	 * 批量查询任务结果由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<MissionRes> }
	 * @throws
	 * @see String
	 * @see List<MissionRes>
	 */
	List<MissionRes> batchSelectMissionResBySkillId(@Param("skillId") String skillId);

	/**
	 * 批量查询任务结果名称由技能id列表
	 *
	 * @param skillIds 技能id列表
	 * @return {@code List<MissionRes> }
	 * @throws
	 * @see Collection<String>
	 * @see List<MissionRes>
	 */
	List<MissionRes> batchSelectMissionResNameBySkillIds(@Param("skillIds") Collection<String> skillIds);

	/**
	 * 批量查询
	 *
	 * @param resourceId 资源id
	 * @return {@code List<SkillMissionRelation> }
	 * @throws
	 * @see String
	 * @see List<SkillMissionRelation>
	 */
	List<SkillMissionRelation> batchSelect(@Param("resourceId") String resourceId);

	/**
	 * 清除由技能id列表
	 *
	 * @param skillIds 技能id列表
	 * @throws
	 * @see List<String>
	 */
	void clearBySkillIds(@Param("skillIds") List<String> skillIds);

	/**
	 * 列表由技能id
	 *
	 * @param skillId 技能id
	 * @return {@code List<IdWeight> }
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
	List<IdWeight> listBySkillId(@Param("skillId") String skillId);

	/**
	 * 清除全部
	 *
	 * @throws
	 */
	void clearAll();

	/**
	 * 批量查询由技能id列表
	 *
	 * @param skillIds 技能id列表
	 * @return {@code List<SkillAndMission> }
	 * @throws
	 * @see List<String>
	 * @see List<SkillAndMission>
	 */
	List<SkillAndMission> batchSelectBySkillIds(@Param("skillIds") List<String> skillIds);
}
