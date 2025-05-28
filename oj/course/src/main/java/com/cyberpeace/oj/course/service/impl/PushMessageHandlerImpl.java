/**
 * 这段代码定义了一个名为 `PushMessageHandlerImpl` 的 Spring 服务类，实现了 `PushMessageHandler` 接口。该类的主要功能是通过 WebSocket 向不同的场景（如课程、操作、课堂等）推送消息。以下是对代码的简要分析： 
 * ### 类和接口定义 
 * - **类**：`PushMessageHandlerImpl` 
 * - 实现了 `PushMessageHandler` 接口。 
 * - 使用 Lombok 的 `@Slf4j` 注解添加日志记录功能。 
 * - 使用 `@Service` 注解表明这是一个 Spring 服务组件。 
 * ### 成员变量 
 * - **MyWebSocketHandler**：用于处理 WebSocket 消息的组件。 
 * - **ClassroomGroupInfoMapper**：用于与数据库交互的 Mapper，用于获取课堂群组信息。 
 * ### 方法 
 * #### 公共方法 
 * 1. **pushLessonPracticeMessage** 
 * - 功能：推送课程练习消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 2. **pushLessonMessage** 
 * - 功能：推送课程消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 3. **pushOperationMessage** 
 * - 功能：推送操作消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 4. **pushClassroomMessage** 
 * - 功能：推送课堂消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 更新数据对象。 
 * - 调用 `pushMessageToClassroom` 和 `pushMessageToUser` 方法。 
 * 5. **pushMessageToNoSceneClassroom** 
 * - 功能：向没有场景的课堂推送消息。 
 * - 参数：`JSONObject data`, `String groupId`。 
 * - 流程： 
 * - 获取群组信息。 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 6. **pushMessageToUser** 
 * - 功能：向用户推送消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 7. **pushMessageToClassroom** 
 * - 功能：向课堂推送消息。 
 * - 参数：`JSONObject data`, `OjLocalScene ojLocalScene`。 
 * - 流程： 
 * - 获取群组信息。 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 8. **getDeleteMessage** 
 * - 功能：获取删除消息。 
 * - 返回值：`JSONObject`。 
 * - 流程： 
 * - 创建 JSON 对象并设置删除事件码。 
 * 9. **pushHelpMessage** 
 * - 功能：推送帮助消息。 
 * - 参数：`JSONObject data`, `String classroomId`, `String userId`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * 10. **pushRoomTipsMessage** 
 * - 功能：推送房间提示消息。 
 * - 参数：`JSONObject data`, `String userId`。 
 * - 流程： 
 * - 组装消息标识符。 
 * - 创建消息上下文并推送消息。 
 * ### 总结 
 * - `PushMessageHandlerImpl` 类通过 WebSocket 向不同场景（如课程、操作、课堂等）推送消息。 
 * - 每个公共方法都负责组装消息标识符和数据，并通过 `MyWebSocketHandler` 推送消息。 
 * - 私有方法主要用于内部逻辑处理，如获取群组信息或向特定用户或课堂推送消息。 
**/
package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.course.service.bo.ClassHelpWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.service.bo.ClassroomWsMessageIdentifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import com.cyberpeace.oj.ojbase.service.bo.LessonPracticeWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.service.bo.LessonWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.service.bo.OperationWsMessageIdentifier;

/**
 * 推消息处理器实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PushMessageHandler
 * @since 1.0.0
 */
@Slf4j
@Service
public class PushMessageHandlerImpl implements PushMessageHandler {

	/**
	 * 我 Web 套接字处理程序
	 *
	 * @see MyWebSocketHandler
	 */
	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	/**
	 * 课堂群信息Mapper
	 *
	 * @see ClassroomGroupInfoMapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * 推送课程练习消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	@Override
	public void pushLessonPracticeMessage(JSONObject data, OjLocalScene ojLocalScene) {

		if (null == ojLocalScene) {
			return;
		}
		// 组装业务编号
		String[] businessIdArray = ojLocalScene.getBusinessId().split(CommonConstant.CODE_COMMA);
		LessonPracticeWsMessageIdentifier messageIdentifier = LessonPracticeWsMessageIdentifier
				.builder().lessonId(businessIdArray[0]).practiceId(businessIdArray[1])
				.shared(ojLocalScene.getShared() ? SceneShare.SHARED.getCode()
						: SceneShare.PRIVATE.getCode())
				.fromWeb(ojLocalScene.getFromWeb()).build();
		messageIdentifier.setUserId(ojLocalScene.getUserId());
		messageIdentifier.setPath(WsPath.LESSON_PRACTICE_SCENE);

		// 组装消息体
		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(messageIdentifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());
	}

	/**
	 * 推送课程消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	@Override
	public void pushLessonMessage(JSONObject data, OjLocalScene ojLocalScene) {
		if (null == ojLocalScene) {
			return;
		}

		LessonWsMessageIdentifier identifier = new LessonWsMessageIdentifier();
		identifier.setFromWeb(ojLocalScene.getFromWeb());
		identifier.setShared(ojLocalScene.getShared());
		identifier.setLessonId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.LESSON_SCENE);
		identifier.setUserId(ojLocalScene.getUserId());

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());
	}

	/**
	 * 推实操消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	@Override
	public void pushOperationMessage(JSONObject data, OjLocalScene ojLocalScene) {
		if (null == ojLocalScene) {
			return;
		}

		OperationWsMessageIdentifier identifier = new OperationWsMessageIdentifier();
		identifier.setFromWeb(ojLocalScene.getFromWeb());
		identifier.setShared(ojLocalScene.getShared() ? SceneShare.SHARED.getCode()
				: SceneShare.PRIVATE.getCode());
		identifier.setPracticeId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.OPERATION_SCENE);
		identifier.setUserId(ojLocalScene.getUserId());

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());
	}

	/**
	 * 推送课堂消息
	 *
	 * @param data         数据
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	@Override
	public void pushClassroomMessage(JSONObject data, OjLocalScene ojLocalScene) {

		if (null == ojLocalScene) {
			return;
		}
		// 计算data数据是否存在
		JSONObject jsonData = data.getJSONObject(JsonKeyConstant.DATA);
		if (null == jsonData) {
			jsonData = new JSONObject();
		}

		jsonData.put(JsonKeyConstant.GROUP_ID, ojLocalScene.getBusinessId());
		data.put(JsonKeyConstant.DATA, jsonData);
		pushMessageToClassroom(data, ojLocalScene);
		pushMessageToUser(data, ojLocalScene);
	}

	/**
	 * 推消息自不场景课堂
	 *
	 * @param data    数据
	 * @param groupId 群id
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
	@Override
	public void pushMessageToNoSceneClassroom(JSONObject data, String groupId) {

		ClassroomWsMessageIdentifier identifier = new ClassroomWsMessageIdentifier();

		// 组装课堂websocket业务类
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper.selectById(groupId);
		if (null == groupInfo) {
			log.error("Classroom group not found! group_id=[{}]", groupId);
			return;
		}
		identifier.setClassroomId(groupInfo.getClassroomId());
		identifier.setPath(WsPath.CLASSROOM_SCENE);

		JSONObject groupData = new JSONObject();
		groupData.put(JsonKeyConstant.GROUP_ID, groupId);
		data.put(JsonKeyConstant.DATA, groupData);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给前台正在使用的用户
	 *
	 * @param data
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	private void pushMessageToUser(JSONObject data, OjLocalScene ojLocalScene) {
		if (null == ojLocalScene) {
			return;
		}
		ClassroomWsMessageIdentifier identifier = new ClassroomWsMessageIdentifier();
		identifier.setUserId(ojLocalScene.getUserId());

		// 组装前台用户组成员websocket业务类
		identifier.setClassroomId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.CLASSROOM_GROUP_SCENE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);
	}

	/**
	 * 推送消息给后台批量启动场景用户
	 *
	 * @param data
	 * @param ojLocalScene OJ当地场景
	 * @throws
	 * @see JSONObject
	 * @see OjLocalScene
	 */
	private void pushMessageToClassroom(JSONObject data, OjLocalScene ojLocalScene) {

		if (null == ojLocalScene) {
			return;
		}
		ClassroomWsMessageIdentifier identifier = new ClassroomWsMessageIdentifier();
		identifier.setUserId(ojLocalScene.getUserId());

		// 组装课堂websocket业务类
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		if (null == groupInfo) {
			log.error("Classroom group not found! group_id=[{}]", ojLocalScene.getBusinessId());
			return;
		}
		identifier.setClassroomId(groupInfo.getClassroomId());
		identifier.setPath(WsPath.CLASSROOM_SCENE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);

	}

	/**
	 * 获取删除消息
	 *
	 * @return {@code JSONObject }
	 * @throws
	 * @see JSONObject
	 */
	@Override
	public JSONObject getDeleteMessage() {
		JSONObject dataObj = new JSONObject();
		dataObj.put(JsonKeyConstant.CODE, OjEvent.SCENE_DELETE_SYNC.getCode());
		dataObj.put(JsonKeyConstant.DATA, CommonConstant.EMPTY_STRING);
		return dataObj;
	}

	/**
	 * 推送帮助消息
	 *
	 * @param data        数据
	 * @param classroomId 课堂id
	 * @param userId      用户id
	 * @throws
	 * @see JSONObject
	 * @see String
	 * @see String
	 */
	@Override
	public void pushHelpMessage(JSONObject data, String classroomId, String userId) {
		ClassHelpWsMessageIdentifier identifier = new ClassHelpWsMessageIdentifier();
		identifier.setUserId(userId);

		identifier.setClassroomId(classroomId);
		identifier.setPath(WsPath.CLASSROOM_HELP);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, false);
	}

	/**
	 * 推送房间提示消息
	 *
	 * @param data   数据
	 * @param userId 用户id
	 * @throws
	 * @see JSONObject
	 * @see String
	 */
	@Override
	public void pushRoomTipsMessage(JSONObject data, String userId) {
		WsMessageIdentifier identifier = new WsMessageIdentifier();
		identifier.setUserId(userId);
		identifier.setPath(WsPath.ROOM_SCENE_FIFTEEN_MIN_TIPS);

		WebSocketMessageContext<WsMessageIdentifier, Object> context = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(context, false);
	}
}
