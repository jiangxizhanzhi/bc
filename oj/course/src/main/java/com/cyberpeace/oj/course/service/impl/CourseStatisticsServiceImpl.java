package com.cyberpeace.oj.course.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.google.common.collect.Lists;

/**
 * 课程统计学Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
public class CourseStatisticsServiceImpl implements CourseStatisticsService {

	/**
	 * 课程Mapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 课记录Mapper
	 */
	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	/**
	 * 统计由id列表
	 *
	 * @param courseIds 课程id列表
	 * @param userId    用户id
	 * @return {@code List<CourseStudyProcessBo> }
	 */
	@Override
	public List<CourseStudyProcessBo> statisticByIds(List<String> courseIds, String userId) {

		if (CollectionUtils.isEmpty(courseIds)) {
			return Lists.newArrayList();
		}

		// 过滤删除的课程
		List<Course> courses = courseMapper.selectBatchIds(courseIds);
		List<String> ids = courses.stream().map(Course::getResourceId).collect(Collectors.toList());
		if (CollectionUtils.isEmpty(ids)) {
			return Lists.newArrayList();
		}

		// 查询有权访问课程
		AuthContext authContext = AuthContextUtils.constructAuth(UserInfoContext.getUserContext(),
				true);
		List<String> webCourses = courseMapper.selectByAuth(authContext);

		// 课程数量 以课程编号进行分组
		List<CourseLesson> courseLessons = courseLessonMapper.listByCourses(ids, authContext.getIsWeb());
		Map<String, List<CourseLesson>> courseMap = courseLessons.stream()
				.collect(Collectors.groupingBy(CourseLesson::getCourseId));
		List<String> lessonIds = courseLessons.stream().map(CourseLesson::getResourceId)
				.collect(Collectors.toList());

		// 学习记录
		List<LessonRecord> lessonRecords = lessonRecordMapper.listByLessonAndUser(userId,
				lessonIds);

		Map<String, List<LessonRecord>> recordMap = lessonRecords.stream().collect(Collectors.groupingBy(LessonRecord::getLessonId));

		List<CourseStudyProcessBo> studyProcessBos = courses.stream().map(course -> {

			CourseStudyProcessBo studyProcessBo = new CourseStudyProcessBo();
			studyProcessBo.setCourseId(course.getResourceId());
			studyProcessBo.setCourseName(course.getName());
			studyProcessBo.setDifficulty(course.getDifficulty());

			// 计算已学习课时
			List<LessonRecord> studyRecord = Lists.newArrayList();
			List<CourseLesson> lessonList = courseMap.get(course.getResourceId());
			if (lessonList == null) {
				lessonList = Lists.newArrayList();
			}
			studyProcessBo.setLessonCount(lessonList.size());
			List<CourseLesson> study = lessonList.stream().filter(lesson -> {
				if (recordMap.get(lesson.getResourceId()) != null && recordMap.get(lesson.getResourceId()).size() > 0) {
					return studyRecord.add(recordMap.get(lesson.getResourceId()).get(0));
				}
				return false;
			}).collect(Collectors.toList());
			studyProcessBo.setCompleteCount(studyRecord.size());

			// 课程为零或者课程无权限,都禁止访问访问
			if (CollectionUtils.isNotEmpty(lessonList)) {
				studyProcessBo
						.setInterview(course.getIsPublic() && CollectionUtils.isNotEmpty(webCourses)
								&& webCourses.contains(course.getResourceId()));
			} else {
				studyProcessBo.setInterview(Boolean.FALSE);
			}

			// 计算课时类型数量
			Map<Integer, List<CourseLesson>> lessonMap = lessonList.stream()
					.collect(Collectors.groupingBy(CourseLesson::getType));
			lessonMap.forEach((key, value) -> {
				if (key.intValue() == CourseLesson.LessonType.THEORY.getCode().intValue()) {
					studyProcessBo.setTheoryCount(value.size());
				} else {
					studyProcessBo.setExerciseCount(value.size());
				}
			});

			// 课时分类
			Map<Integer, List<CourseLesson>> studyLessonMap = study.stream()
					.collect(Collectors.groupingBy(CourseLesson::getType));
			studyLessonMap.forEach((key, value) -> {
				if (key.intValue() == CourseLesson.LessonType.THEORY.getCode().intValue()) {
					studyProcessBo.setCompleteTheory(value.size());
				} else {
					studyProcessBo.setCompleteExercise(value.size());
				}
			});

			// 计算最后学习时间
			if (CollectionUtils.isNotEmpty(studyRecord)) {
				LessonRecord lessonRecord = studyRecord.stream()
						.max(Comparator.comparing(LessonRecord::getUpdateTime)).get();
				studyProcessBo.setLastStudyTime(lessonRecord.getUpdateTime());
			}

			// 计算课程学习时长
			int studyDuration = study.stream().mapToInt(CourseLesson::getDuration).sum();
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
