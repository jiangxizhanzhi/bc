package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.bo.resource.IdAndWeight;

/**
 * inti_rtks_xx 相关数据
 *
 * @author SN
 */
public interface RtksInitService {

	/**
	 * 获取导入的岗位资源需要关联的初始course id
	 * 从init_rtks_knowledge中读取
	 *
	 * @param knowledgeIds 知识id列表
	 * @return {@code List<String>}
	 */
	List<String> getInitCourseIdsByIds(List<String> knowledgeIds);

	/**
	 * 获取init_rtks_level中最后一个level的name
	 *
	 * @return {@code String}
	 */
	String getLastInitLevelName();

	/**
	 * 保存init岗位
	 *
	 * @param role 角色
	 */
	void saveInitRole(Role role);

	void saveInitRoleSkillRel(String roleId, List<IdAndWeight> skillIdsWeights);
}
