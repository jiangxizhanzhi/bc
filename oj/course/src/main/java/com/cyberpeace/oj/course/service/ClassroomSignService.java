package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;

public interface ClassroomSignService extends IService<ClassroomSign> {

	/**
	 * 课堂签到
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	Boolean signIn(String classroomId, AuthContext authContext);

	/**
	 * 远程协助数据注册
	 * @param lessonId
	 * @param nodeId
	 */
	void needHelp(String lessonId, String nodeId);

	/**
	 * 远程协助数据注销
	 * @param classroomId
	 * @param userId
	 */
	void toHelp(String classroomId, String userId);
}
