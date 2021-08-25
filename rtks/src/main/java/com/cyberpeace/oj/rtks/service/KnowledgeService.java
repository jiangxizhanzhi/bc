package com.cyberpeace.oj.rtks.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.rtks.controller.course.CourseConfig;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;

/**
 *
 */
public interface KnowledgeService extends IService<Knowledge> {

	/** 获取分页查询结果列表
	 * @param  skillListReq
	 * @return 分页查询结果
	 */
	ListRsp<KnowledgeRes> page(KnowledgeListReq skillListReq);

	/**通过id获取查询结果
	 * @param id
	 * @return 分页查询结果
	 */
	KnowledgeRes getKnowledgeByResourceId(String id);

	List<CourseConfig> getCourseConfigs(Knowledge knowledge);

	List<String> filterIds(List<String> ids);

	/**通过知识点id获取所有课程列表
	 * @param id
	 * @return 课程列表
	 */
	List<RelatedCourse> getCourseListByKnowledgeId(String id);

	void validate(Knowledge knowledge);
}
