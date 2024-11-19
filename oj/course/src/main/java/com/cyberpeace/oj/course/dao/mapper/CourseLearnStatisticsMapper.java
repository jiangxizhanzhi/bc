package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程学习统计学Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface CourseLearnStatisticsMapper extends BaseMapper<LessonRecord> {

	/**
	 * 查询由热
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<Course> }
	 * @throws
	 * @see List<String>
	 * @see List<Course>
	 */
	List<Course> selectByHeat(List<String> courseIds);

	/**
	 * 按方向对热量求和
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<Course> }
	 * @throws
	 * @see List<String>
	 * @see List<Course>
	 */
	List<Course> sumHeatByDirection(List<String> courseIds);

	/**
	 * 列表课程由分页
	 *
	 * @param page          分页
	 * @param courseContext 课程上下文
	 * @param courseIds     课程id列表
	 * @param isPublic      是否公开
	 * @return {@code IPage<AdminCourseStatisticsRes> }
	 * @throws
	 * @see Page<AdminCourseStatisticsRes>
	 * @see CourseContext
	 * @see List<String>
	 * @see Boolean
	 * @see IPage<AdminCourseStatisticsRes>
	 */
	IPage<AdminCourseStatisticsRes> listCourseByPage(Page<AdminCourseStatisticsRes> page,
			@Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 列表课时由分页
	 *
	 * @param page     分页
	 * @param courseId 课程id
	 * @param isPublic 是否公开
	 * @param type     类型
	 * @param search   搜索
	 * @return {@code IPage<AdminLessonStatisticsRes> }
	 * @throws
	 * @see Page<AdminLessonStatisticsRes>
	 * @see String
	 * @see Boolean
	 * @see Integer
	 * @see String
	 * @see IPage<AdminLessonStatisticsRes>
	 */
	IPage<AdminLessonStatisticsRes> listLessonByPage(Page<AdminLessonStatisticsRes> page, @Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic, Integer type, String search);

	/**
	 * 列表课时练习
	 *
	 * @param lessonIds 课时id列表
	 * @return {@code List<LessonPaperEvaluate> }
	 * @throws
	 * @see List<String>
	 * @see List<LessonPaperEvaluate>
	 */
	List<LessonPaperEvaluate> listLessonPractice(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 列表课时博学记录
	 *
	 * @param page     分页
	 * @param userIds  用户id列表
	 * @param lessonId 课时id
	 * @return {@code IPage<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see Page<AdminLessonStatisticsDetailRes>
	 * @see List<String>
	 * @see String
	 * @see IPage<AdminLessonStatisticsDetailRes>
	 */
	IPage<AdminLessonStatisticsDetailRes> listLessonLearnedRecord(Page<AdminLessonStatisticsDetailRes> page,
			@Param("userIds") List<String> userIds, @Param("lessonId") String lessonId);

	/**
	 * 详情课时由分页
	 *
	 * @param lessonId 课时id
	 * @param userIds  用户id列表
	 * @return {@code List<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<AdminLessonStatisticsDetailRes>
	 */
	List<AdminLessonStatisticsDetailRes> detailLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);

	/**
	 * 详情exp （英文）课时由分页
	 *
	 * @param lessonId 课时id
	 * @param userIds  用户id列表
	 * @return {@code List<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<AdminLessonStatisticsDetailRes>
	 */
	List<AdminLessonStatisticsDetailRes> detailExpLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);
}
