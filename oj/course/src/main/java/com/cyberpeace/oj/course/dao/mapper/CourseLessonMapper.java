package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;

@Mapper
@Repository
public interface CourseLessonMapper extends BaseMapper<CourseLesson> {

	List<CourseLesson> listByCourses(@Param("courseIds") List<String> courseIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询课程下课时 
	 * @param courseId
	 * @param isPublic true=公开 false=全部
	 * @return
	 */
	List<CourseLesson> selectByCourse(@Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic);

	IPage<CourseLesson> pageByContext(Page page, @Param("context") LessonContext context,
			@Param("isPublic") Boolean isPublic,@Param("type") Integer type);

	CourseLesson selectByPublic(@Param("resourceId") String resourceId,
			@Param("isPublic") Boolean isPublic);

	void deleteByCourse(@Param("courseIds") List<String> courseIds);

	List<String> selectIdsByCourse(@Param("courseIds") List<String> courseIds);

	String selectByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	CourseLesson selectCourseByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	Integer selectMinIndex(@Param("courseId") String courseId);

	Integer selectChapterCount(@Param("courseId") String courseId);

    List<CourseLesson> selectByHeat(@Param("courseIds") List<String> courseIds);
}
