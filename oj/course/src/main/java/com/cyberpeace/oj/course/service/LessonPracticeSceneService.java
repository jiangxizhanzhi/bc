package com.cyberpeace.oj.course.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.ojbase.service.bo.TopoSceneDataBo;

/**
 * 课实践场景Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeSceneService` 的接口，该接口用于处理与课后练习场景相关的操作。以下是对其主要功能的简要分析： 
 * ### 接口名称 
 * - **LessonPracticeSceneService**: 课后练习场景服务接口 
 * ### 方法列表 
 * 1. **startScene** 
 * - **功能**: 启动课后练习场景。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `JSONObject`，表示启动结果。 
 * 2. **deleteScene** 
 * - **功能**: 删除课后练习场景。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `Boolean`，表示删除是否成功。 
 * 3. **restartNode** 
 * - **功能**: 重启课后练习场景中的某个节点。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `nodeId`: 节点ID。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `Boolean`，表示重启是否成功。 
 * 4. **recreateNode** 
 * - **功能**: 重建课后练习场景中的某个节点。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `nodeId`: 节点ID。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `Boolean`，表示重建是否成功。 
 * 5. **updateSceneStatus** 
 * - **功能**: 更新课后练习场景的状态（暂停或恢复）。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `status`: 状态（例如，0 表示暂停，1 表示恢复）。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `Boolean`，表示更新是否成功。 
 * 6. **getTopoScene** 
 * - **功能**: 获取课后练习场景的数据。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `practiceId`: 实践ID。 
 * - `authContext`: 鉴权上下文。 
 * - **返回值**: `TopoSceneDataBo`，表示场景数据。 
 * ### 总结 
 * 该接口提供了与课后练习场景相关的多种操作，包括启动、删除、重启节点、重建节点、更新状态以及获取场景数据。这些方法需要鉴权上下文来确保安全性和权限控制。 
**/
public interface LessonPracticeSceneService {

    /**
     * 课后练习场景启动
     *
     * @param lessonId
     * @param practiceId  实践id
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see String
     * @see AuthContext
     * @see JSONObject
     */
    JSONObject startScene(String lessonId, String practiceId, AuthContext authContext);

    /**
     * 课后练习场景删除
     *
     * @param lessonId
     * @param practiceId  实践id
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see String
     * @see AuthContext
     * @see Boolean
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
     * @throws
     * @see String
     * @see String
     * @see String
     * @see AuthContext
     * @see Boolean
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
     * @throws
     * @see String
     * @see String
     * @see String
     * @see AuthContext
     * @see Boolean
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
     * @throws
     * @see String
     * @see String
     * @see Integer
     * @see AuthContext
     * @see Boolean
     */
    Boolean updateSceneStatus(String lessonId, String practiceId, Integer status, AuthContext authContext);

    /**
     * 获取课后练习场景数据
     *
     * @param lessonId
     * @param practiceId
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see String
     * @see AuthContext
     * @see TopoSceneDataBo
     */
    TopoSceneDataBo getTopoScene(String lessonId, String practiceId, AuthContext authContext);

}

