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

	/**校验是否存在类别
	 * @param knowledge
	 */
	void validate(Knowledge knowledge);

	Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids);

	/**遍历知识点，获取知识点的相关内容，修改已删除的关联课程
	 * @param knowledgeResList
	 * @param type
	 */
	void checkKonwledgeRes(List<KnowledgeRes> knowledgeResList, String type);

}