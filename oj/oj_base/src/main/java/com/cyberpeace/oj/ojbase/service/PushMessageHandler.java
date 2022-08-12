package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;

public interface PushMessageHandler {

	/** 向课后练习推送消息*/
	void pushLessonPracticeMessage(JSONObject data, OjLocalScene ojLocalScene);

	/** 向课时推送消息*/
	void pushLessonMessage(JSONObject data, OjLocalScene ojLocalScene);

	/** 向实操题推送消息*/
	void pushOperationMessage(JSONObject data, OjLocalScene ojLocalScene);

	/** 向课堂推送场景信息*/
	void pushClassroomMessage(JSONObject data, OjLocalScene ojLocalScene);

	void pushMessageToNoSceneClassroom(JSONObject data, String groupId);

	/** 获取删除消息*/
	JSONObject getDeleteMessage();

	/** 推送进行远程协助消息*/
	void pushHelpMessage(JSONObject data, String classroomId, String userId);

	/** 推送课程场景*/
	void pushRoomTipsMessage(JSONObject data, String userId);

}
