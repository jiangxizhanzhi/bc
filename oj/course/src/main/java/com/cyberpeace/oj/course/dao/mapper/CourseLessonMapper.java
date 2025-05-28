/**
 * 这段代码定义了一个名为 `CourseLessonMapper` 的接口，继承自 `BaseMapper<CourseLesson>`。这个接口主要用于操作与课程（Course）相关的课时（Lesson）数据。以下是对该代码的简要分析： 
 * ### 1. 包和导入 
 * - **包声明**：`com.cyberpeace.oj.course.dao.mapper` 
 * - **导入的类**： 
 * - `java.util.List`: 用于处理列表。 
 * - `com.baomidou.mybatisplus.core.metadata.IPage`: 分页接口。 
 * - `com.baomidou.mybatisplus.extension.plugins.pagination.Page`: 分页实现。 
 * - `com.cyberpeace.oj.course.dao.context.LessonContext`: 用于传递上下文信息。 
 * - `org.apache.ibatis.annotations.Mapper`: MyBatis 注解，表示这是一个 MyBatis 的 Mapper 接口。 
 * - `org.springframework.stereotype.Repository`: Spring 注解，表示这是一个 Repository，通常用于持久化层。 
 * ### 2. 接口定义 
 * - **注解**： 
 * - `@Mapper`: 标记为 MyBatis 的 Mapper。 
 * - `@Repository`: 标记为 Spring 的 Repository。 
 * - **继承**： 
 * - `extends BaseMapper<CourseLesson>`: 继承了 MyBatis Plus 提供的 `BaseMapper`，提供了基本的 CRUD 操作。 
 * ### 3. 方法定义 
 * #### 列表查询方法 
 * - `listByCourses(List<String> courseIds, Boolean isPublic)`: 根据课程ID列表和是否公开标志查询课时。 
 * - `selectByCourse(String courseId, Boolean isPublic)`: 根据课程ID和是否公开标志查询课时。 
 * - `selectAllVideoLesson()`: 查询所有视频类型的课时。 
 * #### 分页查询方法 
 * - `pageByContext(Page page, LessonContext context, Boolean isPublic, Integer type)`: 根据分页参数、上下文、是否公开和类型进行分页查询。 
 * #### 单条记录查询方法 
 * - `selectByPublic(String resourceId, Boolean isPublic)`: 根据资源ID和是否公开标志查询课时。 
 * - `selectIdsByCourse(List<String> courseIds)`: 根据课程ID列表查询课时ID列表。 
 * - `selectByCourseAndName(String courseId, String name)`: 根据课程ID和名称查询课时ID。 
 * - `selectCourseByCourseAndName(String courseId, String name)`: 根据课程ID和名称查询课时对象。 
 * #### 删除方法 
 * - `deleteByCourse(List<String> courseIds)`: 根据课程ID列表删除课时。 
 * #### 统计方法 
 * - `selectMinIndex(String courseId)`: 根据课程ID查询最小索引。 
 * - `selectChapterCount(String courseId)`: 根据课程ID查询章节数量。 
 * ### 总结 
 * 这个接口主要提供了与课程课时相关的各种操作，包括查询、分页、删除和统计等。通过继承 `BaseMapper`，它已经具备了基本的 CRUD 功能，并且还添加了一些特定于课程课时的功能。这些方法涵盖了从简单的单条记录查询到复杂的分页查询以及批量删除等操作。 
**/
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
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CourseLessonMapper extends BaseMapper<CourseLesson> {

	/**
	 * 按课程列出
	 *
	 * @param courseIds 课程id列表
	 * @param isPublic  是否公开
	 * @return {@code List<CourseLesson> }
	 * @throws
	 * @see List<String>
	 * @see Boolean
	 * @see List<CourseLesson>
	 */
	List<CourseLesson> listByCourses(@Param("courseIds") List<String> courseIds,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 查询课程下课时
	 *
	 * @param courseId
	 * @param isPublic true=公开 false=全部
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see List<CourseLesson>
	 */
	List<CourseLesson> selectByCourse(@Param("courseId") String courseId,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 分页由上下文
	 *
	 * @param page     分页
	 * @param context  上下文
	 * @param isPublic 是否公开
	 * @param type     类型
	 * @return {@code IPage<CourseLesson> }
	 * @throws
	 * @see Page
	 * @see LessonContext
	 * @see Boolean
	 * @see Integer
	 * @see IPage<CourseLesson>
	 */
	IPage<CourseLesson> pageByContext(Page page, @Param("context") LessonContext context,
			@Param("isPublic") Boolean isPublic,@Param("type") Integer type);

	/**
	 * 查询由公共
	 *
	 * @param resourceId 资源id
	 * @param isPublic   是否公开
	 * @return {@code CourseLesson }
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see CourseLesson
	 */
	CourseLesson selectByPublic(@Param("resourceId") String resourceId,
			@Param("isPublic") Boolean isPublic);

	/**
	 * 按课程删除
	 *
	 * @param courseIds 课程id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteByCourse(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询id列表由课程
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> selectIdsByCourse(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询由课程和名称
	 *
	 * @param courseId 课程id
	 * @param name     名称
	 * @return {@code String }
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	String selectByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	/**
	 * 查询课程由课程和名称
	 *
	 * @param courseId 课程id
	 * @param name     名称
	 * @return {@code CourseLesson }
	 * @throws
	 * @see String
	 * @see String
	 * @see CourseLesson
	 */
	CourseLesson selectCourseByCourseAndName(@Param("courseId") String courseId, @Param("name") String name);

	/**
	 * 查询分钟指数
	 *
	 * @param courseId 课程id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer selectMinIndex(@Param("courseId") String courseId);

	/**
	 * 查询章计数
	 *
	 * @param courseId 课程id
	 * @return {@code Integer }
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer selectChapterCount(@Param("courseId") String courseId);

	List<CourseLesson> selectAllVideoLesson();
}
