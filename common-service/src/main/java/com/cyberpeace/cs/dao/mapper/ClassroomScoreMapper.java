package com.cyberpeace.cs.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cs.dao.entity.ClassroomScore;

@Mapper
public interface ClassroomScoreMapper extends BaseMapper<ClassroomScore> {

	/**
	 * 根据课程表id获取分数记录
	 * @param classroomId
	 * @return
	 */
	List<ClassroomScore> listByClassroomIdAndUserId(@Param("classroomId") String classroomId,
			@Param("userId") String userId);

	/**
	 * 根据课程表id获取分数记录的用户列表
	 * @param classroomId
	 * @return
	 */
	List<String> listUserIdByClassroomId(@Param("classroomId") String classroomId);

	void clearByTaskId(@Param("taskId") String taskId);

	void clearByClassroomId(@Param("classroomId") String classroomId);

	List<ClassroomScore> selectByLessonIdAndUserId(@Param("lessonId") String lessonId,
			@Param("userId") String userId);

	Boolean isTaskFinished(@Param("taskId") String taskId);

}
