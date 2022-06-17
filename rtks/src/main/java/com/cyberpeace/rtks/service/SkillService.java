package com.cyberpeace.rtks.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.PageReqBase;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.service.bo.skill.IdWeight;
import com.cyberpeace.rtks.service.bo.skill.SkillRes;
import com.cyberpeace.rtks.service.bo.skill.SkillSimpleRes;

/**
 *
 */
public interface SkillService extends IService<Skill> {

	/** 获取分页查询结果列表
	 * @param  skillListReq
	 * @return 分页查询结果
	 */
	ListRsp<SkillRes> page(PageReqBase skillListReq);

	/**
	 * 创建、编辑岗位时 添加技能的列表查询接口
	 * @param reqBase
	 * @return
	 */
	ListRsp<SkillSimpleRes> pageSimple(PageReqBase reqBase);

	/**通过id获取查询结果
	 * @param id
	 * @return 分页查询结果
	 */
	SkillRes getSkillByResourceId(String id);

	/**
	 * 给定id 返回对应的技能对象 否则报错
	 * */
	Skill getOr404(String skillId);

	/**
	 * 新增技能和任务的关联关系
	 * @param skillId
	 * @param missionTemplates
	 */
	void addMissionRelation(String skillId, List<IdWeight> missionTemplates);

	/**
	 * 删除技能和任务的关联关系
	 * @param skillId
	 * @param missionTemplateIds
	 */
	void deleteMissionRelation(String skillId, List<String> missionTemplateIds);

	/**
	 * 校验技能编号是否可用
	 * @param resourceId
	 */
	void checkResourceId(@Param("resourceId") String resourceId);

	/**
	 * 编辑技能保存时更新任务关联
	 * @param resourceId
	 * @param missions
	 */
	void updateMissionRelations(String resourceId, List<IdWeight> missions);

	/**
	 * 删除技能时删除任务关联
	 * @param skillIds
	 */
	void clearMissionRel(List<String> skillIds);

	/**
	 * 计算用户的技能得分
	 * @param skillId
	 * @param userId
	 * @return score
	 */
	Double calculateScore(String skillId, String userId);

	/**
	 * 清空所有技能和技能相关
	 */
	void clearAll();

}
