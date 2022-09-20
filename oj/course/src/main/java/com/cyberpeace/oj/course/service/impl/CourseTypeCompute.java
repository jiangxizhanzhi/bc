package com.cyberpeace.oj.course.service.impl;

import java.util.List;
import java.util.Map;

import com.cyberpeace.common.baseinterface.Compute;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.google.common.collect.Lists;

import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

public class CourseTypeCompute implements Compute {

	private CourseLessonMapper courseLessonMapper;

	private List<CourseLesson> lessonList;

	public CourseTypeCompute(CourseLessonMapper lessonMapper) {
		this.courseLessonMapper = lessonMapper;
		lessonList = null;
	}

	public CourseTypeCompute(List<CourseLesson> lessonList) {
		this.lessonList = lessonList;
	}

	@Override
	public GenericEnum compute(Object obj) {
		if (!(obj instanceof Course)) {
			throw new BusinessException(OjBusinessErrorCode.PARAM_ERROR);
		}

		Course course = (Course) obj;
		if (CollectionUtils.isEmpty(lessonList)) {
			lessonList = courseLessonMapper.selectByCourse(course.getResourceId(), false);
		}

		Integer maximumType = maximumType(lessonList);
		return EnumUtils.getEnumByCode(maximumType, CourseLesson.Type.class);
	}

	private Integer maximumType(List<CourseLesson> lessons) {
		// 默认文档
		if (CollectionUtils.isEmpty(lessons)) {
			return CourseLesson.Type.DOCUMENT.getCode();
		}

		Map<Integer, List<CourseLesson>> typeMap = StreamApiUtils.groupBy(lessons,
				CourseLesson::getLessonType);
		return sortTypeMap(typeMap).get(CommonConstant.ZERO).getType();
	}

	private List<LessonTypeCount> sortTypeMap(Map<Integer, List<CourseLesson>> typeMap) {
		List<LessonTypeCount> typeCounts = Lists.newArrayList();

		typeMap.forEach((key, value) -> {
			LessonTypeCount typeCount = new LessonTypeCount(key, value.size(), value);
			typeCounts.add(typeCount);
		});

		return StreamApiUtils.sortByColumn(typeCounts, LessonTypeCount::getCount);
	}

}

@Data
class LessonTypeCount {

	public LessonTypeCount(Integer type, Integer count, List<CourseLesson> lessons) {
		this.type = type;
		this.count = count;
		this.lessonList = lessons;
	}

	private Integer type;

	private Integer count;

	private List<CourseLesson> lessonList;
}
