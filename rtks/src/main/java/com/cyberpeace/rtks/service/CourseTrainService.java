package com.cyberpeace.rtks.service;

import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;
import com.cyberpeace.rtks.service.bo.RoleCourseBo;

import java.util.List;

/**
 * 课程训练Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface CourseTrainService {

	/**
	 * 根据用户编号和岗位编号 查询课程对应的进度以及知识点信息
	 *
	 * @param userId
	 * @param roleId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<CourseKnowledgeSkillBo>
	 */
	List<CourseKnowledgeSkillBo> getCourseKnowledge(String userId, String roleId);

	/**
	 * 根据岗位编号获取职业课程路径
	 *
	 * @param roleId
	 * @param resourceId 资源id
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see List<RoleCourseBo>
	 */
	List<RoleCourseBo> getRoleCoursePath(String roleId, String resourceId);

	/**
	 * 获取课程知识点由岗位id
	 *
	 * @param resource_id 资源id
	 * @return {@code List<CourseKnowledgeSkillBo> }
	 * @throws
	 * @see String
	 * @see List<CourseKnowledgeSkillBo>
	 */
	List<CourseKnowledgeSkillBo> getCourseKnowledgeByRoleId(String resource_id);
}
