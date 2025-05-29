package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.AuthContextUtils;
import com.cyberpeace.common.util.MathUtils;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.mapper.CourseLessonMapper;
import com.cyberpeace.oj.course.dao.mapper.CourseMapper;
import com.cyberpeace.oj.course.dao.mapper.LessonRecordMapper;
import com.cyberpeace.oj.course.service.CourseStatisticsService;
import com.cyberpeace.oj.course.service.bo.CourseStudyProcessBo;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 课程统计学Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseStatisticsService
 * @since 1.0.0
 */
/**
 * 这段代码是一个用于统计用户在课程中的学习进度的服务实现类。以下是详细的分析： 
 * ### 类和方法概述 
 * - **类名**: `CourseStatisticsServiceImpl` 
 * - **接口实现**: `CourseStatisticsService` 
 * - **功能**: 统计指定课程ID列表中用户的课程学习进度。 
 * ### 主要成员变量 
 * - `courseMapper`: 用于操作课程数据。 
 * - `courseLessonMapper`: 用于操作课程课时数据。 
 * - `lessonRecordMapper`: 用于操作学习记录数据。 
 * ### 主要方法 
 * - **方法名**: `statisticByIds` 
 * - **参数**: 
 * - `List<String> courseIds`: 需要统计的课程ID列表。 
 * - `String userId`: 用户ID。 
 * - **返回值**: `List<CourseStudyProcessBo>`: 每个课程的学习进度信息。 
 * ### 方法逻辑 
 * 1. **输入校验**: 
 * - 如果`courseIds`为空，则直接返回空列表。 
 * 2. **查询课程数据**: 
 * - 根据`courseIds`查询课程信息，并过滤掉被删除的课程。 
 * 3. **查询有权访问的课程**: 
 * - 使用`AuthContext`来过滤出用户有权访问的课程。 
 * 4. **查询课程课时数据**: 
 * - 根据课程ID列表查询课程课时数据，并按课程ID分组。 
 * 5. **查询学习记录数据**: 
 * - 根据用户ID和课时ID查询学习记录数据，并按课时ID分组。 
 * 6. **构建课程学习进度对象**: 
 * - 遍历每门课程，构建`CourseStudyProcessBo`对象，并填充相关字段： 
 * - 设置课程ID、课程名称、难度等基本信息。 
 * - 计算已学习的课时数和完成的课时数。 
 * - 判断课程是否有权访问。 
 * - 计算理论课时和练习课时的数量及已完成的数量。 
 * - 计算最后学习时间。 
 * - 计算课程学习总时长。 
 * - 计算课程学习进度百分比。 
 * 7. **返回结果**: 
 * - 返回包含所有课程学习进度信息的列表。 
 * ### 总结 
 * 该方法通过多步骤的数据查询和处理，最终生成了每个课程的学习进度信息。主要涉及数据库查询、数据过滤、分组统计和计算等操作。代码结构清晰，逻辑严谨，适合用于课程学习进度的统计场景。 
**/
@Service
public class CourseStatisticsServiceImpl implements CourseStatisticsService {

	/**
	 * 课程Mapper
	 *
	 * @see CourseMapper
	 */
	@Autowired
	private CourseMapper courseMapper;

	/**
	 * 课程课Mapper
	 *
	 * @see CourseLessonMapper
	 */
	@Autowired
	private CourseLessonMapper courseLessonMapper;

	/**
	 * 课记录Mapper
	 *
	 * @see LessonRecordMapper
	 */
	@Autowired
	private LessonRecordMapper lessonRecordMapper;

	/**
	 * 统计由id列表
	 *
	 * @param courseIds 课程id列表
	 * @param userId    用户id
	 * @return {@code List<CourseStudyProcessBo> }
	 * @throws
	 * @see List<String>
	 * @see String
	 * @see List<CourseStudyProcessBo>
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

