package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.Skill;

/**
 *
 */
public interface SkillService extends IService<Skill> {

	/** 获取分页查询结果列表
	 * @param  skillListReq
	 * @return 分页查询结果
	 */
	ListRsp<SkillRes> page(SkillListReq skillListReq);

	/**通过id获取查询结果
	 * @param id
	 * @return 分页查询结果
	 */
	SkillRes getSkillByResourceId(String id);

	/**
	 * 给定id 返回对应的技能对象 否则报错
	 * */
	Skill getOr404(String skillId);

	void checkSkillRes(List<SkillRes> skillResList, String type);

	/**获取全部技能
	 * @return
	 */
	List<SkillRes> getAllSkills();

	/**
	 * 新增技能和任务的关联关系
	 * @param skillId
	 * @param missionTemplateIds
	 */
	void addMissionRelation(String skillId, List<String> missionTemplateIds);

	/**
	 * 删除技能和任务的关联关系
	 * @param skillId
	 * @param missionTemplateIds
	 */
	void deleteMissionRelation(String skillId, List<String> missionTemplateIds);
}
