package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.course.service.bo.StartSceneBo;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;

import java.util.List;

/**
 * 课堂场景Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface ClassroomSceneService {

	/**
	 * 课堂组场景启动
	 *
	 * @param groupId
	 * @param authContext
	 * @return
	 */
	JSONObject startScene(String groupId, AuthContext authContext);

	/**
	 * 课堂组场景删除
	 *
	 * @param groupId
	 * @param authContext
	 * @return 存在场景删除完成后为空字符串 不存在场景则返回组编号
	 */
	String deleteScene(String groupId, AuthContext authContext);

	/**
	 * 清除课程表本地信息
	 *
	 * @param localScene 当地场景
	 * @param groupInfo  组信息
	 */
	void deleteLocalScene(LocalScene localScene, ClassroomGroupInfo groupInfo);

	/**
	 * 批量启动场景
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws InterruptedException 中断异常
	 */
	List<GroupInfoBo> batchStartScene(String classroomId, AuthContext authContext)
			throws InterruptedException;

	/**
	 * 批量删除场景
	 *
	 * @param classroomId
	 * @param authContext auth 上下文
	 */
	void batchDeleteScene(String classroomId, AuthContext authContext);

	/**
	 * 课堂监控列表
	 *
	 * @param classroomId
	 * @return
	 */
	List<SceneMonitorBo> selectMonitorList(String classroomId);

	/**
	 * 启动完成
	 *
	 * @param classroomId
	 * @param groupId     组id
	 */
	void finishStart(String classroomId, String groupId);

	/**
	 * 自组Socket消息
	 *
	 * @param groupId 组id
	 * @param ojEvent OJ 活动
	 * @param message 消息
	 */
	void toGroupSocketMessage(String groupId, OjEvent ojEvent, String message);

	/**
	 * 清除批量启动场景
	 *
	 * @param classroomId 课堂id
	 */
	void clearBatchStartScene(String classroomId);

	/**
	 * 前往 Google 课堂消息
	 *
	 * @param sceneBo 场景Bo
	 * @param ojEvent OJ 活动
	 * @param message 消息
	 */
	void toClassroomMessage(StartSceneBo sceneBo, OjEvent ojEvent, String message);

	/**
	 * 小组场景暂停与恢复
	 *
	 * @param groupId
	 * @return
	 */
	Boolean updateSceneStatus(String groupId);
}
