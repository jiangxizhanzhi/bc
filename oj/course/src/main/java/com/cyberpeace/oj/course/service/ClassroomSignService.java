package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;

public interface
ClassroomSignService extends IService<ClassroomSign> {

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
	void needHelp(String lessonId, String nodeId, String roomId);

	/**
	 * 远程协助数据注销
	 * @param classroomId
	 * @param userId
	 */
	void toHelp(String classroomId, String userId);

	/**
	 * 结束远程协助
	 * @param classroomId
	 * @param userId
	 */
	void endHelp(String classroomId, String userId);

	/**
	 * 获取远程协助状态
	 * @param lessonId
	 * @param nodeId
	 * @return
	 */
	HelpStatusBo getHelpStatus(String lessonId, String nodeId, String roomId);

	/**
	 * 获取远程协助地址
	 * @param classroomId
	 * @param userId
	 * @return
	 */
	String getHelpUrl(String classroomId, String userId);
}
