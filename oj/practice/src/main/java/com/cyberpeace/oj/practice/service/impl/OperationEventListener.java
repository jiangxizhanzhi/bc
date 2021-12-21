package com.cyberpeace.oj.practice.service.impl;

import java.util.Date;

import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
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
import com.cyberpeace.common.util.TimeUtil;
import com.cyberpeace.cs.constant.SceneEvent;
import com.cyberpeace.cs.constant.SceneEventStatus;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.bo.OperationWsMessageIdentifier;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OperationEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.PRACTICE.getCode();
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
					"OperationSceneEventListener.onSceneStartProcess: OjLocalScene not found! oj_local_scene_id=[{}]",
					businessId);
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		// 场景启动code值
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put("code", Event.SCENE_UPDATE.getCode());
		pushMessageToWeb(JsonUtil.toJsonString(dataObj), ojLocalScene, context);

		// 场景启动完成更新数据库 消息推送
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageToWeb(JsonUtil.toJsonString(startSceneMessage), ojLocalScene, context);
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
			throw new BusinessException(OjBusinessErrorCode.RESOURCE_NOT_FIND);
		}

		pushMessageToWeb(data, ojLocalScene, context);
		return true;
	}

	private void pushMessageToWeb(String data, OjLocalScene ojLocalScene, MqContext mqContext) {
		OperationWsMessageIdentifier identifier = new OperationWsMessageIdentifier();
		identifier.setFromWeb(ojLocalScene.getFromWeb());
		identifier.setShared(ojLocalScene.getShared());
		identifier.setPracticeId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.OPERATION_SCENE);
		identifier.setUserId(mqContext.getUserId());

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());
	}

}
