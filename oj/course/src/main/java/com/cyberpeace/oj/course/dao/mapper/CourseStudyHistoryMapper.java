/**
 * 这段代码定义了一个名为 `CourseStudyHistoryMapper` 的接口，该接口继承了 MyBatis-Plus 提供的 `BaseMapper` 接口。这个接口用于操作 `CourseStudyHistory` 实体类的数据。以下是详细的分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.dao.mapper; 
 * ``` 
 * 该文件位于 `com.cyberpeace.oj.course.dao.mapper` 包中。 
 * ### 导入语句 
 * ```java 
 * import com.baomidou.mybatisplus.core.mapper.BaseMapper; 
 * import com.cyberpeace.oj.course.dao.entity.CourseStudyHistory; 
 * import org.apache.ibatis.annotations.Mapper; 
 * import org.apache.ibatis.annotations.Param; 
 * import java.util.List; 
 * ``` 
 * 导入了必要的类： 
 * - `BaseMapper`：MyBatis-Plus 提供的基础映射接口。 
 * - `CourseStudyHistory`：实体类。 
 * - `Mapper`：MyBatis 注解，用于标识这是一个 MyBatis 的 Mapper 接口。 
 * - `Param`：用于注解方法参数。 
 * - `List`：Java 集合框架中的接口。 
 * ### 类声明 
 * ```java 
 * @Mapper 
 * public interface CourseStudyHistoryMapper extends BaseMapper<CourseStudyHistory> { 
 * ``` 
 * - `@Mapper`：标注这个接口是一个 MyBatis 的 Mapper。 
 * - `extends BaseMapper<CourseStudyHistory>`：继承自 MyBatis-Plus 的 `BaseMapper` 接口，使得一些基本的 CRUD 操作可以直接使用。 
 * ### 方法声明 
 * #### 方法 1: `selectByUserId` 
 * ```java 
 * List<CourseStudyHistory> selectByUserId(@Param("userId") String userId); 
 * ``` 
 * - 返回类型：`List<CourseStudyHistory>`，表示查询结果是一个课程学习历史记录列表。 
 * - 参数：`userId`，表示通过用户 ID 查询课程学习历史记录。 
 * - 注释：方法用于根据用户 ID 查询该用户的课程学习历史记录。 
 * #### 方法 2: `selectByCourseIdAndUserId` 
 * ```java 
 * CourseStudyHistory selectByCourseIdAndUserId(@Param("courseId") String courseId, @Param("userId") String userId); 
 * ``` 
 * - 返回类型：`CourseStudyHistory`，表示查询结果是单个课程学习历史记录。 
 * - 参数：`courseId` 和 `userId`，表示通过课程 ID 和用户 ID 查询课程学习历史记录。 
 * - 注释：方法用于根据课程 ID 和用户 ID 查询特定的课程学习历史记录。 
 * ### 总结 
 * 该接口提供了两个查询方法，分别用于根据用户 ID 查询课程学习历史记录和根据课程 ID 和用户 ID 查询特定的课程学习历史记录。这些方法简化了数据库操作，并利用了 MyBatis-Plus 的特性来减少样板代码。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseStudyHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课程研究历史Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface CourseStudyHistoryMapper extends BaseMapper<CourseStudyHistory> {

	/**
	 * 查询由用户id
	 *
	 * @param userId 用户id
	 * @return {@code List<CourseStudyHistory> }
	 * @throws
	 * @see String
	 * @see List<CourseStudyHistory>
	 */
	List<CourseStudyHistory> selectByUserId(@Param("userId") String userId);

	/**
	 * 查询由课程id和用户id
	 *
	 * @param courseId 课程id
	 * @param userId   用户id
	 * @return {@code CourseStudyHistory }
	 * @throws
	 * @see String
	 * @see String
	 * @see CourseStudyHistory
	 */
	CourseStudyHistory selectByCourseIdAndUserId(@Param("courseId") String courseId,@Param("userId") String userId);

}
