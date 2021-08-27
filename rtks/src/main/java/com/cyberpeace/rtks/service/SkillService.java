package com.cyberpeace.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.course.CourseConfig;
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

	void validate(Skill skill);

	List<CourseConfig> getCourseConfigs(Skill skill);

	List<String> filterIds(List<String> ids);

	void checkSkillRes(List<SkillRes> skillResList, String type);
}
