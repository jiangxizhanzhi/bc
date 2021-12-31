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
	 * @return
	 */
	Classroom create(Classroom classroom);

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
}
