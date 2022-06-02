package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;

@Mapper
public interface ClassroomMapper extends BaseMapper<Classroom> {

	/**
	 * 根据组织和课时查询课堂信息
	 * @param orgId
	 * @param lessonId
	 * @return
	 */
	Classroom selectByOrgAndLesson(@Param("orgId") String orgId,
			@Param("lessonId") String lessonId);

	/**
	 * 查询课程表数据
	 * @param listQuery
	 * @return
	 */
	List<Classroom> selectList(@Param("listQuery") ClassRoomListQuery listQuery);

	/**
	 * 课程表数据查询 查询时间范围内 自己创建的 或者组织下的
	 * @param courseIds
	 * @param roomListQuery
	 * @return
	 */
	List<Classroom> selectListByCourse(@Param("courseIds") List<String> courseIds,
			@Param("listQuery") ClassRoomListQuery roomListQuery);

	/**
	 * 课程表删除
	 * @param lessonIds
	 */
	List<Classroom> selectByLesson(@Param("lessonIds") List<String> lessonIds);

	/**
	 * 查询该组织的课堂表
	 * @param orgId
	 * @return
	 */
	List<Classroom> selectByOrgId(@Param("orgId") String orgId);

	/**
	 * 查询课程表下用户关联的课程id列表
	 * @param userId
	 * @return
	 */
	List<String> selectCourseIdsByUserId(@Param("userId") String userId);

	/**
	 * 查询用户关联的且不在指定课程列表中的课程表
	 * @param userId
	 * @param courseIds
	 * @param roomListQuery
	 * @return
	 */
	List<Classroom> selectByUserIdAndNotInCourses(@Param("userId") String userId,
			@Param("courseIds") List<String> courseIds,
			@Param("listQuery") ClassRoomListQuery roomListQuery);

	/**
	 * 查询用户关联的所有课程表
	 * @param userIds
	 * @return
	 */
	List<Classroom> selectByUserIds(@Param("userIds") List<String> userIds);

	/**
	 * 通过用户id和课程id查询课程表
	 * @param userId
	 * @param lessonId
	 * @return
	 */
	Classroom selectByUserIdAndLesson(@Param("userId") String userId,
			@Param("lessonId") String lessonId);
}
