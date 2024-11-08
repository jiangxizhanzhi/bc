package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;

/**
 * 推送消息处理程序
 *
 * @author SN
 * @date 2024/11/08
 */
public interface PushMessageHandler {

	/**
	 * 向课后练习推送消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 */
	void pushLessonPracticeMessage(JSONObject data, OjLocalScene ojLocalScene);

	/**
	 * 向课时推送消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 */
	void pushLessonMessage(JSONObject data, OjLocalScene ojLocalScene);

	/**
	 * 向实操题推送消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 */
	void pushOperationMessage(JSONObject data, OjLocalScene ojLocalScene);

	/**
	 * 向课堂推送场景信息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 */
	void pushClassroomMessage(JSONObject data, OjLocalScene ojLocalScene);

	/**
	 * 推消息自不场景课堂
	 *
	 * @param data    数据
	 * @param groupId 群id
	 */
	void pushMessageToNoSceneClassroom(JSONObject data, String groupId);

	/**
	 * 获取删除消息
	 *
	 * @return {@code JSONObject }
	 */
	JSONObject getDeleteMessage();

	/**
	 * 推送进行远程协助消息
	 *
	 * @param data        数据
	 * @param classroomId 课堂id
	 * @param userId      用户id
	 */
	void pushHelpMessage(JSONObject data, String classroomId, String userId);

	/**
	 * 推送课程场景
	 *
	 * @param data   数据
	 * @param userId 用户id
	 */
	void pushRoomTipsMessage(JSONObject data, String userId);

}
