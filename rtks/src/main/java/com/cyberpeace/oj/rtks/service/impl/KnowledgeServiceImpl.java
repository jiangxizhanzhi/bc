package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.rtks.controller.req.KnowledgeListReq;
import com.cyberpeace.oj.rtks.controller.res.KnowledgeRes;
import com.cyberpeace.oj.rtks.dao.entity.Knowledge;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.mapper.KnowledgeMapper;
import com.cyberpeace.oj.rtks.service.KnowledgeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class KnowledgeServiceImpl extends ServiceImpl<KnowledgeMapper, Knowledge> implements KnowledgeService {

	@Autowired
	private KnowledgeMapper knowledgeMapper;

	@Autowired
	private CourseService courseService;

	@Override
	public ListRsp<KnowledgeRes> page(KnowledgeListReq knowledgeListReq) {
		Page page = knowledgeListReq.toPageParam();

		IPage<KnowledgeRes> result = knowledgeMapper.listKnowledge(page, knowledgeListReq);

		List<KnowledgeRes> knowledgeResList = result.getRecords();
		for (KnowledgeRes knowledgeRes : knowledgeResList) {
			setCourseName(knowledgeRes);
		}

		return PageUtil.getListResult(result);
	}

	private void setCourseName(KnowledgeRes knowledgeRes) {
		String courses = knowledgeRes.getCourses();

		List<RelatedCourse> relatedCourseList = JsonUtil.toJsonObjectArray(courses,
				RelatedCourse.class);
		List ids = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			ids.add(relatedCourse.getCourseId());
		}
		Map<String, String> courseMap = getAllCourseNameByIds(ids);
		for (RelatedCourse knowledgeCourse : relatedCourseList) {
			knowledgeCourse.setCourseName(courseMap.get(knowledgeCourse.getCourseId()));
		}
		knowledgeRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		knowledgeRes.setCourseList(relatedCourseList);
	}

	private Map<String, String> getAllCourseNameByIds(List<String> ids) {
		Map<String, String> map = Maps.newHashMap();
		List<CourseInfoBo> courseList = courseService.selectByIds(ids);
		for (CourseInfoBo courseInfoBo : courseList) {
			map.put(courseInfoBo.getResourceId(), courseInfoBo.getName());
		}
		return map;
	}

	@Override
	public KnowledgeRes getKnowledgeByResourceId(String id) {
		KnowledgeRes knowledgeRes = knowledgeMapper.getKnowledgeByResourceId(id);
		setCourseName(knowledgeRes);
		return knowledgeRes;
	}
}
