package com.cyberpeace.rtks.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.cyberpeace.rtks.controller.res.RelatedCourse;
import com.cyberpeace.rtks.controller.res.RoleDetailRes;
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
	private CourseStatisticsService courseStatisticsService;

	@Override
	public boolean setCourseResult(UserInfo userInfo, String courses,
			List<RelatedCourse> relatedCourseList, List<CourseStudyProcessBo> processBos,
			String related_course_type) {
		boolean isDeleted = false;
		UserInfoContext.setUserContext(userInfo);
		try {
			relatedCourseList.addAll(JsonUtil.toJsonObjectArray(courses, RelatedCourse.class));
		} catch (Exception e) {
			log.info("json转换错误：" + courses);
		}

		List ids = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			ids.add(relatedCourse.getResourceId());
		}

		if (ids.size() > 0) {
			List<String> existIds = courseService.idsByNotDeleteIds(ids);

			if (existIds.size() < ids.size()) { // 存在被删除的课程内容,返回true
				Iterator<RelatedCourse> iterator = relatedCourseList.iterator();
				while (iterator.hasNext()) {
					RelatedCourse relatedCourse = iterator.next();
					if (existIds.indexOf(relatedCourse.getResourceId()) == -1) {
						iterator.remove();
					}
				}
				isDeleted = true;
				// 返回true，需要更新课程内容
			}
			if (existIds.size() > 0) {
				// 查询课程或者学习情况
				if (RoleDetailRes.RELATED_COURSE_STUDY.equals(related_course_type)
						|| RoleDetailRes.RELATED_COURSE_ALL.equals(related_course_type)) {
					processBos.addAll(
							courseStatisticsService.statisticByIds(existIds, userInfo.getUserId()));

				}

				List<CourseInfoBo> courseList = courseService.selectByIds(existIds);

				Map<String, CourseInfoBo> allCourseMap = getAllCourseMap(courseList);

				for (RelatedCourse relatedCourse : relatedCourseList) {
					if (allCourseMap.get(relatedCourse.getResourceId()) != null) { // 课程的课时可能为0，导致两边结果不一致，查询为null
						Integer weight = relatedCourse.getWeight();
						BeanUtils.copyProperties(allCourseMap.get(relatedCourse.getResourceId()),
								relatedCourse);
						relatedCourse.setWeight(weight);
					}

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
