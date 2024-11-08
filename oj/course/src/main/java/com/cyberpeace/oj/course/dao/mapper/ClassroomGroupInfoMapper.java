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
 * @date 2024/11/08
 */
@Mapper
public interface ClassroomGroupInfoMapper extends BaseMapper<ClassroomGroupInfo> {

	/**
	 * 根据课堂编号和用户查询分组信息
	 *
	 * @param roomId
	 * @param userId
	 * @return
	 */
	ClassroomGroupInfo selectByRoomAndUser(@Param("roomId") String roomId,
			@Param("userId") String userId);

	/**
	 * 查询课堂下的分组信息
	 *
	 * @param roomId
	 * @return
	 */
	List<ClassroomGroupInfo> selectByRoom(@Param("roomId") String roomId);

	/**
	 * 查询课堂下的最后一组组号
	 *
	 * @param roomId
	 * @return
	 */
	Integer selectLastGroupByRoom(@Param("roomId") String roomId);

	/**
	 * 删除组信息
	 *
	 * @param classroomId 课堂id
	 */
	void deleteByRoom(@Param("roomId") String classroomId);
}
