package com.cyberpeace.rtks.service.impl;

import java.util.List;

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
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.course.CourseConfig;
import com.cyberpeace.rtks.controller.req.SkillListReq;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
import com.cyberpeace.rtks.controller.res.SkillRes;
import com.cyberpeace.rtks.dao.entity.CourseRelated;
import com.cyberpeace.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.rtks.dao.entity.Skill;
import com.cyberpeace.rtks.dao.mapper.SkillMapper;
import com.cyberpeace.rtks.service.*;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class SkillServiceImpl extends ServiceImpl<SkillMapper, Skill> implements SkillService {

	@Autowired
	private SkillMapper skillMapper;

	@Autowired
	private RelatedCourseService relatedCourseDao;

	@Autowired
	private SkillCategoryService skillCategoryService;

	@Autowired
	private SkillSystemService skillSystemService;

	@Autowired
	private CourseRelatedService courseRelatedService;

	@Override
	public ListRsp<SkillRes> page(SkillListReq skillListReq) {
		Page page = skillListReq.toPageParam();

		IPage<SkillRes> result = skillMapper.listSkill(page, skillListReq);

		List<SkillRes> skillResList = result.getRecords();
		checkSkillRes(skillResList, RoleDetailRes.RELATED_COURSE_ALL);

		return PageUtil.getListResult(result);
	}

	@Override
	public SkillRes getSkillByResourceId(String id) {
		SkillRes skillRes = skillMapper.getSkillByResourceId(id);
		List<RelatedCourse> relatedCourseList = Lists.newArrayList();
		List<CourseStudyProcessBo> processBos = Lists.newArrayList();

		if (relatedCourseDao.setCourseResult(skillRes.getCourses(), relatedCourseList, processBos,
				RoleDetailRes.RELATED_COURSE_COURSE)) {
			skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);

			Skill skill = new Skill();
			BeanUtils.copyProperties(skillRes, skill);
			// 修改关联表
			String courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
					skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
			skill.setCourses(courses);

			updateById(skill);
		} else {
			skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);
		}

		return skillRes;
	}

	@Override
	public void validate(Skill skill) {
		if (skillCategoryService.getById(skill.getCategoryId()) == null) {
			throw new InvalidParamException("类别不存在");
		}
		if (skillSystemService.getById(skill.getSystemId()) == null) {
			throw new InvalidParamException("体系不存在");
		}
	}

	@Override
	public List<String> filterIds(List<String> ids) {
		return skillMapper.filterIds(ids);
	}

	@Override
	public void checkSkillRes(List<SkillRes> skillResList, String related_course_type) {
		List<Skill> skillList = Lists.newArrayList();

		for (SkillRes skillRes : skillResList) {
			String courses = skillRes.getCourses();

			List<RelatedCourse> relatedCourseList = Lists.newArrayList();
			List<CourseStudyProcessBo> processBos = Lists.newArrayList();

			boolean isDeleted = relatedCourseDao.setCourseResult(courses, relatedCourseList,
					processBos, related_course_type);
			skillRes.setCourses(JsonUtil.toJsonString(relatedCourseList));
			skillRes.setCourseList(relatedCourseList);
			if (RoleDetailRes.RELATED_COURSE_STUDY.equals(related_course_type)) {
				skillRes.setProcess(getProcess(processBos));
				skillRes.setCourses("");
				skillRes.setCourseList(null);
				skillRes.setCourseStudyProcessBoList(processBos);
			} else if (RoleDetailRes.RELATED_COURSE_ALL.equals(related_course_type)) {
				skillRes.setProcess(getProcess(processBos));
				skillRes.setCourseStudyProcessBoList(processBos);
			}

			if (isDeleted) {

				// 修改关联表
				Skill skill = new Skill();
				BeanUtils.copyProperties(skillRes, skill);
				courses = courseRelatedService.saveCourseRelatedList(relatedCourseList,
						skill.getResourceId(), CourseRelated.RELATED_TYPE_SKILL);
				skill.setCourses(courses);

				skillList.add(skill);
			}

		}
		updateBatchById(skillList);
	}

	private Double getProcess(List<CourseStudyProcessBo> processBos) {
		Double lessonCount = Double.valueOf(0);

		Double completeCount = Double.valueOf(0);
		for (CourseStudyProcessBo courseStudyProcessBo : processBos) {
			lessonCount += courseStudyProcessBo.getLessonCount();
			completeCount += courseStudyProcessBo.getCompleteCount();
		}
		if (lessonCount <= 0.0) {
			return lessonCount;
		} else {
			return completeCount / lessonCount;
		}
	}

	@Override
	public List<CourseConfig> getCourseConfigs(Skill skill) {
		return JsonUtil.toJsonObjectArray(skill.getCourses(), CourseConfig.class);
	}
}
