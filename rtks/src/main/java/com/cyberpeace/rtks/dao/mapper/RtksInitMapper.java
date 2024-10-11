package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;

/**
 *  init_rtks_xx 相关
 *
 * @author SN
 * @date 2023/06/01
 */
@Mapper
@Repository
public interface RtksInitMapper {

	/**
	 * 获取init_rtks_level中最后一个level的name
	 *
	 * @return {@code String}
	 */
	String getLastInitLevelName();

	List<Knowledge> getInitKnowledgeByIds(@Param("knowledgeIds") List<String> knowledgeIds);

	void saveInitRole(@Param("role") Role role);

	void copyRole(String resourceId);

	void updateRoleCourseIds(@Param("resourceId") String resourceId,
			@Param("knowledgeCourses") String knowledgeCourses);

	void batchInsertRoleSkillRel(@Param("relations") List<RoleSkillRelation> relations);
}
