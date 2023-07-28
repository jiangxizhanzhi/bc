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

@Slf4j
@Service
public class LessonPracticeEventListener extends MessageListener {

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.LESSON_PRACTICE.getCode();
	}

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
