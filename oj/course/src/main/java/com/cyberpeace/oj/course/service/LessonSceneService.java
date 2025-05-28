/**
 * 这段代码定义了一个名为 `LessonSceneService` 的接口，该接口主要用于管理课程场景相关的操作。以下是对其主要功能和方法的简要分析： 
 * ### 接口名称 
 * - **LessonSceneService**: 用于管理课程场景的服务接口。 
 * ### 方法列表 
 * 1. **startScene** 
 * - **描述**: 启动一个课时场景。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `authContext`: 鉴权上下文对象。 
 * - **返回值**: `JSONObject`, 返回操作结果或相关信息。 
 * - **异常**: 无明确声明。 
 * 2. **deleteScene** 
 * - **描述**: 删除一个课时场景。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `authContext`: 鉴权上下文对象。 
 * - **返回值**: `Boolean`, 表示操作是否成功。 
 * - **异常**: 无明确声明。 
 * 3. **restartNode** 
 * - **描述**: 重启课时场景中的某个节点。 
 * - **参数**: 
 * - `resourceId`: 资源ID。 
 * - `nodeId`: 节点ID。 
 * - `roomId`: 房间ID。 
 * - `authContext`: 鉴权上下文对象。 
 * - **返回值**: `Boolean`, 表示操作是否成功。 
 * - **异常**: 无明确声明。 
 * 4. **recreateNode** 
 * - **描述**: 重建课时场景中的某个节点。 
 * - **参数**: 
 * - `resourceId`: 资源ID。 
 * - `nodeId`: 节点ID。 
 * - `roomId`: 房间ID。 
 * - `authContext`: 鉴权上下文对象。 
 * - **返回值**: `Boolean`, 表示操作是否成功。 
 * - **异常**: 无明确声明。 
 * 5. **updateSceneStatus** 
 * - **描述**: 暂停或恢复课时场景。 
 * - **参数**: 
 * - `roomId`: 房间ID。 
 * - `resourceId`: 资源ID。 
 * - `status`: 状态（例如：暂停、恢复）。 
 * - `authContext`: 鉴权上下文对象。 
 * - **返回值**: `Boolean`, 表示操作是否成功。 
 * - **异常**: 无明确声明。 
 * 6. **lessonMonitor** 
 * - **描述**: 获取课时场景的监控信息。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - **返回值**: `List<SceneMonitorBo>`, 包含课时场景的监控信息。 
 * - **异常**: 无明确声明。 
 * ### 总结 
 * - **功能**: 该接口提供了对课程场景进行启动、删除、重启节点、重建节点、更新状态以及获取监控信息等功能。 
 * - **鉴权**: 所有方法都接受 `AuthContext` 参数，表示这些操作需要鉴权。 
 * - **返回类型**: 根据具体的功能，返回类型包括 `JSONObject` 和 `Boolean`，以提供操作结果或状态。 
 * 这个接口的设计考虑到了课程场景的多种管理需求，并且通过鉴权机制确保了操作的安全性。 
**/
package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.service.bo.SceneMonitorBo;

import java.util.List;

/**
 * 课场景Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface LessonSceneService {

	/**
	 * 课时场景启动
	 *
	 * @param lessonId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see JSONObject
	 */
	JSONObject startScene(String lessonId, AuthContext authContext);

	/**
	 * 课时场景删除
	 *
	 * @param lessonId
	 * @param authContext
	 * @return
	 * @throws
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see AuthContext
	 * @see Boolean
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
	 * @throws
	 * @see String
	 * @see String
	 * @see Integer
	 * @see AuthContext
	 * @see Boolean
	 */
	Boolean updateSceneStatus(String roomId, String resourceId, Integer status, AuthContext authContext);

	/**
	 * 获取课时监控信息
	 *
	 * @param lessonId
	 * @return
	 * @throws
	 * @see String
	 * @see List<SceneMonitorBo>
	 */
	List<SceneMonitorBo> lessonMonitor(String lessonId);
}
