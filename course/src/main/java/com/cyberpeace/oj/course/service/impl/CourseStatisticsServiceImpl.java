package com.cyberpeace.oj.course.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.Lesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.google.common.collect.Lists;

@Service
public class CourseStatisticsServiceImpl implements CourseStatisticsService {

	@Autowired
	private CourseMapper courseMapper;

	@Autowired
	private CourseLessonMapper courseLessonMapper;

	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	@Override
	public List<CourseStudyProcessBo> statisticByIds(@NotEmpty List<String> courseIds,
			String userId) {

		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}

		// 过滤删除的课程
		List<Course> courses = courseMapper.selectBatchIds(courseIds);
		List<String> ids = courses.stream().map(Course::getResourceId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return Lists.newArrayList();
		}

		// 课程数量 以课程编号进行分组
		List<Lesson> lessons = courseLessonMapper.listByCourses(ids);
		Map<String, List<Lesson>> courseMap = lessons.stream()
				.collect(Collectors.groupingBy(Lesson::getCourse));
		List<String> lessonIds = lessons.stream().map(Lesson::getResourceId)
				.collect(Collectors.toList());

		// 学习记录
		List<LessonRecord> lessonRecords = lessonRecordMapper.listByLessonAndUser(userId,
				lessonIds);
		Map<String, LessonRecord> recordMap = lessonRecords.stream()
				.collect(Collectors.toMap(LessonRecord::getLesson, lessonRecord -> lessonRecord));

		List<CourseStudyProcessBo> studyProcessBos = courses.stream().map(course -> {

			CourseStudyProcessBo studyProcessBo = new CourseStudyProcessBo();
			studyProcessBo.setCourseId(course.getResourceId());
			studyProcessBo.setCourseName(course.getName());
			studyProcessBo.setDifficulty(course.getDifficulty());

			// 计算已学习课时
			List<LessonRecord> studyRecord = Lists.newArrayList();
			List<Lesson> lessonList = courseMap.get(course.getResourceId());
			if (lessonList == null) {
				lessonList = Lists.newArrayList();
			}
			studyProcessBo.setLessonCount(lessonList.size());
			List<Lesson> study = lessonList.stream().filter(lesson -> {
				if (recordMap.get(lesson.getResourceId()) != null) {
					return studyRecord.add(recordMap.get(lesson.getResourceId()));
				}
				return false;
			}).collect(Collectors.toList());
			studyProcessBo.setCompleteCount(studyRecord.size());

			// 计算课时类型数量
			Map<Integer, List<Lesson>> lessonMap = lessonList.stream()
					.collect(Collectors.groupingBy(Lesson::getType));
			lessonMap.forEach((key, value) -> {
				if (key.intValue() == Lesson.TYPE_THEORY) {
					studyProcessBo.setTheoryCount(value.size());
				} else {
					studyProcessBo.setExerciseCount(value.size());
				}
			});

			// 课时分类
			Map<Integer, List<Lesson>> studyLessonMap = study.stream()
					.collect(Collectors.groupingBy(Lesson::getType));
			studyLessonMap.forEach((key, value) -> {
				if (key.intValue() == Lesson.TYPE_THEORY) {
					studyProcessBo.setCompleteTheory(value.size());
				} else {
					studyProcessBo.setCompleteExercise(value.size());
				}
			});

			// 计算最后学习时间
			if (CollectionUtils.isNotEmpty(studyRecord)) {
				LessonRecord lessonRecord = studyRecord.stream()
						.max(Comparator.comparing(LessonRecord::getEndTime)).get();
				studyProcessBo.setLastStudyTime(lessonRecord.getEndTime());
			}

			// 计算课程学习时长
			int studyDuration = study.stream().mapToInt(Lesson::getDuration).sum();
			studyProcessBo.setCompleteDuration(studyDuration);

			// 计算进度
			studyProcessBo.setProcess(
					MathUtils.divide(new Double(studyProcessBo.getCompleteCount() * 100),
							new Double(studyProcessBo.getLessonCount())));

			return studyProcessBo;
		}).collect(Collectors.toList());

		return studyProcessBos;
	}
}
