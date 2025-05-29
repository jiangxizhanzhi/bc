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
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseLearnStatisticsMapper` 的 MyBatis Mapper 接口。该接口继承了 `BaseMapper<LessonRecord>`，并扩展了一些自定义的方法用于课程和课程章节的学习统计。以下是对其主要功能的简要分析： 
 * ### 1. 接口定义 
 * - **继承 `BaseMapper<LessonRecord>`**：继承自 MyBatis Plus 提供的 `BaseMapper`，提供了基本的 CRUD 操作。 
 * - **使用 `@Mapper` 注解**：表明这是一个 MyBatis 的 Mapper 接口。 
 * ### 2. 方法定义 
 * - **`selectByHeat(List<String> courseIds)`**： 
 * - 根据给定的课程 ID 列表查询课程，并按热度排序。 
 * - 返回一个 `List<Course>`。 
 * - **`sumHeatByDirection(List<String> courseIds)`**： 
 * - 按方向对课程的热度进行求和。 
 * - 返回一个 `List<Course>`。 
 * - **`listCourseByPage(Page page, CourseContext courseContext, List<String> courseIds, Boolean isPublic)`**： 
 * - 按分页条件查询课程统计数据。 
 * - 返回一个 `IPage<AdminCourseStatisticsRes>`。 
 * - **`listLessonByPage(Page page, String courseId, Boolean isPublic, Integer type, String search)`**： 
 * - 按分页条件查询课程章节统计数据。 
 * - 返回一个 `IPage<AdminLessonStatisticsRes>`。 
 * - **`listLessonPractice(List<String> lessonIds)`**： 
 * - 查询指定课程章节的练习数据。 
 * - 返回一个 `List<AdminLessonStatisticsRes>`。 
 * - **`listLessonLearnedRecord(Page page, List<String> userIds, String lessonId)`**： 
 * - 按分页条件查询指定用户在指定课程章节中的学习记录。 
 * - 返回一个 `IPage<AdminLessonStatisticsDetailRes>`。 
 * - **`detailLessonByPage(String lessonId, List<String> userIds)`**： 
 * - 查询指定课程章节的详细学习记录。 
 * - 返回一个 `List<AdminLessonStatisticsDetailRes>`。 
 * - **`detailExpLessonByPage(String lessonId, List<String> userIds)`**： 
 * - 同上，查询指定课程章节的详细学习记录。 
 * - 返回一个 `List<AdminLessonStatisticsDetailRes>`。 
 * ### 3. 参数说明 
 * - **`Page page`**：分页参数，用于控制分页查询。 
 * - **`CourseContext courseContext`**：课程背景信息。 
 * - **`List<String> courseIds`**：课程 ID 列表。 
 * - **`Boolean isPublic`**：是否公开课程或章节。 
 * - **`Integer type`**：类型（例如，课程类型或章节类型）。 
 * - **`String search`**：搜索关键字。 
 * - **`String lessonId`**：课程章节 ID。 
 * - **`List<String> userIds`**：用户 ID 列表。 
 * ### 总结 
 * 这个 Mapper 接口主要用于课程和课程章节的学习统计，提供了一系列的方法来处理分页查询、统计和详细记录查询等操作。这些方法有助于实现课程管理系统的统计功能。 
**/
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
	 * @param courseContext 课程背景
	 * @param courseIds     课程id列表
	 * @param isPublic      是否公开
	 * @return {@code IPage<AdminCourseStatisticsRes> }
	 * @throws
	 * @see Page
	 * @see CourseContext
	 * @see List<String>
	 * @see Boolean
	 * @see IPage<AdminCourseStatisticsRes>
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
	 * @throws
	 * @see Page
	 * @see String
	 * @see Boolean
	 * @see Integer
	 * @see String
	 * @see IPage<AdminLessonStatisticsRes>
	 */
	IPage<AdminLessonStatisticsRes> listLessonByPage(Page page, @Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic, Integer type, String search);

	/**
	 * 列表课程练习
	 *
	 * @param lessonIds 课id列表
	 * @return {@code List<AdminLessonStatisticsRes> }
	 * @throws
	 * @see List<String>
	 * @see List<AdminLessonStatisticsRes>
	 */
	List<AdminLessonStatisticsRes> listLessonPractice(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 列出经验教训记录
	 *
	 * @param page     分页
	 * @param userIds  用户id列表
	 * @param lessonId 课id
	 * @return {@code IPage<AdminLessonStatisticsDetailRes> }
	 * @throws
	 * @see Page
	 * @see List<String>
	 * @see String
	 * @see IPage<AdminLessonStatisticsDetailRes>
	 */
	IPage<AdminLessonStatisticsDetailRes> listLessonLearnedRecord(Page page,
			@Param("userIds") List<String> userIds, @Param("lessonId") String lessonId);

	/**
	 * 详情课由分页
	 *
	 * @param lessonId 课id
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
	 * 详情exp （英文）课由分页
	 *
	 * @param lessonId 课id
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

