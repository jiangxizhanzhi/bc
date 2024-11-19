package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 课时练习事件监听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see MessageListener
 * @since 1.0.0
 */
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
	 * 推送消息处理器
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
		return BusinessCode.LESSON_PRACTICE.getCode();
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
		if (null != ojLocalScene) {
			// 推送删除数据
			pushMessageHandler.pushLessonPracticeMessage(pushMessageHandler.getDeleteMessage(),
					ojLocalScene);
		}
		return true;
	}

	/**
	 * 上场景启动过程
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
