package com.cyberpeace.oj.rtks.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.oj.common.model.ListRsp;
import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.common.util.PageUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.rtks.controller.req.SkillListReq;
import com.cyberpeace.oj.rtks.controller.res.SkillRes;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.dao.entity.Skill;
import com.cyberpeace.oj.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.oj.rtks.service.SkillService;
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
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private CourseService courseService;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		for (SkillRes skillRes : skillResList) {
			setCourseName(skillRes);
		}

		return PageUtil.getListResult(result);
	}

	private void setCourseName(SkillRes skillRes) {
		String courses = skillRes.getCourses();

		List<RelatedCourse> relatedCourseList = JsonUtil.toJsonObjectArray(courses,
				RelatedCourse.class);
		List ids = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			ids.add(relatedCourse.getCourseId());
		}
		Map<String, String> courseMap = getAllCourseNameByIds(ids);
		for (RelatedCourse skillCourse : relatedCourseList) {
			skillCourse.setCourseName(courseMap.get(skillCourse.getCourseId()));
		}
		skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
		skillRes.setCourseList(relatedCourseList);
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
	public SkillRes getSkillByResourceId(String id) {
		SkillRes skillRes = skillMapper.getSkillByResourceId(id);
		setCourseName(skillRes);
		return skillRes;
	}
}
