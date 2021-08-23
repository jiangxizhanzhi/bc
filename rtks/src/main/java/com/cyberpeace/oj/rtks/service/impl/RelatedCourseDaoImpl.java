package com.cyberpeace.oj.rtks.service.impl;

import com.cyberpeace.oj.common.util.JsonUtil;
import com.cyberpeace.oj.course.service.CourseService;
import com.cyberpeace.oj.course.service.bo.CourseInfoBo;
import com.cyberpeace.oj.rtks.dao.entity.RelatedCourse;
import com.cyberpeace.oj.rtks.service.RelatedCourseService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class RelatedCourseDaoImpl implements RelatedCourseService {

	@Autowired
	private CourseService courseService;

	@Override
	public boolean setCourseResult(String courses, List<RelatedCourse> relatedCourseList) {
		boolean isDeleted = false;

		try {
			relatedCourseList.addAll(JsonUtil.toJsonObjectArray(courses, RelatedCourse.class));
		} catch (Exception e) {
			log.info("json转换错误：" + courses);
		}

		List ids = Lists.newArrayList();

		for (RelatedCourse relatedCourse : relatedCourseList) {
			ids.add(relatedCourse.getResource_id());
		}

		if (ids.size() > 0) {
			List<String> existIds = courseService.idsByNotDeleteIds(ids);

			if (existIds.size() < ids.size()) { // 存在被删除的课程内容,返回true
				Iterator<RelatedCourse> iterator = relatedCourseList.iterator();
				while (iterator.hasNext()) {
					RelatedCourse relatedCourse = iterator.next();
					if (existIds.indexOf(relatedCourse.getResource_id()) == -1) {
						iterator.remove();
					}
				}
				isDeleted = true;
				// 返回true，需要更新课程内容
			}
			if (existIds.size() > 0) {
				List<CourseInfoBo> courseList = courseService.selectByIds(existIds);

				Map<String, String> courseNameMap = getAllCourseNameByIds(courseList);
				Map<String, Integer> courseEbookMap = getAllCourseEbookByIds(courseList);

				for (RelatedCourse knowledgeCourse : relatedCourseList) {
					knowledgeCourse.setName(courseNameMap.get(knowledgeCourse.getResource_id()));
					knowledgeCourse.setEBook(courseEbookMap.get(knowledgeCourse.getResource_id()));

				}
			}
		}

		return isDeleted;
	}

	private Map<String, String> getAllCourseNameByIds(List<CourseInfoBo> courseList) {
		Map<String, String> map = Maps.newHashMap();

		for (CourseInfoBo courseInfoBo : courseList) {
			map.put(courseInfoBo.getResourceId(), courseInfoBo.getName());
		}

		return map;
	}

	private Map<String, Integer> getAllCourseEbookByIds(List<CourseInfoBo> courseList) {
		Map<String, Integer> map = Maps.newHashMap();

		for (CourseInfoBo courseInfoBo : courseList) {
			map.put(courseInfoBo.getResourceId(), courseInfoBo.getEBook());
		}

		return map;
	}
}
