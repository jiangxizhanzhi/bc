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

/**
 * 课程课Mapper
 *
 * @author SN
 * @date 2024/11/11
 */
@Mapper
@Repository
public interface CourseLessonMapper extends BaseMapper<CourseLesson> {

	/**
	 * 按课程列出
	 *
	 * @param courseIds 课程id列表
	 * @param isPublic  是公共
	 * @return {@code List<CourseLesson> }
	 */
	List<CourseLesson> listByCourses(@Param("courseIds") List<String> courseIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询课程下课时
	 *
	 * @param courseId
	 * @param isPublic true=公开 false=全部
	 * @return
	 */
	List<CourseLesson> selectByCourse(@Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 分页由上下文
	 *
	 * @param page     分页
	 * @param context  上下文
	 * @param isPublic 是公共
	 * @param type     类型
	 * @return {@code IPage<CourseLesson> }
	 */
	IPage<CourseLesson> pageByContext(Page page, @Param("context") LessonContext context,
			@Param("isPublic") Boolean isPublic,@Param("type") Integer type);

	/**
	 * 查询由公共
	 *
	 * @param resourceId 资源id
	 * @param isPublic   是公共
	 * @return {@code CourseLesson }
	 */
	CourseLesson selectByPublic(@Param("resourceId") String resourceId,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 按课程删除
	 *
	 * @param courseIds 课程id列表
	 */
	void deleteByCourse(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询id列表由课程
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 */
	List<String> selectIdsByCourse(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询由课程和名称
	 *
	 * @param courseId 课程id
	 * @param name     名称
	 * @return {@code String }
	 */
	String selectByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	/**
	 * 查询课程由课程和名称
	 *
	 * @param courseId 课程id
	 * @param name     名称
	 * @return {@code CourseLesson }
	 */
	CourseLesson selectCourseByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	/**
	 * 查询分钟指数
	 *
	 * @param courseId 课程id
	 * @return {@code Integer }
	 */
	Integer selectMinIndex(@Param("courseId") String courseId);

	/**
	 * 查询章计数
	 *
	 * @param courseId 课程id
	 * @return {@code Integer }
	 */
	Integer selectChapterCount(@Param("courseId") String courseId);
}
