/**
 * 这段代码定义了一个名为 `LessonRecordMapper` 的接口，继承自 `BaseMapper<LessonRecord>`。该接口使用了 MyBatis-Plus 框架，并提供了几个用于操作 `LessonRecord` 实体对象的方法。以下是详细的分析： 
 * ### 主要组成部分 
 * 1. **注解**: 
 * - `@Mapper`: 标识这是一个 MyBatis 的 Mapper 接口。 
 * 2. **继承关系**: 
 * - `LessonRecordMapper` 继承自 `BaseMapper<LessonRecord>`，这意味着它已经包含了基本的 CRUD 操作方法（如 `selectById`, `insert`, `updateById`, `deleteById` 等）。 
 * 3. **自定义方法**: 
 * - `List<LessonRecord> selectByUser(@Param("userId") String userId)`: 根据用户ID查询用户的课程记录。 
 * - `List<LessonRecord> listByLessonAndUser(@Param("userId") String userId, @Param("lessonIds") List<String> lessonIds)`: 根据用户ID和课程ID列表查询课程记录。 
 * - `List<LessonRecord> selectByTime(@Param("userId") String userId, @Param("start") Date startTime, @Param("end") Date endTime)`: 根据用户ID和时间段查询课程记录。 
 * - `LessonRecord selectByLessonAndUser(@Param("lessonId") String lessonId, @Param("userId") String userId)`: 根据用户ID和课程ID查询单个课程记录。 
 * - `int updateLearnedTime(@Param("lessonId") String lessonId, @Param("courseId") String courseId, @Param("userId") String userId, @Param("learnedType") Integer learnedType, @Param("roomId") String roomId)`: 更新用户的某个课程的学习时间。 
 * ### 方法说明 
 * - **`selectByUser(String userId)`**: 查询指定用户的所有课程记录。 
 * - **`listByLessonAndUser(String userId, List<String> lessonIds)`**: 查询指定用户在多个课程中的记录。 
 * - **`selectByTime(String userId, Date startTime, Date endTime)`**: 查询指定用户在某段时间内的所有课程记录。 
 * - **`selectByLessonAndUser(String lessonId, String userId)`**: 查询指定用户在某个特定课程中的记录。 
 * - **`updateLearnedTime(String lessonId, String courseId, String userId, Integer learnedType, String roomId)`**: 更新用户在某个课程中的学习时间。 
 * ### 总结 
 * 这个 `LessonRecordMapper` 接口提供了一组丰富的方法来操作 `LessonRecord` 实体对象。这些方法包括按用户、课程和时间查询记录以及更新学习时间。通过继承 `BaseMapper<LessonRecord>`，该接口还具备了默认的 CRUD 操作能力，从而简化了数据库操作。 
**/
package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 课记录Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface LessonRecordMapper extends BaseMapper<LessonRecord> {

	/**
	 * 查询由用户
	 *
	 * @param userId 用户id
	 * @return {@code List<LessonRecord> }
	 * @throws
	 * @see String
	 * @see List<LessonRecord>
	 */
	List<LessonRecord> selectByUser(@Param("userId") String userId);

	/**
	 * 按课程和用户列出
	 *
	 * @param userId    用户id
	 * @param lessonIds 课id列表
	 * @return {@code List<LessonRecord> }
	 * @throws
	 * @see String
	 * @see List<String>
	 * @see List<LessonRecord>
	 */
	List<LessonRecord> listByLessonAndUser(@Param("userId") String userId,
			@Param("lessonIds") List<String> lessonIds);

	/**
	 * 查询由时间
	 *
	 * @param userId    用户id
	 * @param startTime 开始时间
	 * @param endTime   结束时间
	 * @return {@code List<LessonRecord> }
	 * @throws
	 * @see String
	 * @see Date
	 * @see Date
	 * @see List<LessonRecord>
	 */
	List<LessonRecord> selectByTime(@Param("userId") String userId, @Param("start") Date startTime,
			@Param("end") Date endTime);

	/**
	 * 查询由课和用户
	 *
	 * @param lessonId 课id
	 * @param userId   用户id
	 * @return {@code LessonRecord }
	 * @throws
	 * @see String
	 * @see String
	 * @see LessonRecord
	 */
	LessonRecord selectByLessonAndUser(@Param("lessonId") String lessonId,
									   @Param("userId") String userId);

	/**
	 * 更新学习时间
	 *
	 * @param lessonId    课id
	 * @param courseId    课程id
	 * @param userId      用户id
	 * @param learnedType 学习类型
	 * @param roomId      房间id
	 * @return int
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Integer
	 * @see String
	 * @see int
	 */
	int updateLearnedTime(@Param("lessonId") String lessonId, @Param("courseId") String courseId,@Param("userId") String userId,
						  @Param("learnedType") Integer learnedType, @Param("roomId") String roomId);
}
