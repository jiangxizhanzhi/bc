package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;

@Mapper
public interface ClassroomSignMapper extends BaseMapper<ClassroomSign> {

	/**
	 * 根据课堂查询签到信息
	 * @param classroomId
	 * @return
	 */
	List<ClassroomSign> selectByRoom(@Param("roomId") String classroomId);

	/**
	 * 根据课堂和用户查询签到信息
	 * @param roomId
	 * @param userId
	 * @return
	 */
	ClassroomSign selectByRoomAndUser(@Param("roomId") String roomId,
			@Param("userId") String userId);

	List<ClassroomSign> selectByRoomsAndUsers(@Param("classroomIds") List<String> classroomIds,
			@Param("userIds") List<String> userIds);
}
