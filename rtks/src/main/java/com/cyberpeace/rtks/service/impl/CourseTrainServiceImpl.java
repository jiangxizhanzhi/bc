package com.cyberpeace.rtks.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.exception.InvalidParamException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.DataTransferUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.res.CourseRelatedRes;
import com.cyberpeace.rtks.dao.entity.Role;
import com.cyberpeace.rtks.dao.mapper.RoleMapper;
import com.cyberpeace.rtks.service.CourseTrainService;
import com.cyberpeace.rtks.service.KnowledgeService;
import com.cyberpeace.rtks.service.SkillService;
import com.cyberpeace.rtks.service.bo.CourseKnowledgeSkillBo;
import com.cyberpeace.rtks.service.bo.RoleCourseBo;
import com.google.common.collect.Lists;

@Service
public class CourseTrainServiceImpl implements CourseTrainService {

	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private KnowledgeService knowledgeService;

	@Autowired
	private SkillService skillService;

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Autowired
	private CourseService courseService;

	@Override
	public List<CourseKnowledgeSkillBo> getCourseKnowledge(String userId, String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			throw new InvalidParamException("未检索到相应岗位!");
		}

		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
				String.class);
		List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);
		List<CourseStudyProcessBo> processBos = courseStatisticsService.statisticByIds(courseIds,
				userId);

		List<CourseInfoBo> courses = courseService.selectByIds(courseIds);
		Map<String, CourseInfoBo> infoMap = courses.stream().collect(
				Collectors.toMap(CourseInfoBo::getResourceId, courseInfoBo -> courseInfoBo));

		Map<String, List<CourseRelatedRes>> courseMap = knowledgeService
				.getKnowledgeByCourseId(courseIds);

		List<CourseKnowledgeSkillBo> boList = processBos.stream().map(bo -> {
			CourseKnowledgeSkillBo knowledgeBo = BeanUtils.deepCopy(infoMap.get(bo.getCourseId()),
					CourseKnowledgeSkillBo.class);
			knowledgeBo.setKnowledges(courseMap.get(bo.getCourseId()));
			knowledgeBo.setProcess(MathUtils.ceil(bo.getProcess()));
			knowledgeBo.setCompleteTheory(bo.getCompleteTheory());
			knowledgeBo.setCompleteExercise(bo.getCompleteExercise());
			knowledgeBo.setTheoryCount(DataTransferUtils.nullToZero(bo.getTheoryCount()));
			knowledgeBo.setExerciseCount(bo.getExerciseCount());
			knowledgeBo.setCompleteCount(bo.getCompleteCount());
			knowledgeBo.setLastStudyTime(bo.getLastStudyTime());

			return knowledgeBo;
		}).collect(Collectors.toList());
		return boList;
	}

	@Override
	public List<CourseKnowledgeSkillBo> getCourseSkillByRoleId(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			throw new InvalidParamException("未检索到相应岗位!");
		}

		List<String> skillIds = JsonUtil.toJsonObjectArray(role.getSkillIds(), String.class);
		List<String> courseIds = skillService.getCourseByIds(skillIds);

		List<CourseInfoBo> courses = courseService.selectByIds(courseIds);

		Map<String, List<CourseRelatedRes>> courseMap = skillService.getSkillByCourseId(courseIds);

		List<CourseKnowledgeSkillBo> boList = courses.stream().map(bo -> {
			CourseKnowledgeSkillBo knowledgeSkillBo = BeanUtils.deepCopy(bo,
					CourseKnowledgeSkillBo.class);
			knowledgeSkillBo.setSkills(courseMap.get(bo.getResourceId()));

			return knowledgeSkillBo;
		}).collect(Collectors.toList());

		return boList;
	}

	@Override
	public List<CourseKnowledgeSkillBo> getCourseKnowledgeByRoleId(String roleId) {
		Role role = roleMapper.selectById(roleId);
		if (null == role) {
			throw new InvalidParamException("未检索到相应岗位!");
		}

		List<String> knowledgeIds = JsonUtil.toJsonObjectArray(role.getKnowledgeIds(),
				String.class);
		List<String> courseIds = knowledgeService.getCourseByIds(knowledgeIds);

		List<CourseInfoBo> courses = courseService.selectByIds(courseIds);

		Map<String, List<CourseRelatedRes>> courseMap = knowledgeService
				.getKnowledgeByCourseId(courseIds);

		List<CourseKnowledgeSkillBo> boList = courses.stream().map(bo -> {
			CourseKnowledgeSkillBo knowledgeSkillBo = BeanUtils.deepCopy(bo,
					CourseKnowledgeSkillBo.class);
			knowledgeSkillBo.setKnowledges(courseMap.get(bo.getResourceId()));

			return knowledgeSkillBo;
		}).collect(Collectors.toList());

		return boList;
	}

	@Override
	public List<RoleCourseBo> getRoleCoursePath(String roleId) {
		Role role = roleMapper.selectById(roleId);

		String coursesStr = role.getKnowledgeCourses();
		List<String> courseIds = JsonUtil.toJsonObjectArray(coursesStr, String.class);
		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}

		List<CourseInfoBo> infoBos = courseService.selectByIds(courseIds);
		List<RoleCourseBo> courseBos = infoBos.stream()
				.map(courseInfoBo -> BeanUtils.copy(courseInfoBo, RoleCourseBo.class))
				.collect(Collectors.toList());
		return courseBos;
	}
}
