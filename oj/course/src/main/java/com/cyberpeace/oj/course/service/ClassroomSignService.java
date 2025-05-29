package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.AuthContext;
import com.cyberpeace.oj.course.dao.entity.ClassroomSign;
import com.cyberpeace.oj.course.service.bo.HelpStatusBo;

/**
 * 课堂标志Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see IService
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomSignService` 的接口，该接口扩展了 `IService<ClassroomSign>` 接口。这个接口主要用于处理与课堂签到及远程协助相关的服务方法。以下是对每个方法的简要分析： 
 * 1. **signIn(String classroomId, AuthContext authContext)**: 
 * - **功能**: 实现学生在指定课堂中的签到操作。 
 * - **参数**: 
 * - `classroomId`: 课堂ID。 
 * - `authContext`: 包含用户认证信息的对象。 
 * - **返回值**: 布尔值，表示签到是否成功。 
 * 2. **needHelp(String lessonId, String nodeId, String roomId)**: 
 * - **功能**: 注册一个请求远程协助的数据。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `nodeId`: 节点ID。 
 * - `roomId`: 房间ID。 
 * - **异常**: 没有声明具体的异常，但可能抛出一些未检查的异常。 
 * 3. **toHelp(String classroomId, String userId)**: 
 * - **功能**: 将某个用户标记为需要帮助的状态。 
 * - **参数**: 
 * - `classroomId`: 课堂ID。 
 * - `userId`: 用户ID。 
 * - **异常**: 同上，没有声明具体的异常。 
 * 4. **endHelp(String classroomId, String userId)**: 
 * - **功能**: 结束某个用户的远程协助状态。 
 * - **参数**: 
 * - `classroomId`: 课堂ID。 
 * - `userId`: 用户ID。 
 * - **异常**: 同上，没有声明具体的异常。 
 * 5. **getHelpStatus(String lessonId, String nodeId, String roomId)**: 
 * - **功能**: 获取指定课程、节点和房间的远程协助状态。 
 * - **参数**: 
 * - `lessonId`: 课程ID。 
 * - `nodeId`: 节点ID。 
 * - `roomId`: 房间ID。 
 * - **返回值**: `HelpStatusBo` 对象，包含远程协助的状态信息。 
 * 6. **getHelpUrl(String classroomId, String userId)**: 
 * - **功能**: 获取某个用户的远程协助URL。 
 * - **参数**: 
 * - `classroomId`: 课堂ID。 
 * - `userId`: 用户ID。 
 * - **返回值**: 字符串，包含远程协助的URL。 
 * ### 总结 
 * - `ClassroomSignService` 接口提供了与课堂签到及远程协助相关的服务方法。 
 * - 方法涵盖了从签到、请求远程协助、获取远程协助状态到结束远程协助的整个流程。 
 * - 使用了 `AuthContext` 来传递认证信息，并通过特定的ID（如课堂ID、用户ID等）来定位具体的操作对象。 
 * - 方法中没有明确声明异常，但实际使用时可能需要处理潜在的异常情况。 
**/
public interface ClassroomSignService extends IService<ClassroomSign> {

    /**
     * 课堂签到
     *
     * @param classroomId
     * @param authContext
     * @return
     * @throws
     * @see String
     * @see AuthContext
     * @see Boolean
     */
    Boolean signIn(String classroomId, AuthContext authContext);

    /**
     * 远程协助数据注册
     *
     * @param lessonId
     * @param nodeId   节点id
     * @param roomId   房间id
     * @throws
     * @see String
     * @see String
     * @see String
     */
    void needHelp(String lessonId, String nodeId, String roomId);

    /**
     * 远程协助数据注销
     *
     * @param classroomId
     * @param userId      用户id
     * @throws
     * @see String
     * @see String
     */
    void toHelp(String classroomId, String userId);

    /**
     * 结束远程协助
     *
     * @param classroomId
     * @param userId      用户id
     * @throws
     * @see String
     * @see String
     */
    void endHelp(String classroomId, String userId);

    /**
     * 获取远程协助状态
     *
     * @param lessonId
     * @param nodeId
     * @param roomId   房间id
     * @return
     * @throws
     * @see String
     * @see String
     * @see String
     * @see HelpStatusBo
     */
    HelpStatusBo getHelpStatus(String lessonId, String nodeId, String roomId);

    /**
     * 获取远程协助地址
     *
     * @param classroomId
     * @param userId
     * @return
     * @throws
     * @see String
     * @see String
     * @see String
     */
    String getHelpUrl(String classroomId, String userId);
}

