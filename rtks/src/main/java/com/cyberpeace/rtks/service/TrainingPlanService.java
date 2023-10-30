package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.res.KnowledgeTreeRes;
import com.cyberpeace.rtks.res.RoleDetailRes;

public interface TrainingPlanService {

	/**
	 * 修改框架树，增加角色
	 *
	 * @param frame
	 */
	void parseTree(LevelTreeOutput frame);

	/**
	 * 通过角色id获取包含技能、知识点、课程列表的角色对象
	 *
	 * @param resourceId
	 * @param type
	 * @return
	 */
	RoleDetailRes getRoleOutputByRoleId(String resourceId, String type);

	/**
	 * 获取知识点课程分页
	 *
	 * @param pageReqBase
	 * @param existRoleId
	 * @return
	 */
	ListRsp<KnowledgeRes> getKnowledgeCourseList(PageReqBase pageReqBase, String existRoleId);

	/**
	 * 获取包含课程学习信息的角色对象
	 *
	 * @param resourceId
	 * @param type
	 * @return
	 */
	RoleDetailRes getLearningPathTree(String resourceId, String type);

	RoleDetailRes getRoleDetailResByRoleId(String existRoleId);

	/**
	 * 获取按照类别分组的知识树
	 *
	 * @return
	 */
	List<KnowledgeTreeRes> getKnowledgeTree();

	Role getRoleByRoleId(String existRoleId);
}
