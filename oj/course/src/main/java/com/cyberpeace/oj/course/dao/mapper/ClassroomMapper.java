package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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
	 * 课程表数据查询
	 * @param courseIds
	 * @param roomListQuery
	 * @return
	 */
	List<Classroom> selectListByCourse(@Param("courseIds") List<String> courseIds,
			@Param("listQuery") ClassRoomListQuery roomListQuery);
}
