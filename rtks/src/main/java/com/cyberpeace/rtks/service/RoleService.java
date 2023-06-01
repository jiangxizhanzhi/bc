package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.DeleteReq;
import com.cyberpeace.rtks.controller.course.CourseConfigOutput;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.MissionTemplateSimple;
import com.cyberpeace.rtks.service.bo.skill.RoleSkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillWeightRes;

public interface RoleService extends IService<Role> {

	/**
	 * 获取层级id下的岗位列表
	 * */
	List<Role> getRolesByLevelId(String levelId);

	/**
	 * 给定一个id的数组，返回level_id在该数组中的岗位id列表
	 * */
	List<String> getRoleIdsByLevelIds(List<String> ids);

	IPage<Role> listRoles(Page<Role> page, String keyword, String frameId);

	Role getOr404(String resourceId);

	void removeAll(String table);

	void initDefaultFromPreset(String table);

	/**修改知识点或技能点，重新查询对应的课程，重置相关岗位关联的知识点或技能点课程字符串
	 * @param id 关联的知识点或技能点id
	 * @param type 类型
	 * */
	void updateBatchByModifyRelatedIds(String id, String type);

	/**删除知识点或技能点，通过删除的ids,重置相关岗位关联的id,并且修改变化的知识点或技能点课程字符串。
	 * @param deleteReq 关联的rtks的id
	 * @param type 类型
	 * */
	void updateBatchByDeleteRelatedIds(DeleteReq deleteReq, String type);

	/**
	 * 删除技能点时删除岗位-技能关联
	 * @param skillIds
	 */
	void deleteSkillRelBySkillIds(List<String> skillIds);

	/**
	 * 建立岗位-技能关联
	 * @param resourceId
	 * @param skillIdWeights
	 */
	void addSkillRelation(String resourceId, List<IdWeight> skillIdWeights);

	/**
	 * 编辑岗位时更新岗位-技能关联
	 * @param resourceId
	 * @param skillIdWeights
	 */
	void updateSkillRel(String resourceId, List<IdWeight> skillIdWeights);

	/**
	 * 更新技能关联：先删除全部关联，再重建
	 * @param roleId
	 * @param skillIdWeights
	 */
	void updateSkillRelSimple(String roleId, List<IdWeight> skillIdWeights);

	/**
	 * 读取单个岗位下技能清单
	 * @param roleId
	 * @return
	 */
	List<SkillWeightRes> listSkillByRoleId(String roleId);

	/**
	 * 删除岗位时删除岗位-技能关联
	 */
	void deleteSkillRelByRoleIds(List<String> roleIds);

	/**
	 * 查询 各个 role的skill数和mission数
	 * @param roleIds
	 * @return
	 */
	Map<String, RoleSkillRes> mapRoleSkillCount(List<String> roleIds);

	/**
	 * 获取岗位关联的所有missionId，去重
	 * @param roleId
	 * @return
	 */
	List<String> getRelMissionIds(String roleId);

	/**
	 * 清空rtks_role表和rtks_role_skill_relation表
	 */
	void clearRole();

	/**
	 * 创建和编辑岗位时，校验重名
	 * @param name
	 * @param resourceId
	 * @return
	 */
	Boolean ifNameDuplicate(String name, String resourceId);

	/**
	 * 获取课程列表
	 *
	 * @param roleId 角色id
	 * @return {@code List<CourseConfigOutput>}
	 */
	List<CourseConfigOutput> getCourseList(String roleId);

	/**
	 * 获取任务列表
	 *
	 * @param roleId 角色id
	 * @return {@code List<MissionTemplateSimple>}
	 */
	List<MissionTemplateSimple> getMissionList(String roleId);
}
