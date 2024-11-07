package com.cyberpeace.rtks.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.entity.RoleSkillRelation;

/**
 * init_rtks_xx 相关
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

	/**
	 * 获取初始化知识由id列表
	 *
	 * @param knowledgeIds 知识id列表
	 * @return {@code List<Knowledge> }
	 */
	List<Knowledge> getInitKnowledgeByIds(@Param("knowledgeIds") List<String> knowledgeIds);

	/**
	 * 保存初始化角色
	 *
	 * @param role 角色
	 */
	void saveInitRole(@Param("role") Role role);

	/**
	 * 复制角色
	 *
	 * @param resourceId 资源id
	 */
	void copyRole(String resourceId);

	/**
	 * 更新角色课程id列表
	 *
	 * @param resourceId       资源id
	 * @param knowledgeCourses
	 */
	void updateRoleCourseIds(@Param("resourceId") String resourceId,
			@Param("knowledgeCourses") String knowledgeCourses);

	/**
	 * 批量插入角色技能相对
	 *
	 * @param relations 关系
	 */
	void batchInsertRoleSkillRel(@Param("relations") List<RoleSkillRelation> relations);
}
