package com.cyberpeace.oj.rtks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.dao.entity.Skill;

/**
 *
 */
public interface SkillService extends IService<Skill>, RoleRelatedService {

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
}
