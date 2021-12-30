package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;

import java.util.List;

public interface ClassroomGroupInfoService extends IService<ClassroomGroupInfo> {

	/**
	 * 查询课堂场景 前台使用后台直接返回为空
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	ClassroomGroupInfo selectByLessonAndUser(String lessonId, AuthContext authContext);

	/**
	 *  查询课堂分组信息
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	List<GroupInfoBo> getGroupInfoByRoom(String classroomId, AuthContext authContext);
}
