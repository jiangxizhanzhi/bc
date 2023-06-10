package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;
import com.cyberpeace.rtks.service.bo.RoleCourseBo;

import java.util.List;

public interface CourseTrainService {

	/**
	 * 根据用户编号和岗位编号 查询课程对应的进度以及知识点信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<CourseKnowledgeSkillBo> getCourseKnowledge(String userId, String roleId);

	/**
	 * 根据岗位编号获取职业课程路径
	 * @param roleId
	 * @return
	 */
	List<RoleCourseBo> getRoleCoursePath(String roleId, String resourceId);

	List<CourseKnowledgeSkillBo> getCourseKnowledgeByRoleId(String resource_id);
}
