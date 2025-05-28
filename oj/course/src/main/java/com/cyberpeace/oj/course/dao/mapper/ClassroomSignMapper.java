/**
 * 这段代码定义了一个名为 `ClassroomSignMapper` 的接口，它继承自 `BaseMapper<ClassroomSign>`。这个接口用于操作与 `ClassroomSign` 实体类相关的数据库表。以下是对代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：代码位于 `com.cyberpeace.oj.course.dao.mapper` 包下。 
 * - **导入语句**： 
 * - `com.baomidou.mybatisplus.core.mapper.BaseMapper`：MyBatis-Plus 提供的基础映射接口。 
 * - `com.cyberpeace.oj.course.dao.entity.ClassroomSign`：实体类 `ClassroomSign`。 
 * - `org.apache.ibatis.annotations.Mapper`：MyBatis 注解，表示这是一个 MyBatis 映射器。 
 * - `org.apache.ibatis.annotations.Param`：用于注解方法参数。 
 * - `java.util.List`：用于返回集合类型数据。 
 * ### 类定义 
 * - **注解**： 
 * - `@Mapper`：将该接口标记为 MyBatis 映射器。 
 * - `interface ClassroomSignMapper`：定义一个名为 `ClassroomSignMapper` 的接口。 
 * - `extends BaseMapper<ClassroomSign>`：继承了 `BaseMapper`，提供了一些基本的 CRUD 操作。 
 * ### 方法定义 
 * 1. **selectByRoom** 
 * - **描述**：根据课堂 ID 查询签到信息。 
 * - **参数**：`@Param("roomId") String classroomId` 
 * - **返回值**：`List<ClassroomSign>`，即根据 `classroomId` 查询得到的所有签到记录。 
 * 2. **selectByRoomAndUser** 
 * - **描述**：根据课堂 ID 和用户 ID 查询签到信息。 
 * - **参数**：`@Param("roomId") String roomId` 和 `@Param("userId") String userId` 
 * - **返回值**：`ClassroomSign`，即根据 `roomId` 和 `userId` 查询得到的一条签到记录。 
 * ### 总结 
 * - `ClassroomSignMapper` 接口主要用于数据库中 `ClassroomSign` 表的操作。 
 * - 它提供了两个自定义的方法来根据不同的条件查询签到信息。 
 * - 使用 MyBatis-Plus 提供的 `BaseMapper` 进行基本的 CRUD 操作，并扩展了两个自定义查询方法。 
 * 这个接口的设计符合良好的面向对象编程实践，并且充分利用了 MyBatis-Plus 的功能来简化数据库操作。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课堂标志Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface ClassroomSignMapper extends BaseMapper<ClassroomSign> {

	/**
	 * 根据课堂查询签到信息
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<ClassroomSign>
	 */
	List<ClassroomSign> selectByRoom(@Param("roomId") String classroomId);

	/**
	 * 根据课堂和用户查询签到信息
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see ClassroomSign
	 */
	ClassroomSign selectByRoomAndUser(@Param("roomId") String roomId,
			@Param("userId") String userId);
}
