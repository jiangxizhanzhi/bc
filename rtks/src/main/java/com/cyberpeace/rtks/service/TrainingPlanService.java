package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.controller.frame.LevelTreeOutput;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.res.KnowledgeTreeRes;
import com.cyberpeace.rtks.res.RoleDetailRes;
import com.cyberpeace.rtks.dao.entity.Role;

/**
 * 训练计划Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface TrainingPlanService {

	/**
	 * 修改框架树，增加角色
	 *
	 * @param frame 框架
	 * @throws
	 * @see LevelTreeOutput
	 */
	void parseTree(LevelTreeOutput frame);

	/**
	 * 通过角色id获取包含技能、知识点、课程列表的角色对象
	 *
	 * @param resource_id
	 * @param type
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see RoleDetailRes
	 */
	RoleDetailRes getRoleOutputByRoleId(String resource_id, String type);

	/**
	 * 获取知识点课程分页
	 *
	 * @param pageReqBase
	 * @param existRoleId
	 * @return
	 * @throws
	 * @see PageReqBase
	 * @see String
	 * @see ListRsp<KnowledgeRes>
	 */
	ListRsp<KnowledgeRes> getKnowledgeCourseList(PageReqBase pageReqBase, String existRoleId);

	/**
	 * 获取包含课程学习信息的角色对象
	 *
	 * @param resource_id
	 * @param type
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see RoleDetailRes
	 */
	RoleDetailRes getLearningPathTree(String resource_id, String type);

	/**
	 * 获取岗位详情结果由岗位id
	 *
	 * @param existRoleId 存在岗位id
	 * @return {@code RoleDetailRes }
	 * @throws
	 * @see String
	 * @see RoleDetailRes
	 */
	RoleDetailRes getRoleDetailResByRoleId(String existRoleId);

	/**
	 * 获取按照类别分组的知识树
	 *
	 * @return
	 * @throws
	 * @see List<KnowledgeTreeRes>
	 */
	List<KnowledgeTreeRes> getKnowledgeTree();

	/**
	 * 获取岗位由岗位id
	 *
	 * @param existRoleId 存在岗位id
	 * @return {@code Role }
	 * @throws
	 * @see String
	 * @see Role
	 */
	Role getRoleByRoleId(String existRoleId);
}
