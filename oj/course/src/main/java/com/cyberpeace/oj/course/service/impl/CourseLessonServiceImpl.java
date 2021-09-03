package com.cyberpeace.oj.course.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.oj.course.dao.entity.LessonFile;
import com.cyberpeace.oj.course.dao.entity.LessonScene;
import com.cyberpeace.oj.course.dao.mapper.LessonFileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseLessonService;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

@Service
public class CourseLessonServiceImpl implements CourseLessonService {

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LessonFileMapper lessonFileMapper;

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

	@Override
	public LessonScene getLessonScene(UserInfo user, String lessonId) {
		// 获取启动的场景 优先从课堂获取 todo
		return null;
	}

	@Override
	public LessonInfoBo getLessonDetail(String courseId, String lessonId) {
		CourseLesson lesson = courseLessonMapper.selectById(lessonId);
		// 基本信息拷贝
		LessonInfoBo infoBo = BeanUtils.copy(lesson, LessonInfoBo.class);

		// 课时文件组装
		String attachments = infoBo.getAttachments();
		if (StringUtils.isNotBlank(attachments)) {
			String[] fileIds = attachments.split(",");
			List<LessonFile> files = lessonFileMapper.selectBatchIds(Arrays.asList(fileIds));
			infoBo.setLessonFile(files);
		}

		// 上节和下节课程查询

		// 获取场景信息



		return null;
	}
}
