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
	 * 根据时间范围获取课堂信息
	 *
	 * @param startOfDay
	 * @param endOfDay
	 * @return
	 */
	List<TimeScheduleBo> listByTime(Date startOfDay, Date endOfDay);

	/**
	 * 新建课堂表
	 * @param classroom
	 * @return
	 */
	Classroom create(Classroom classroom);

	/**
	 * 分页查询课时表信息
	 * @param roomListQuery
	 * @param authContext
	 * @return
	 */
	List<ClassroomBo> selectPage(ClassRoomListQuery roomListQuery, AuthContext authContext);

	/**
	 *  查询课堂分组信息
	 * @param resourceId
	 * @param authContext
	 * @return
	 */
	List<GroupInfoBo> getGroupInfo(String resourceId, AuthContext authContext);
}
