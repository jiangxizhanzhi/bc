package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseLessonServiceImpl implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Override
	public List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime) {

		List<LessonRecord> lessonRecords = lessonRecordMapper.selectByTime(userId, startTime,
				endTime);
		return lessonRecords;
	}

	@Override
	public List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons) {
		List<CourseLesson> lessonList = lessons.stream()
				.filter(courseLesson -> recordLesson.contains(courseLesson.getResourceId()))
				.collect(Collectors.toList());

		List<String> courseIds = lessonList.stream().map(CourseLesson::getCourse).distinct().collect(Collectors.toList());
		return courseIds;
	}
}
