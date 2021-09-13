package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.Skill;

/**
 *
 */
public interface SkillService extends IService<Skill> {

	List<String> getCourseByIds(List<String> skillIds);

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

	void validate(Skill skill);

	/**
	 * 给定技能id 如果技能不存在报错 否则返回该技能下课程配置信息
	 * */
	List<CourseConfig> getCourseConfigs(String skillId);

	/**
	 * 给定id 返回对应的技能对象 否则报错
	 * */
	Skill getOr404(String skillId);

	void checkSkillRes(List<SkillRes> skillResList, String type);

	/**获取全部技能
	 * @return
	 */
	List<SkillRes> getAllSkills();

	List<SkillRes> getSkillBySceneCategory(String scene_category_id);

	List<SkillRes> getAllSkillsBySkillSystemId(String skillSystemId);

	Map<String, List<CourseRelatedRes>> getSkillByCourseId(List<String> courseIds);
}
