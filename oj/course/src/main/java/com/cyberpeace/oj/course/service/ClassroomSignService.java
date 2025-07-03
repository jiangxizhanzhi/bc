package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;

/**
 * 课堂标志Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see IService
 * @since 1.0.0
 */
public interface
ClassroomSignService extends IService<ClassroomSign> {

	/**
	 * 课堂签到
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean signIn(String classroomId, AuthContext authContext);

	/**
	 * 远程协助数据注册
	 *
	 * @param lessonId
	 * @param nodeId   节点id
	 * @param roomId   房间id
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	void needHelp(String lessonId, String nodeId, String roomId);

	/**
	 * 远程协助数据注销
	 *
	 * @param classroomId
	 * @param userId      用户id
	 * @throws
	 * @see String
	 * @see String
	 */
	void toHelp(String classroomId, String userId);

	/**
	 * 结束远程协助
	 *
	 * @param classroomId
	 * @param userId      用户id
	 * @throws
	 * @see String
	 * @see String
	 */
	void endHelp(String classroomId, String userId);

	/**
	 * 获取远程协助状态
	 *
	 * @param lessonId
	 * @param nodeId
	 * @param roomId   房间id
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see HelpStatusBo
	 */
	HelpStatusBo getHelpStatus(String lessonId, String nodeId, String roomId);

	/**
	 * 获取远程协助地址
	 *
	 * @param classroomId
	 * @param userId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 */
	String getHelpUrl(String classroomId, String userId);

	/**
	 * 生成访问记录
	 *
	 * @param classroomId classroomid
	 * @throws
	 * @see String
	 */
	void access(String classroomId);
}
