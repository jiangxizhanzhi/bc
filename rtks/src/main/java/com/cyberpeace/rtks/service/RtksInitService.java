package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.bo.resource.IdAndWeight;

/**
 * inti_rtks_xx 相关数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/06/01
 * @since 1.0.0
 */
public interface RtksInitService {

	/**
	 * 获取导入的岗位资源需要关联的初始course id
	 * 从init_rtks_knowledge中读取
	 *
	 * @param knowledgeIds 知识id列表
	 * @return {@code List<String>}
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getInitCourseIdsByIds(List<String> knowledgeIds);

	/**
	 * 获取init_rtks_level中最后一个level的name
	 *
	 * @return {@code String}
	 * @throws
	 * @see String
	 */
	String getLastInitLevelName();

	/**
	 * 保存init岗位
	 *
	 * @param role 角色
	 * @throws
	 * @see Role
	 */
	void saveInitRole(Role role);

	/**
	 * 保存初始化岗位技能关联
	 *
	 * @param roleId          岗位id
	 * @param skillIdsWeights 技能id列表权重
	 * @throws
	 * @see String
	 * @see List<IdAndWeight>
	 */
	void saveInitRoleSkillRel(String roleId, List<IdAndWeight> skillIdsWeights);
}
