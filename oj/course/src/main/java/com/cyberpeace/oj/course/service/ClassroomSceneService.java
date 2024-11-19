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
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface ClassroomSceneService {

	/**
	 * 课堂组场景启动
	 *
	 * @param groupId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see JSONObject
	 */
	JSONObject startScene(String groupId, AuthContext authContext);

	/**
	 * 课堂组场景删除
	 *
	 * @param groupId
	 * @param authContext
	 * @return 存在场景删除完成后为空字符串 不存在场景则返回组编号
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see String
	 */
	String deleteScene(String groupId, AuthContext authContext);

	/**
	 * 清除课程表本地信息
	 *
	 * @param localScene 当地场景
	 * @param groupInfo  组信息
	 * @throws
	 * @see LocalScene
	 * @see ClassroomGroupInfo
	 */
	void deleteLocalScene(LocalScene localScene, ClassroomGroupInfo groupInfo);

	/**
	 * 批量启动场景
	 *
	 * @param classroomId
	 * @param authContext
	 * @return
	 * @throws @throws InterruptedException 中断异常
	 * @see String
	 * @see AuthContext
	 * @see List<GroupInfoBo>
	 */
	List<GroupInfoBo> batchStartScene(String classroomId, AuthContext authContext)
			throws InterruptedException;

	/**
	 * 批量删除场景
	 *
	 * @param classroomId
	 * @param authContext auth 上下文
	 * @throws
	 * @see String
	 * @see AuthContext
	 */
	void batchDeleteScene(String classroomId, AuthContext authContext);

	/**
	 * 课堂监控列表
	 *
	 * @param classroomId
	 * @return
	 * @throws
	 * @see String
	 * @see List<SceneMonitorBo>
	 */
	List<SceneMonitorBo> selectMonitorList(String classroomId);

	/**
	 * 启动完成
	 *
	 * @param classroomId
	 * @param groupId     组id
	 * @throws
	 * @see String
	 * @see String
	 */
	void finishStart(String classroomId, String groupId);

	/**
	 * 自组Socket消息
	 *
	 * @param groupId 组id
	 * @param ojEvent OJ 活动
	 * @param message 消息
	 * @throws
	 * @see String
	 * @see OjEvent
	 * @see String
	 */
	void toGroupSocketMessage(String groupId, OjEvent ojEvent, String message);

	/**
	 * 清除批量启动场景
	 *
	 * @param classroomId 课堂id
	 * @throws
	 * @see String
	 */
	void clearBatchStartScene(String classroomId);

	/**
	 * 前往 Google 课堂消息
	 *
	 * @param sceneBo 场景Bo
	 * @param ojEvent OJ 活动
	 * @param message 消息
	 * @throws
	 * @see StartSceneBo
	 * @see OjEvent
	 * @see String
	 */
	void toClassroomMessage(StartSceneBo sceneBo, OjEvent ojEvent, String message);

	/**
	 * 小组场景暂停与恢复
	 *
	 * @param groupId
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 */
	Boolean updateSceneStatus(String groupId);
}
