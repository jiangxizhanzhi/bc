package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;

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

	Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids);
}
