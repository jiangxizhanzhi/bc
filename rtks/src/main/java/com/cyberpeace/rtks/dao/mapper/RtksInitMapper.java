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
 * @version 1.0.0
 * @date 2023/06/01
 * @since 1.0.0
 */
@Mapper
@Repository
public interface RtksInitMapper {

	/**
	 * 获取init_rtks_level中最后一个level的name
	 *
	 * @return {@code String}
	 * @throws
	 * @see String
	 */
	String getLastInitLevelName();

	/**
	 * 获取初始化知识点由id列表
	 *
	 * @param knowledgeIds 知识点id列表
	 * @return {@code List<Knowledge> }
	 * @throws
	 * @see List<String>
	 * @see List<Knowledge>
	 */
	List<Knowledge> getInitKnowledgeByIds(@Param("knowledgeIds") List<String> knowledgeIds);

	/**
	 * 保存初始化岗位
	 *
	 * @param role 岗位
	 * @throws
	 * @see Role
	 */
	void saveInitRole(@Param("role") Role role);

	/**
	 * 复制岗位
	 *
	 * @param resourceId 资源id
	 * @throws
	 * @see String
	 */
	void copyRole(String resourceId);

	/**
	 * 更新岗位课程id列表
	 *
	 * @param resourceId       资源id
	 * @param knowledgeCourses 知识点课程
	 * @throws
	 * @see String
	 * @see String
	 */
	void updateRoleCourseIds(@Param("resourceId") String resourceId,
			@Param("knowledgeCourses") String knowledgeCourses);

	/**
	 * 批量插入岗位技能关联
	 *
	 * @param relations 关系
	 * @throws
	 * @see List<RoleSkillRelation>
	 */
	void batchInsertRoleSkillRel(@Param("relations") List<RoleSkillRelation> relations);
}
