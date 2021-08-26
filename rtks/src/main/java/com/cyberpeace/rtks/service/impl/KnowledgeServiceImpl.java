package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.controller.res.KnowledgeRes;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.Knowledge;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.rtks.service.CourseRelatedService;
import com.cyberpeace.rtks.service.KnowledgeCategoryService;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.RelatedCourseService;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private RelatedCourseService relatedCourseService;

	@Autowired
	private KnowledgeCategoryService knowledgeCategoryService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Override
	public ListRsp<KnowledgeRes> page(KnowledgeListReq knowledgeListReq) {
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> result = knowledgeMapper.listKnowledge(page, knowledgeListReq);

		List<KnowledgeRes> knowledgeResList = result.getRecords();

		List<Knowledge> knowledgeList = Lists.newArrayList();

		// 遍历知识点，获取知识点的相关内容，修改已删除的关联课程
		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			String courses = knowledgeRes.getCourses();

			List<RelatedCourse> relatedCourseList = Lists.newArrayList();
			if (relatedCourseService.setCourseResult(courses, relatedCourseList)) {
				knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
				knowledgeRes.setCourseList(relatedCourseList);

				Knowledge knowledge = new Knowledge();
				BeanUtils.copyProperties(knowledgeRes, knowledge);

				// 修改关联表
				courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
						knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
				knowledge.setCourses(courses);
				knowledgeList.add(knowledge);

			} else {
				knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
				knowledgeRes.setCourseList(relatedCourseList);
			}

		}
		updateBatchById(knowledgeList);

		return PageUtil.getListResult(result);
	}

	@Override
	public KnowledgeRes getKnowledgeByResourceId(String id) {
		KnowledgeRes knowledgeRes = knowledgeMapper.getKnowledgeByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();

		//
		if (relatedCourseService.setCourseResult(knowledgeRes.getCourses(), relatedCourseList)) {
			knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			knowledgeRes.setCourseList(relatedCourseList);

			Knowledge knowledge = new Knowledge();
			BeanUtils.copyProperties(knowledgeRes, knowledge);

			// 修改关联表
			String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
					knowledge.getResourceId(), CourseRelated.RELATED_TYPE_KNOWLEDGE);
			knowledge.setCourses(courses);
			updateById(knowledge);

		}

		knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		knowledgeRes.setCourseList(relatedCourseList);

		return knowledgeRes;
	}

	@Override
	public List<RelatedCourse> getCourseListByKnowledgeId(String id) {
		// todo
		return null;
	}

	@Override
	public List<String> filterIds(List<String> ids) {
		return knowledgeMapper.filterIds(ids);
	}

	@Override
	public void validate(Knowledge knowledge) {
		if (knowledgeCategoryService.getById(knowledge.getCategoryId()) == null) {
			throw new InvalidParamException("类别不存在");
		}
	}

	@Override
	public Map<String, List<CourseRelatedRes>> getKnowledgeByCourseId(List<String> ids) {
		List<CourseRelatedRes> knowledgeList = knowledgeMapper.getKnowledgeByCourseId(ids);

		Map<String, List<CourseRelatedRes>> courseKnowledgeResMap = knowledgeList.stream()
				.collect(Collectors.groupingBy(CourseRelatedRes::getResourceId));
		return courseKnowledgeResMap;
	}

	@Override
	public List<CourseConfig> getCourseConfigs(Knowledge knowledge) {
		return JsonUtil.toJsonObjectArray(knowledge.getCourses(), CourseConfig.class);
	}
}
