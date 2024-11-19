package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.res.KnowledgeRes;
import com.cyberpeace.rtks.dao.entity.Knowledge;

/**
 * 知识点Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface KnowledgeService extends IService<Knowledge> {

	/**
	 * 获取分页查询结果列表
	 *
	 * @param skillListReq
	 * @return 分页查询结果
	 * @throws
	 * @see KnowledgeListReq
	 * @see ListRsp<KnowledgeRes>
	 */
	ListRsp<KnowledgeRes> page(KnowledgeListReq skillListReq);

	/**
	 * 通过id获取查询结果
	 *
	 * @param id
	 * @return 分页查询结果
	 * @throws
	 * @see String
	 * @see KnowledgeRes
	 */
	KnowledgeRes getKnowledgeByResourceId(String id);

	/**
	 * 给定知识点id 如果知识点对象不存在报错 否则返回该知识点下课程配置信息
	 *
	 * @param knowledgeId 知识点id
	 * @return {@code List<CourseConfig> }
	 * @throws
	 * @see String
	 * @see List<CourseConfig>
	 */
	List<CourseConfig> getCourseConfigs(String knowledgeId);

	/**
	 * 给定知识点id 返回对应知识点对象 不存在则报错
	 *
	 * @param knowledgeId 知识点id
	 * @return {@code Knowledge }
	 * @throws
	 * @see String
	 * @see Knowledge
	 */
	Knowledge getOr404(String knowledgeId);

	/**
	 * 校验是否存在类别
	 *
	 * @param knowledge 知识点
	 * @throws
	 * @see Knowledge
	 */
	void validate(Knowledge knowledge);

	/**
	 * 根据课程编号查询关联的知识点
	 *
	 * @param ids
	 * @return
	 * @throws
	 * @see List<String>
	 * @see Map<String, List<CourseRelatedRes>>
	 */
	Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids);

	/**
	 * 遍历知识点，获取知识点的相关内容，修改已删除的关联课程
	 *
	 * @param knowledgeResList
	 * @param type             类型
	 * @throws
	 * @see List<KnowledgeRes>
	 * @see String
	 */
	void checkKnowledgeRes(List<KnowledgeRes> knowledgeResList, String type);

	/**
	 * 根据知识点编号查询对应的课程编号
	 *
	 * @param knowledgeIds
	 * @return
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> getCourseByIds(List<String> knowledgeIds);

	/**
	 * 获取全部知识点
	 *
	 * @return {@code List<KnowledgeRes> }
	 * @throws
	 * @see List<KnowledgeRes>
	 */
	List<KnowledgeRes> getAllKnowledge();
}