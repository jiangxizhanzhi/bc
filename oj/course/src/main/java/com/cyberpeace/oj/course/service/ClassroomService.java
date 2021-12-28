package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.context.ClassRoomListQuery;
import com.cyberpeace.oj.course.dao.entity.Classroom;
import com.cyberpeace.oj.course.service.bo.ClassroomBo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.course.service.bo.TimeScheduleBo;

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
	 *  查询课堂分组信息
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	List<GroupInfoBo> getGroupInfo(String classroomId, AuthContext authContext);
}
