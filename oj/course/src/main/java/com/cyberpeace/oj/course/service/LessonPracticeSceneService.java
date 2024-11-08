package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;

/**
 * 课实践场景Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface LessonPracticeSceneService {

	/**
	 * 课后练习场景启动
	 *
	 * @param lessonId
	 * @param practiceId  实践id
	 * @param authContext
	 * @return
	 */
	JSONObject startScene(String lessonId, String practiceId, AuthContext authContext);

	/**
	 * 课后练习场景删除
	 *
	 * @param lessonId
	 * @param practiceId  实践id
	 * @param authContext
	 * @return
	 */
	Boolean deleteScene(String lessonId, String practiceId, AuthContext authContext);

	/**
	 * 课后练习场景节点重启
	 *
	 * @param lessonId
	 * @param practiceId
	 * @param nodeId
	 * @param authContext
	 * @return
	 */
	Boolean restartNode(String lessonId, String practiceId, String nodeId, AuthContext authContext);

	/**
	 * 课后练习场景节点重建
	 *
	 * @param lessonId
	 * @param practiceId
	 * @param nodeId
	 * @param authContext
	 * @return
	 */
	Boolean recreateNode(String lessonId, String practiceId, String nodeId,
			AuthContext authContext);

	/**
	 * 课后练习场景暂停与恢复
	 *
	 * @param lessonId
	 * @param practiceId
	 * @param status
	 * @param authContext
	 * @return
	 */
	Boolean updateSceneStatus(String lessonId, String practiceId, Integer status, AuthContext authContext);

	/**
	 * 获取课后练习场景数据
	 *
	 * @param lessonId
	 * @param practiceId
	 * @param authContext
	 * @return
	 */
	TopoSceneDataBo getTopoScene(String lessonId, String practiceId, AuthContext authContext);

}
