package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.*;

import java.util.Date;
import java.util.List;

public interface ClassroomService extends IService<Classroom> {

	/**
	 * 新建课堂表
	 * @param classroom
	 * @param authContext
	 * @return
	 */
	Classroom create(Classroom classroom, AuthContext authContext);

	/**
	 * 前台课程表数据查询
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<TimeScheduleBo> selectTimeList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 分页查询课时表信息
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<ClassroomBo> selectList(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 * 获取用户签到信息列表
	 * @param page
	 * @param classroomId
	 * @return
	 */
	ListRsp<ClassroomMonitorBo> selectSignList(Page page, String classroomId);

	/**
	 * 获取用户签到信息列表
	 * @param classroomId
	 * @return
	 */
	List<ClassroomReportBo> selectClassroomReport(String classroomId);

	/**
	 * 课堂学习统计
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	ClassroomStatisticBo classroomStatistic(String classroomId, AuthContext authContext);

	/**
	 * 课堂数据删除 课堂数据 场景数据 分组数据
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	Integer deleteClassroom(String classroomId, AuthContext authContext);

	/**
	 * 课堂表更新
	 * @param classroom
	 * @param authContext
	 * @return
	 */
	Integer updateClassroom(Classroom classroom, AuthContext authContext);
}
