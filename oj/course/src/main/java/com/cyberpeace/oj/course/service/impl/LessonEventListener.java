package com.cyberpeace.oj.course.service.impl;

import java.util.Date;

import com.cyberpeace.common.constant.CommonConstant;
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
import com.cyberpeace.oj.course.service.bo.LessonWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.oj.ojbase.constant.OjBusinessErrorCode;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
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

	@Autowired
	private LocalSceneMapper localSceneMapper;

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

		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		// 设置场景启动code值
		dataObj.put("code", Event.SCENE_UPDATE.getCode());
		// 消息推送
		pushMessageToWeb(JsonUtil.toJsonString(dataObj), ojLocalScene, context);

		// 场景启动完成更新数据库
		JSONObject scene = dataObj.getJSONObject("data").getJSONObject("data")
				.getJSONObject("scene");
		if (null != scene && !scene.isEmpty()
				&& scene.getIntValue("event") == SceneEvent.CREATE.getCode().intValue()
				&& scene.getIntValue("event_status") == SceneEventStatus.FINISH.getCode()) {

			// 更新localScene数据
			LocalScene localScene = localSceneMapper.selectById(ojLocalScene.getLocalSceneId());
			localScene.setSceneStatus(LocalScene.SceneStatusEnum.START.getCode());
			localScene.setDestroyTime(TimeUtil.addMins(new Date(), localScene.getSceneTtl()));
			localScene.setStartTime(new Date());
			localSceneMapper.updateById(localScene);

			// 推送倒计时
			JSONObject json = new JSONObject();
			json.put("code", OjEvent.SCENE_CREATE_FINISH.getCode());
			JSONObject countDown = new JSONObject();
			countDown.put("remaining_time", localScene.getSceneTtl() * CommonConstant.SIXTY);
			json.put("data", countDown);

			pushMessageToWeb(JsonUtil.toJsonString(json), ojLocalScene, context);

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

	/**
	 * 课程消息推送
	 * @param data
	 * @param ojLocalScene
	 * @param mqContext
	 */
	private void pushMessageToWeb(String data, OjLocalScene ojLocalScene, MqContext mqContext) {

		LessonWsMessageIdentifier identifier = new LessonWsMessageIdentifier();
		identifier.setFromWeb(ojLocalScene.getFromWeb());
		identifier.setShared(ojLocalScene.getShared());
		identifier.setLessonId(ojLocalScene.getBusinessId());
		identifier.setPath(WsPath.OPERATION_SCENE);
		identifier.setUserId(mqContext.getUserId());

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().data(data).messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, ojLocalScene.getShared());

	}
}
