package com.cyberpeace.oj.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 课程练习事件侦听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MessageListener
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeEventListener` 的类，该类实现了 `MessageListener` 接口。这个类的主要功能是监听特定的消息类型，并根据不同的消息类型执行相应的操作。以下是对代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.service.impl` 
 * - **类名**: `LessonPracticeEventListener` 
 * - **继承关系**: 继承自 `MessageListener` 
 * - **注解**: 
 * - `@Slf4j`: 使用 Lombok 自动生成日志对象。 
 * - `@Service`: 标识这是一个 Spring 管理的服务类。 
 * ### 成员变量 
 * - **`ojLocalSceneService`**: 类型为 `OjLocalSceneService`，用于操作 `OjLocalScene` 实体。 
 * - **`pushMessageHandler`**: 类型为 `PushMessageHandler`，用于处理消息推送。 
 * ### 方法 
 * #### `declareMessageType` 
 * - **返回值**: `Integer` 
 * - **功能**: 返回一个表示消息类型的整数。在这个实现中，返回的是 `LocalScene.BusinessCode.LESSON_PRACTICE.getCode()`。 
 * #### `onSceneDelete` 
 * - **参数**: 
 * - `data`: 字符串，表示数据。 
 * - `businessId`: 字符串，表示业务ID。 
 * - `context`: `MqContext`，表示上下文信息。 
 * - **返回值**: `Boolean` 
 * - **功能**: 处理场景删除操作。首先从数据库中删除对应的 `OjLocalScene` 记录，然后推送删除消息。 
 * #### `onSceneStartProcess` 
 * - **参数**: 
 * - `data`: 字符串，表示数据。 
 * - `businessId`: 字符串，表示业务ID。 
 * - `context`: `MqContext`，表示上下文信息。 
 * - **返回值**: `Boolean` 
 * - **功能**: 处理场景启动过程。首先从数据库中获取 `OjLocalScene` 记录，如果记录不存在，则抛出异常。然后更新 JSON 对象并推送消息，最后推送启动完成的消息。 
 * #### `onSceneElement` 
 * - **参数**: 
 * - `data`: 字符串，表示数据。 
 * - `businessId`: 字符串，表示业务ID。 
 * - `context`: `MqContext`，表示上下文信息。 
 * - **返回值**: `Boolean` 
 * - **功能**: 处理场景元素操作。首先从数据库中获取 `OjLocalScene` 记录，如果记录不存在，则抛出异常。然后更新 JSON 对象并推送消息。 
 * ### 总结 
 * - `LessonPracticeEventListener` 类主要用于监听与课程练习相关的消息，并根据消息类型执行相应的操作（如删除、启动、更新等）。 
 * - 该类依赖于 `OjLocalSceneService` 和 `PushMessageHandler` 两个服务来完成具体的操作。 
 * - 通过这些方法，可以确保在接收到特定消息时，能够正确地处理和响应。 
**/
@Slf4j
@Service
public class LessonPracticeEventListener extends MessageListener {

	/**
	 * OJ当地场景Service
	 *
	 * @see OjLocalSceneService
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * 推送消息处理程序
	 *
	 * @see PushMessageHandler
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.LESSON_PRACTICE.getCode();
	}

	/**
	 * 上场景删除
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see MqContext
	 * @see Boolean
	 */
	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		// 删除本地数据
		OjLocalScene ojLocalScene = ojLocalSceneService.deleteLocalScene(businessId);
		if (null == ojLocalScene) {
			return true;
		}

		// 推送删除数据
		pushMessageHandler.pushLessonPracticeMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);
		return true;
	}

	/**
	 * 上场景开始过程
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see MqContext
	 * @see Boolean
	 */
	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error("OjLocalScene not found! oj_local_scene_id=[{}]", businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 场景启动code值
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageHandler.pushLessonPracticeMessage(dataObj, ojLocalScene);

		// 场景启动完成更新数据库 消息推送
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageHandler.pushLessonPracticeMessage(startSceneMessage, ojLocalScene);
		}

		return true;
	}

	/**
	 * 上场景元素
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 * @throws
	 * @see String
	 * @see String
	 * @see MqContext
	 * @see Boolean
	 */
	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error("OjLocalScene not found! oj_local_scene_id=[{}]", businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageHandler.pushLessonPracticeMessage(dataObj, ojLocalScene);
		return true;
	}
}

