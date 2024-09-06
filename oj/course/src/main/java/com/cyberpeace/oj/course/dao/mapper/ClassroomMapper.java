package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
	 * 根据计划查询房间
	 * @param planId
	 * @return
	 */
	List<Classroom> selectByPlan(@Param("planId") String planId);

	/**
	 * 根据ids删除房间
	 * @param orgIds
	 * @return
	 */
	void deleteByOrgIds(@Param("orgIds") List<String> orgIds);

	IPage<Classroom> page(Page listQuery, String userId, String trainPlanId, List<String> childrenOrg, boolean isAdmin, List<String> courseIds);

	List<String> selectIdsByPlanIds(List<String> ids);

	List<Classroom> selectByUserPlan(String userId, String planId);

	List<String> selectTempTaskIdsByUser(String userId, boolean isSuperUser);

    List<Classroom> selectByPlanIdsOrderTime(@Param("planIds") List<String> planIds);
}
