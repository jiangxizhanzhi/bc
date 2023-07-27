package com.cyberpeace.rtks.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.res.RoleDetailRes;
import com.cyberpeace.rtks.dao.entity.CourseName;
import com.cyberpeace.rtks.service.CourseNameService;
import com.cyberpeace.rtks.service.RelatedCourseService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class RelatedCourseServiceImpl implements RelatedCourseService {

	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseNameService courseNameService;

	@Autowired
	private CourseStatisticsService courseStatisticsService;

	@Override
	public boolean setCourseResult(UserInfo userInfo, String courses,
			List<RelatedCourse> relatedCourseList, List<CourseStudyProcessBo> processBos,
			String relatedCourseType) {
		boolean isDeleted = false;
		UserInfoContext.setUserContext(userInfo);
		try {
			relatedCourseList.addAll(JsonUtil.toJsonObjectArray(courses, RelatedCourse.class));
		} catch (Exception e) {
			// json转换错误
			log.info("[RTKS] json parse error:" + courses);
		}

		List ids = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			ids.add(relatedCourse.getResourceId());
		}

		if (ids.size() > 0) {

			// 先查询已存在的课程id
			List<String> existIds = courseService.idsByNotDeleteIds(ids);

			// 再获取不存在的课程id
			List<String> notExistIds = Lists.newArrayList();
			if (existIds.size() < ids.size()) {
				Iterator<RelatedCourse> iterator = relatedCourseList.iterator();
				while (iterator.hasNext()) {
					RelatedCourse relatedCourse = iterator.next();

					if (existIds.indexOf(relatedCourse.getResourceId()) == -1) {
						notExistIds.add(relatedCourse.getResourceId());
					}
				}
			}
			// 调用本地接口获取课程内容列表
			List<CourseName> courseNames = courseNameService.selectByIds(notExistIds);

			List<CourseInfoBo> courseList = Lists.newArrayList();
			BeanUtils.deepCopyList(courseNames, CourseInfoBo.class);

			for (CourseName courseName : courseNames) {

				CourseInfoBo course = BeanUtils.copy(courseName, CourseInfoBo.class);
				course.setCategoryId(courseName.getDirection());

				courseList.add(course);

				CourseStudyProcessBo courseStudyProcessBo = BeanUtils.copy(courseName,
						CourseStudyProcessBo.class);

				courseStudyProcessBo.setCourseId(courseName.getResourceId());
				courseStudyProcessBo.setCourseName(courseName.getName());
				courseStudyProcessBo.setLessonCount(courseName.getLessonCount());
				courseStudyProcessBo.setInterview(courseName.getInterview());
				courseStudyProcessBo.setCourseId(courseName.getResourceId());

				processBos.add(courseStudyProcessBo);
			}

			// 增加远程接口的课程列表
			if (existIds.size() > 0) {
				// 查询课程或者学习情况
				if (RoleDetailRes.RELATED_COURSE_STUDY.equals(relatedCourseType)
						|| RoleDetailRes.RELATED_COURSE_ALL.equals(relatedCourseType)) {
					processBos.addAll(
							courseStatisticsService.statisticByIds(existIds, userInfo.getUserId()));

				}
				courseList.addAll(courseService.selectByIds(existIds));
			}

			Map<String, CourseInfoBo> allCourseMap = getAllCourseMap(courseList);

			for (RelatedCourse relatedCourse : relatedCourseList) {
				Integer weight = relatedCourse.getWeight();

				CourseInfoBo courseInfoBo = allCourseMap.get(relatedCourse.getResourceId());

				if (courseInfoBo != null) {
					org.springframework.beans.BeanUtils.copyProperties(courseInfoBo, relatedCourse);

					relatedCourse.setDirection(courseInfoBo.getCategoryId());
					relatedCourse.setEBook(courseInfoBo.getCourseType());
					relatedCourse.setWeight(weight);
				}

			}
		}

		return isDeleted;
	}

	private Map<String, CourseInfoBo> getAllCourseMap(List<CourseInfoBo> courseList) {
		Map<String, CourseInfoBo> map = Maps.newHashMap();

		for (CourseInfoBo courseInfoBo : courseList) {
			map.put(courseInfoBo.getResourceId(), courseInfoBo);
		}

		return map;
	}

}
