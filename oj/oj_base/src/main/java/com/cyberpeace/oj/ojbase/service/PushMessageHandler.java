/**
 * 这段代码定义了一个名为 `PushMessageHandler` 的接口，该接口包含了多个用于推送不同类型消息的方法。以下是对其的简要分析： 
 * ### 主要功能 
 * - **消息推送**：提供多种方法来向不同的目标（如课后练习、课时、实操题、课堂等）推送消息。 
 * - **获取消息**：提供一个方法来获取特定类型的消息（如删除消息）。 
 * ### 方法描述 
 * 1. **pushLessonPracticeMessage** 
 * - 功能：向课后练习推送消息。 
 * - 参数：数据对象（`JSONObject`）和本地场景对象（`OjLocalScene`）。 
 * 2. **pushLessonMessage** 
 * - 功能：向课时推送消息。 
 * - 参数：数据对象（`JSONObject`）和本地场景对象（`OjLocalScene`）。 
 * 3. **pushOperationMessage** 
 * - 功能：向实操题推送消息。 
 * - 参数：数据对象（`JSONObject`）和本地场景对象（`OjLocalScene`）。 
 * 4. **pushClassroomMessage** 
 * - 功能：向课堂推送场景信息。 
 * - 参数：数据对象（`JSONObject`）和本地场景对象（`OjLocalScene`）。 
 * 5. **pushMessageToNoSceneClassroom** 
 * - 功能：向没有场景的课堂推送消息。 
 * - 参数：数据对象（`JSONObject`）和群组ID（`String`）。 
 * 6. **getDeleteMessage** 
 * - 功能：获取删除消息。 
 * - 返回值：`JSONObject` 类型的消息对象。 
 * 7. **pushHelpMessage** 
 * - 功能：推送远程协助消息。 
 * - 参数：数据对象（`JSONObject`）、课堂ID（`String`）和用户ID（`String`）。 
 * 8. **pushRoomTipsMessage** 
 * - 功能：推送课程场景消息。 
 * - 参数：数据对象（`JSONObject`）和用户ID（`String`）。 
 * ### 依赖类 
 * - **JSONObject**: 由 `com.alibaba.fastjson.JSONObject` 提供，用于表示 JSON 格式的数据。 
 * - **OjLocalScene**: 定义在 `com.cyberpeace.oj.ojbase.dao.entity` 包中，用于表示与本地场景相关的实体。 
 * ### 总结 
 * 该接口主要用于定义一套标准化的消息推送方法，适用于不同类型的教育场景。每个方法都接收数据对象和某些特定的上下文信息（如场景对象或用户ID），并执行相应的推送操作。通过这些方法，可以灵活地向不同的目标推送不同类型的消息。 
**/
package com.cyberpeace.oj.ojbase.service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;

/**
 * 推送消息处理程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface PushMessageHandler {

    /**
     * 向课后练习推送消息
     *
     * @param data         数据
     * @param ojLocalScene OJ当地场景
     * @throws
     * @see JSONObject
     * @see OjLocalScene
     */
    void pushLessonPracticeMessage(JSONObject data, OjLocalScene ojLocalScene);

    /**
     * 向课时推送消息
     *
     * @param data         数据
     * @param ojLocalScene OJ当地场景
     * @throws
     * @see JSONObject
     * @see OjLocalScene
     */
    void pushLessonMessage(JSONObject data, OjLocalScene ojLocalScene);

    /**
     * 向实操题推送消息
     *
     * @param data         数据
     * @param ojLocalScene OJ当地场景
     * @throws
     * @see JSONObject
     * @see OjLocalScene
     */
    void pushOperationMessage(JSONObject data, OjLocalScene ojLocalScene);

    /**
     * 向课堂推送场景信息
     *
     * @param data         数据
     * @param ojLocalScene OJ当地场景
     * @throws
     * @see JSONObject
     * @see OjLocalScene
     */
    void pushClassroomMessage(JSONObject data, OjLocalScene ojLocalScene);

    /**
     * 推消息自不场景课堂
     *
     * @param data    数据
     * @param groupId 群id
     * @throws
     * @see JSONObject
     * @see String
     */
    void pushMessageToNoSceneClassroom(JSONObject data, String groupId);

    /**
     * 获取删除消息
     *
     * @return {@code JSONObject }
     * @throws
     * @see JSONObject
     */
    JSONObject getDeleteMessage();

    /**
     * 推送进行远程协助消息
     *
     * @param data        数据
     * @param classroomId 课堂id
     * @param userId      用户id
     * @throws
     * @see JSONObject
     * @see String
     * @see String
     */
    void pushHelpMessage(JSONObject data, String classroomId, String userId);

    /**
     * 推送课程场景
     *
     * @param data   数据
     * @param userId 用户id
     * @throws
     * @see JSONObject
     * @see String
     */
    void pushRoomTipsMessage(JSONObject data, String userId);

}
