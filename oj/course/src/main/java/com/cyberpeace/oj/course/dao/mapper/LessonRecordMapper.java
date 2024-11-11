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
 * @date 2024/11/11
 */
@Mapper
public interface LessonRecordMapper extends BaseMapper<LessonRecord> {

	/**
	 * 查询由用户
	 *
	 * @param userId 用户id
	 * @return {@code List<LessonRecord> }
	 */
	List<LessonRecord> selectByUser(@Param("userId") String userId);

	/**
	 * 按课程和用户列出
	 *
	 * @param userId    用户id
	 * @param lessonIds 课id列表
	 * @return {@code List<LessonRecord> }
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
	 */
	List<LessonRecord> selectByTime(@Param("userId") String userId, @Param("start") Date startTime,
			@Param("end") Date endTime);

	/**
	 * 查询由课和用户
	 *
	 * @param lessonId 课id
	 * @param userId   用户id
	 * @return {@code LessonRecord }
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
	 */
	int updateLearnedTime(@Param("lessonId") String lessonId, @Param("courseId") String courseId,@Param("userId") String userId,
						  @Param("learnedType") Integer learnedType, @Param("roomId") String roomId);
}
