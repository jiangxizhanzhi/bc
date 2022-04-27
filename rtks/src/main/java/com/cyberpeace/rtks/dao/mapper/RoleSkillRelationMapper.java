package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.RoleSkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;

@Mapper
@Repository
public interface RoleSkillRelationMapper extends BaseMapper<RoleSkillRelation> {

	void deleteBySkillIds(@Param("skillIds") List<String> skillIds);

	void batchInsert(@Param("relations") List<RoleSkillRelation> relations);

	List<RoleSkillRelation> batchGetByRoleId(@Param("roleId") String roleId);

	void deleteByRoleIdAndSkillIds(@Param("roleId") String roleId,
			@Param("skillIds") List<String> skillIds);

	List<IdWeight> listByRoleId(@Param("roleId") String roleId);

	void deleteByRoleId(@Param("roleId") String roleId);

	List<SkillWeightRes> listSkillByRoleId(@Param("roleId") String roleId);

	void deleteByRoleIds(@Param("roleIds") List<String> roleIds);

	List<RoleSkillRes> countByRoleIds(@Param("roleIds") List<String> roleIds);

	List<String> getMissionIdsByRoleId(@Param("roleId") String roleId);
}
