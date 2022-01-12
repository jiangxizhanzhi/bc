package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CourseValidateHandler {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 校验课程权限
	 * @param courseId
	 * @param authContext
	 * @return
	 */
	public void validateCoursePermission(String courseId, AuthContext authContext) {
		List<String> authIds = courseMapper.selectByAuth(authContext);
		if (!authIds.contains(courseId)) {
			log.error("Course not permission! course_id=[{}]", courseId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}
	}

	/**
	 * 校验课时权限
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	public CourseLesson validateLessonPermission(String lessonId, AuthContext authContext) {
		CourseLesson courseLesson = courseLessonMapper.selectByPublic(lessonId,
				authContext.getIsWeb());
		if (null == courseLesson) {
			log.error("Lesson not public! lesson_id=[{}]", lessonId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_PERMISSION);
		}

		validateCoursePermission(courseLesson.getCourseId(), authContext);
		return courseLesson;
	}

	/**
	 * 校验课时是否存在
	 * @param lessonId
	 * @param isPublic
	 * @return
	 */
	public CourseLesson validateLessonExist(String lessonId, Boolean isPublic) {
		CourseLesson lesson = courseLessonMapper.selectByPublic(lessonId, isPublic);
		if (null == lesson) {
			log.error("Lesson not found! lesson_id=[{}]", lessonId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return lesson;
	}

	/**
	 * 课程数据存在校验
	 * @param courseId
	 * @return
	 */
	public Course validateCourseExist(String courseId) {
		Course course = courseMapper.selectById(courseId);
		if (null == course) {
			log.error("Course not found! course_id=[{}]", courseId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return course;
	}

	/**
	 * 课程名称存在性校验
	 * @param courseName
	 * @return
	 */
	public void validateCourseNameExist(String courseName) {
		String courseId = courseMapper.selectByName(courseName);
		if (StringUtils.isNotBlank(courseId)) {
			log.error("Course is exist! course_name=[{}]", courseName);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}

	/**
	 * 课程编号存在性校验
	 * @param courseNum
	 * @return
	 */
	public void validateCourseNumExist(String courseNum) {
		Course course = courseMapper.selectByCourseNum(courseNum);
		if (null != course) {
			log.error("Course num is exist! course_num=[{}]", courseNum);
			throw new BusinessException(OjBusinessErrorCode.COURSE_NUM_IS_EXIST);
		}
	}

	/**
	 * 课时名称重复性校验
	 * @param courseId
	 * @param name
	 */
	public void validateLessonNameExist(String courseId, String name) {
		String lessonId = courseLessonMapper.selectByCourseAndName(courseId, name);
		if (StringUtils.isNotBlank(lessonId)) {
			log.error("Lesson is exist! course_id=[{}], lesson_name=[{}]", lessonId, name);
			throw new BusinessException(OjBusinessErrorCode.NAME_IS_EXIST);
		}
	}
}
