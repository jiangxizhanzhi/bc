package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.service.bo.CourseKnowledgeBo;

public interface TrainingPlanService {

	void parseTree(LevelTreeOutput frame);

	Map<String, List<SkillRes>> getSkillTree();

	RoleDetailRes getRoleOutputByRoleId(String resource_id, String type);

	ListRsp<KnowledgeRes> getKnowledgeCourseList(PageReqBase pageReqBase, String existRoleId);

	RoleDetailRes getLearningPathTree(String resource_id, String type);

	/**
	 * 根据用户编号和岗位编号 查询课程对应的进度以及知识点信息
	 * @param userId
	 * @param roleId
	 * @return
	 */
	List<CourseKnowledgeBo> getCourseKnowledges(String userId, String roleId);
}
