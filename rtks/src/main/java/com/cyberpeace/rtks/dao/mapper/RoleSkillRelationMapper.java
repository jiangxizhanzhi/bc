package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionContext;
import com.cyberpeace.rtks.service.bo.skill.RoleSkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;

/**
 * 角色技能关系Mapper
 *
 * @author SN
 * @date 2024/11/07
 */
@Mapper
@Repository
public interface RoleSkillRelationMapper extends BaseMapper<RoleSkillRelation> {

	/**
	 * 删除由技能id列表
	 *
	 * @param skillIds 技能id列表
	 */
	void deleteBySkillIds(@Param("skillIds") List<String> skillIds);

	/**
	 * 批量插入
	 *
	 * @param relations 关系
	 */
	void batchInsert(@Param("relations") List<RoleSkillRelation> relations);

	/**
	 * 批量获取由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<RoleSkillRelation> }
	 */
	List<RoleSkillRelation> batchGetByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由角色id和技能id列表
	 *
	 * @param roleId   角色id
	 * @param skillIds 技能id列表
	 */
	void deleteByRoleIdAndSkillIds(@Param("roleId") String roleId,
			@Param("skillIds") List<String> skillIds);

	/**
	 * 列表由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<IdWeight> }
	 */
	List<IdWeight> listByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由角色id
	 *
	 * @param roleId 角色id
	 */
	void deleteByRoleId(@Param("roleId") String roleId);

	/**
	 * 列表技能由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<SkillWeightRes> }
	 */
	List<SkillWeightRes> listSkillByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由角色id列表
	 *
	 * @param roleIds 角色id列表
	 */
	void deleteByRoleIds(@Param("roleIds") List<String> roleIds);

	/**
	 * 计数由角色id列表
	 *
	 * @param roleIds 角色id列表
	 * @return {@code List<RoleSkillRes> }
	 */
	List<RoleSkillRes> countByRoleIds(@Param("roleIds") List<String> roleIds);

	/**
	 * 获取任务id列表由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<String> }
	 */
	List<String> getMissionIdsByRoleId(@Param("roleId") String roleId);

	/**
	 * 获取任务上下文由角色id
	 *
	 * @param roleId 角色id
	 * @return {@code List<MissionContext> }
	 */
	List<MissionContext> getMissionContextsByRoleId(@Param("roleId") String roleId);

	/**
	 * 清除角色技能 rel
	 */
	void clearRoleSkillRel();
}
