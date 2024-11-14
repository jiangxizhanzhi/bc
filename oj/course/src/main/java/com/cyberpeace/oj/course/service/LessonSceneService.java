package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;

import java.util.List;

/**
 * 课时场景Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface LessonSceneService {

	/**
	 * 课时场景启动
	 *
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	JSONObject startScene(String lessonId, AuthContext authContext);

	/**
	 * 课时场景删除
	 *
	 * @param lessonId
	 * @param authContext
	 * @return
	 */
	Boolean deleteScene(String lessonId, AuthContext authContext);

	/**
	 * 课时场景节点重启
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param roomId
	 * @param authContext
	 * @return
	 */
	Boolean restartNode(String resourceId, String nodeId, String roomId, AuthContext authContext);

	/**
	 * 课时场景节点重建
	 *
	 * @param resourceId
	 * @param nodeId
	 * @param roomId      房间id
	 * @param authContext
	 * @return
	 */
	Boolean recreateNode(String resourceId, String nodeId, String roomId, AuthContext authContext);

	/**
	 * 课时场景暂停与恢复
	 *
	 * @param roomId
	 * @param resourceId
	 * @param status
	 * @param authContext
	 * @return
	 */
	Boolean updateSceneStatus(String roomId, String resourceId, Integer status, AuthContext authContext);

	/**
	 * 获取课时监控信息
	 *
	 * @param lessonId
	 * @return
	 */
	List<SceneMonitorBo> lessonMonitor(String lessonId);
}
