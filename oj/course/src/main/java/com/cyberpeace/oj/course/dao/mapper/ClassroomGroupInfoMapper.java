package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课堂群信息Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomGroupInfoMapper` 的接口，该接口继承了 MyBatis-Plus 提供的 `BaseMapper` 接口，并添加了一些自定义的方法来操作 `ClassroomGroupInfo` 实体类。以下是详细的分析： 
 * ### 包与导入 
 * - **包声明**：代码位于 `com.cyberpeace.oj.course.dao.mapper` 包下。 
 * - **导入语句**： 
 * - 导入了 MyBatis-Plus 的 `BaseMapper` 和 `ClassroomGroupInfo` 类。 
 * - 导入了 MyBatis 的 `Mapper` 和 `Param` 注解。 
 * - 导入了 Java 的 `List`。 
 * ### 类定义 
 * - **注解**： 
 * - `@Mapper`：表明这是一个 MyBatis 的 Mapper 接口。 
 * ### 方法定义 
 * - **selectByRoomAndUser** 
 * - **描述**：根据课堂编号和用户ID查询分组信息。 
 * - **参数**： 
 * - `roomId`：字符串类型的课堂编号。 
 * - `userId`：字符串类型的用户ID。 
 * - **返回值**：一个 `ClassroomGroupInfo` 对象。 
 * - **selectByRoom** 
 * - **描述**：根据课堂编号查询所有分组信息。 
 * - **参数**： 
 * - `roomId`：字符串类型的课堂编号。 
 * - **返回值**：一个包含多个 `ClassroomGroupInfo` 对象的列表。 
 * - **selectLastGroupByRoom** 
 * - **描述**：查询指定课堂下的最后一组的组号。 
 * - **参数**： 
 * - `roomId`：字符串类型的课堂编号。 
 * - **返回值**：一个整数，表示最后一组的组号。 
 * - **deleteByRoom** 
 * - **描述**：删除指定课堂的所有分组信息。 
 * - **参数**： 
 * - `classroomId`：字符串类型的课堂ID。 
 * - **返回值**：无（`void`）。 
 * ### 总结 
 * - 这个接口主要负责与数据库中的 `ClassroomGroupInfo` 表进行交互。 
 * - 它提供了多种查询和删除功能，包括按课堂编号和用户ID查询、按课堂编号查询所有分组信息、查询指定课堂的最后一组组号以及删除指定课堂的所有分组信息。 
 * - 通过继承 `BaseMapper`，该接口还自动获得了许多基本的 CRUD 操作方法。 
**/
@Mapper
public interface ClassroomGroupInfoMapper extends BaseMapper<ClassroomGroupInfo> {

	/**
	 * 根据课堂编号和用户查询分组信息
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see ClassroomGroupInfo
	 */
	ClassroomGroupInfo selectByRoomAndUser(@Param("roomId") String roomId,
			@Param("userId") String userId);

	/**
	 * 查询课堂下的分组信息
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<ClassroomGroupInfo>
	 */
	List<ClassroomGroupInfo> selectByRoom(@Param("roomId") String roomId);

	/**
	 * 查询课堂下的最后一组组号
	 *
	 * @param roomId
	 * @return
	 * @throws
	 * @see String
	 * @see Integer
	 */
	Integer selectLastGroupByRoom(@Param("roomId") String roomId);

	/**
	 * 删除组信息
	 *
	 * @param classroomId 课堂id
	 * @throws
	 * @see String
	 */
	void deleteByRoom(@Param("roomId") String classroomId);
}

