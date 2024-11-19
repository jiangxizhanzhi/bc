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
 * 岗位技能关系Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface RoleSkillRelationMapper extends BaseMapper<RoleSkillRelation> {

	/**
	 * 删除由技能id列表
	 *
	 * @param skillIds 技能id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteBySkillIds(@Param("skillIds") List<String> skillIds);

	/**
	 * 批量插入
	 *
	 * @param relations 关系
	 * @throws
	 * @see List<RoleSkillRelation>
	 */
	void batchInsert(@Param("relations") List<RoleSkillRelation> relations);

	/**
	 * 批量获取由岗位id
	 *
	 * @param roleId 岗位id
	 * @return {@code List<RoleSkillRelation> }
	 * @throws
	 * @see String
	 * @see List<RoleSkillRelation>
	 */
	List<RoleSkillRelation> batchGetByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由岗位id和技能id列表
	 *
	 * @param roleId   岗位id
	 * @param skillIds 技能id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void deleteByRoleIdAndSkillIds(@Param("roleId") String roleId,
			@Param("skillIds") List<String> skillIds);

	/**
	 * 列表由岗位id
	 *
	 * @param roleId 岗位id
	 * @return {@code List<IdWeight> }
	 * @throws
	 * @see String
	 * @see List<IdWeight>
	 */
	List<IdWeight> listByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由岗位id
	 *
	 * @param roleId 岗位id
	 * @throws
	 * @see String
	 */
	void deleteByRoleId(@Param("roleId") String roleId);

	/**
	 * 列表技能由岗位id
	 *
	 * @param roleId 岗位id
	 * @return {@code List<SkillWeightRes> }
	 * @throws
	 * @see String
	 * @see List<SkillWeightRes>
	 */
	List<SkillWeightRes> listSkillByRoleId(@Param("roleId") String roleId);

	/**
	 * 删除由岗位id列表
	 *
	 * @param roleIds 岗位id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteByRoleIds(@Param("roleIds") List<String> roleIds);

	/**
	 * 计数由岗位id列表
	 *
	 * @param roleIds 岗位id列表
	 * @return {@code List<RoleSkillRes> }
	 * @throws
	 * @see List<String>
	 * @see List<RoleSkillRes>
	 */
	List<RoleSkillRes> countByRoleIds(@Param("roleIds") List<String> roleIds);

	/**
	 * 获取任务id列表由岗位id
	 *
	 * @param roleId 岗位id
	 * @return {@code List<String> }
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> getMissionIdsByRoleId(@Param("roleId") String roleId);

	/**
	 * 获取任务上下文由岗位id
	 *
	 * @param roleId 岗位id
	 * @return {@code List<MissionContext> }
	 * @throws
	 * @see String
	 * @see List<MissionContext>
	 */
	List<MissionContext> getMissionContextsByRoleId(@Param("roleId") String roleId);

	/**
	 * 清除岗位技能关联
	 *
	 * @throws
	 */
	void clearRoleSkillRel();
}
