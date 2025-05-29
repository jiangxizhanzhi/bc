package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.*;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

/**
 * Lesson 事件侦听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see MessageListener
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonEventListener` 的服务类，继承自 `MessageListener` 类。这个类主要用于处理与课程相关的消息事件。以下是代码的主要组成部分和功能分析： 
 * ### 主要组成部分 
 * 1. **依赖注入** 
 * - `@Autowired` 注解用于自动注入 `OjLocalSceneService` 和 `PushMessageHandler` 两个服务对象。 
 * - `OjLocalSceneService` 用于操作 `OjLocalScene` 实体。 
 * - `PushMessageHandler` 用于处理消息推送。 
 * 2. **声明消息类型** 
 * - `declareMessageType()` 方法返回一个整数，表示该监听器处理的消息类型。这里使用了 `LocalScene.BusinessCode.LESSON.getCode()` 来获取特定的消息类型码。 
 * 3. **处理不同消息事件** 
 * - `onSceneDelete()` 方法：当接收到场景删除的消息时，调用 `OjLocalSceneService` 删除对应的本地场景数据，并推送删除消息。 
 * - `onSceneStartProcess()` 方法：当接收到场景开始的消息时，验证场景数据的有效性，更新场景状态并推送更新消息。 
 * - `onSceneElement()` 方法：处理场景元素的消息，类似于 `onSceneStartProcess()` 方法，但可能针对不同的场景元素进行处理。 
 * 4. **数据校验** 
 * - `validateOjLocalScene()` 方法：检查指定 ID 的 `OjLocalScene` 是否存在。如果不存在，则抛出 `BusinessException` 异常。 
 * ### 功能总结 
 * - **消息类型声明**：明确该监听器处理的消息类型。 
 * - **场景管理**：包括场景删除、场景开始和场景元素处理等。 
 * - **数据验证**：确保在处理消息之前，相关数据是存在的。 
 * - **消息推送**：根据不同的业务逻辑，推送相应的消息给客户端或其他系统组件。 
 * ### 技术点 
 * - **Spring 注解**：使用 `@Service` 注解将该类标记为 Spring 服务。 
 * - **Lombok**：使用 `@Slf4j` 注解简化日志记录。 
 * - **FastJSON**：使用 `com.alibaba.fastjson.JSONObject` 进行 JSON 数据处理。 
 * - **异常处理**：通过 `BusinessException` 处理业务逻辑错误。 
 * 总体来说，这段代码实现了一个基于消息驱动的课程管理监听器，能够处理场景删除、场景开始和场景元素等不同类型的事件，并且确保在处理这些事件时数据的正确性和完整性。 
**/
@Slf4j
@Service
public class LessonEventListener extends MessageListener {

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
		return LocalScene.BusinessCode.LESSON.getCode();
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

		// 推送删除数据
		pushMessageHandler.pushLessonMessage(pushMessageHandler.getDeleteMessage(), ojLocalScene);
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
		// 数据校验
		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);

		// 推送code更新
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());

		// 消息推送
		pushMessageHandler.pushLessonMessage(dataObj, ojLocalScene);

		// 场景启动完成更新数据库 重新设置code值
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageHandler.pushLessonMessage(startSceneMessage, ojLocalScene);
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

		// 数据校验
		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);

		// 推送code更新
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageHandler.pushLessonMessage(dataObj, ojLocalScene);
		return true;
	}

	/**
	 * 数据存在性校验
	 *
	 * @param ojLocalSceneId
	 * @return
	 * @throws
	 * @see String
	 * @see OjLocalScene
	 */
	private OjLocalScene validateOjLocalScene(String ojLocalSceneId) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(ojLocalSceneId);
		if (null == ojLocalScene) {
			log.error(
					"OperationEventListener onSceneElement: OjLocalScene not found! oj_local_scene_id=[{}]",
					ojLocalSceneId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return ojLocalScene;
	}
}

