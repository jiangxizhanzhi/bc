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

@Mapper
public interface CourseLearnStatisticsMapper extends BaseMapper<LessonRecord> {

	List<Course> selectByHeat(List<String> courseIds);

	List<Course> sumHeatByDirection(List<String> courseIds);

	IPage<AdminCourseStatisticsRes> listCourseByPage(Page<AdminCourseStatisticsRes> page,
			@Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	IPage<AdminLessonStatisticsRes> listLessonByPage(Page<AdminLessonStatisticsRes> page, @Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic, Integer type, String search);

	List<LessonPaperEvaluate> listLessonPractice(@Param("lessonIds") List<String> lessonIds);

	IPage<AdminLessonStatisticsDetailRes> listLessonLearnedRecord(Page<AdminLessonStatisticsDetailRes> page,
			@Param("userIds") List<String> userIds, @Param("lessonId") String lessonId);

	List<AdminLessonStatisticsDetailRes> detailLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);

	List<AdminLessonStatisticsDetailRes> detailExpLessonByPage(@Param("lessonId") String lessonId,
			@Param("userIds") List<String> userIds);
}
