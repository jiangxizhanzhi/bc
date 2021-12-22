package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.course.service.bo.LessonWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LessonEventListener extends MessageListener {

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.LESSON.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		ojLocalSceneService.deleteScene(businessId);
		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error(
					"LessonEventListener.onSceneStartProcess: OjLocalScene not found! oj_local_scene_id=[{}]",
					businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 推送code更新
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put("code", Event.SCENE_UPDATE.getCode());

		// 消息推送
		pushMessageToWeb(JsonUtil.toJsonString(dataObj), ojLocalScene);

		// 场景启动完成更新数据库 重新设置code值
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageToWeb(JsonUtil.toJsonString(startSceneMessage), ojLocalScene);
		}

		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error(
					"OperationEventListener onSceneElement: OjLocalScene not found! oj_local_scene_id=[{}]",
					businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 推送code更新
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put("code", Event.SCENE_UPDATE.getCode());
		pushMessageToWeb(JsonUtil.toJsonString(dataObj), ojLocalScene);
		return true;
	}

	/**
	 * 课程消息推送
	 * @param data
	 * @param ojLocalScene
	 */
	private void pushMessageToWeb(String data, OjLocalScene ojLocalScene) {

		LessonWsMessageIdentifier identifier = new LessonWsMessageIdentifier();
		identifier.setFromWeb(ojLocalScene.getFromWeb());
		identifier.setShared(ojLocalScene.getShared() ? SceneShare.SHARED.getCode()
				: SceneShare.PRIVATE.getCode());
		identifier.setLessonId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.LESSON_SCENE);
		identifier.setUserId(ojLocalScene.getUserId());

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());

	}
}
