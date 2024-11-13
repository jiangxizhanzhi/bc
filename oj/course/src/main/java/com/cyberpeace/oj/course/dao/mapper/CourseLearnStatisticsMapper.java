package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.controller.res.AdminCourseStatisticsRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsDetailRes;
import com.cyberpeace.oj.course.controller.res.AdminLessonStatisticsRes;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程学习统计学Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface CourseLearnStatisticsMapper extends BaseMapper<LessonRecord> {

	/**
	 * 查询由热
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<Course> }
	 */
	List<Course> selectByHeat(List<String> courseIds);

	/**
	 * 按方向对热量求和
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<Course> }
	 */
	List<Course> sumHeatByDirection(List<String> courseIds);

	/**
	 * 列表课程由分页
	 *
	 * @param page          分页
	 * @param courseContext 课程背景
	 * @param courseIds     课程id列表
	 * @param isPublic      是否公开
	 * @return {@code IPage<AdminCourseStatisticsRes> }
	 */
	IPage<AdminCourseStatisticsRes> listCourseByPage(Page page,
			@Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 列表课由分页
	 *
	 * @param page     分页
	 * @param courseId 课程id
	 * @param isPublic 是否公开
	 * @param type     类型
	 * @param search   搜索
	 * @return {@code IPage<AdminLessonStatisticsRes> }
	 */
	IPage<AdminLessonStatisticsRes> listLessonByPage(Page page, @Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic, Integer type, String search);

	/**
	 * 列表课程练习
	 *
	 * @param lessonIds 课id列表
	 * @return {@code List<AdminLessonStatisticsRes> }
	 */
	List<AdminLessonStatisticsRes> listLessonPractice(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 列出经验教训记录
	 *
	 * @param page     分页
	 * @param userIds  用户id列表
	 * @param lessonId 课id
	 * @return {@code IPage<AdminLessonStatisticsDetailRes> }
	 */
	IPage<AdminLessonStatisticsDetailRes> listLessonLearnedRecord(Page page,
			@Param("userIds") List<String> userIds, @Param("lessonId") String lessonId);

	/**
	 * 详情课由分页
	 *
	 * @param lessonId 课id
	 * @param userIds  用户id列表
	 * @return {@code List<AdminLessonStatisticsDetailRes> }
	 */
	List<AdminLessonStatisticsDetailRes> detailLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);

	/**
	 * 详情exp （英文）课由分页
	 *
	 * @param lessonId 课id
	 * @param userIds  用户id列表
	 * @return {@code List<AdminLessonStatisticsDetailRes> }
	 */
	List<AdminLessonStatisticsDetailRes> detailExpLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);
}
