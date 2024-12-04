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
