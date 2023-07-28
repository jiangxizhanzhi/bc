package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.oj.ojbase.constant.*;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LessonEventListener extends MessageListener {

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.LESSON.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		// 删除本地数据
		OjLocalScene ojLocalScene = ojLocalSceneService.deleteLocalScene(businessId);

		// 推送删除数据
		pushMessageHandler.pushLessonMessage(pushMessageHandler.getDeleteMessage(), ojLocalScene);
		return true;
	}

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
	 * @param ojLocalSceneId
	 * @return
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
