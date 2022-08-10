package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.service.bo.GroupInfoBo;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;

import java.util.List;

public interface ClassroomSceneService {

	/**
	 * 课堂组场景启动
	 * @param groupId
	 * @param authContext
	 * @return
	 */
	JSONObject startScene(String groupId, AuthContext authContext);

	/**
	 * 课堂组场景删除
	 * @param groupId
	 * @param authContext
	 * @return
	 */
	Boolean deleteScene(String groupId, AuthContext authContext);

	/**
	 * 批量启动场景
	 * @param classroomId
	 * @param authContext
	 * @return
	 */
	List<GroupInfoBo> batchStartScene(String classroomId, AuthContext authContext)
			throws InterruptedException;

	/**
	 * 批量删除场景
	 * @param classroomId
	 * @param authContext
	 */
	void batchDeleteScene(String classroomId, AuthContext authContext);

	/**
	 * 课堂监控列表
	 * @param classroomId
	 * @return
	 */
	List<SceneMonitorBo> selectMonitorList(String classroomId);

	/**
	 * 启动完成
	 * @param classroomId
	 * @param groupId
	 */
	void finishStart(String classroomId, String groupId);

	void toGroupSocketMessage(String groupId, OjEvent ojEvent, String message);

	void clearBatchStartScene(String classroomId);
}
