/**
 * 这段代码定义了一个名为 `CourseMapper` 的接口，该接口继承自 `BaseMapper<Course>`。这个接口主要用于数据库操作，特别是与课程相关的数据访问。以下是对代码的简要分析： 
 * ### 接口定义 
 * - **接口名**：`CourseMapper` 
 * - **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * - `@Repository`：表明这是一个 Spring 的数据访问组件。 
 * ### 方法说明 
 * #### 分页查询 
 * - `IPage<Course> listByPage(Page page, @Param("courseContext") CourseContext courseContext, @Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);` 
 * - 功能：根据分页信息、课程背景、课程ID列表和是否公开等条件查询课程列表。 
 * - 返回类型：`IPage<Course>`（分页结果）。 
 * #### 其他查询方法 
 * - `List<String> notDeleteIdsByIds(@Param("courseIds") List<String> courseIds);` 
 * - 功能：根据课程ID列表获取不删除的ID列表。 
 * - 返回类型：`List<String>`。 
 * - `List<String> selectByAuth(@Param("authContext") AuthContext authContext);` 
 * - 功能：根据认证上下文查询课程ID列表。 
 * - 返回类型：`List<String>`。 
 * - `List<String> selectSpecial(@Param("courseIds") List<String> courseIds, @Param("specialContext") SpecialCourseContext specialContext);` 
 * - 功能：根据课程ID列表和特殊上下文查询课程ID列表。 
 * - 返回类型：`List<String>`。 
 * - `List<String> selectIdsByCategory(@Param("courseIds") List<String> courseIds, @Param("categoryIds") List<String> categoryIds, @Param("isPublic") Boolean isPublic);` 
 * - 功能：根据课程ID列表、分类ID列表和是否公开查询课程ID列表。 
 * - 返回类型：`List<String>`。 
 * #### 单个实体查询 
 * - `String selectByName(@Param("name") String name);` 
 * - 功能：根据课程名称查询课程。 
 * - 返回类型：`String`（可能返回课程编号或其他唯一标识）。 
 * - `Course selectByCourseNum(@Param("courseNum") String courseNum);` 
 * - 功能：根据课程编号查询课程。 
 * - 返回类型：`Course`。 
 * - `List<Course> selectIdsByCourseName(@Param("courseNames") List<String> courseNames);` 
 * - 功能：根据课程名称列表查询课程列表。 
 * - 返回类型：`List<Course>`。 
 * #### 按用户或组织查询 
 * - `List<Course> selectCourseByUserId(String userId);` 
 * - 功能：根据用户ID查询课程列表。 
 * - 返回类型：`List<Course>`。 
 * - `List<Course> selectCourseByOrgId(String orgId);` 
 * - 功能：根据组织ID查询课程列表。 
 * - 返回类型：`List<Course>`。 
 * #### 更新方法 
 * - `void updateCourseHeat(@Param("heat") Integer heat, @Param("resourceId") String resourceId);` 
 * - 功能：更新课程热度。 
 * - 参数：热度值和资源ID。 
 * #### 其他 
 * - `List<String> selectTime();` 
 * - 功能：查询时间列表。 
 * - 返回类型：`List<String>`。 
 * ### 总结 
 * `CourseMapper` 接口提供了丰富的数据库操作方法，涵盖了课程管理中的多种场景，包括分页查询、条件查询、单个实体查询、按用户或组织查询以及更新操作。这些方法为课程管理模块提供了必要的数据访问支持。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.CourseContext;
import com.cyberpeace.oj.course.dao.context.SpecialCourseContext;
import com.cyberpeace.oj.course.dao.entity.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 课程Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
@Repository
public interface CourseMapper extends BaseMapper<Course> {

	/**
	 * 列表由分页
	 *
	 * @param page          分页
	 * @param courseContext 课程背景
	 * @param courseIds     课程id列表
	 * @param isPublic      是否公开
	 * @return {@code IPage<Course> }
	 * @throws
	 * @see Page
	 * @see CourseContext
	 * @see List<String>
	 * @see Boolean
	 * @see IPage<Course>
	 */
	IPage<Course> listByPage(Page page, @Param("courseContext") CourseContext courseContext,
			@Param("courseIds") List<String> courseIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 不删除id列表由id列表
	 *
	 * @param courseIds 课程id列表
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 */
	List<String> notDeleteIdsByIds(@Param("courseIds") List<String> courseIds);

	/**
	 * 查询由认证
	 *
	 * @param authContext auth 上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see AuthContext
	 * @see List<String>
	 */
	List<String> selectByAuth(@Param("authContext") AuthContext authContext);

	/**
	 * 查询特殊
	 *
	 * @param courseIds      课程id列表
	 * @param specialContext 特殊上下文
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see SpecialCourseContext
	 * @see List<String>
	 */
	List<String> selectSpecial(@Param("courseIds") List<String> courseIds,
			@Param("specialContext") SpecialCourseContext specialContext);

	/**
	 * 查询id列表由类别
	 *
	 * @param courseIds   课程id列表
	 * @param categoryIds 类别id列表
	 * @param isPublic    是否公开
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 * @see List<String>
	 * @see Boolean
	 * @see List<String>
	 */
	List<String> selectIdsByCategory(@Param("courseIds") List<String> courseIds,
			@Param("categoryIds") List<String> categoryIds, @Param("isPublic") Boolean isPublic);

	/**
	 * 根据名称查询课程
	 *
	 * @param name
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 */
	String selectByName(@Param("name") String name);

	/**
	 * 根据课程编号查询
	 *
	 * @param courseNum
	 * @return
	 * @throws
	 * @see String
	 * @see Course
	 */
	Course selectByCourseNum(@Param("courseNum") String courseNum);

	/**
	 * 查询id列表由课程名称
	 *
	 * @param courseNames 课程名称
	 * @return {@code List<Course> }
	 * @throws
	 * @see List<String>
	 * @see List<Course>
	 */
	List<Course> selectIdsByCourseName(@Param("courseNames") List<String> courseNames);

	/**
	 * 查询课程由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<Course> }
	 * @throws
	 * @see String
	 * @see List<Course>
	 */
	List<Course> selectCourseByUserId(String userId);

	/**
	 * 查询课程由组织id
	 *
	 * @param orgId 组织id
	 * @return {@code List<Course> }
	 * @throws
	 * @see String
	 * @see List<Course>
	 */
	List<Course> selectCourseByOrgId(String orgId);

	/**
	 * 更新 Course Heat
	 *
	 * @param heat       热
	 * @param resourceId 资源id
	 * @throws
	 * @see Integer
	 * @see String
	 */
	void updateCourseHeat(@Param("heat") Integer heat,@Param("resourceId") String resourceId);

	/**
	 * 查询时间
	 *
	 * @return {@code List<String> }
	 * @throws
	 * @see List<String>
	 */
	List<String> selectTime();
}
