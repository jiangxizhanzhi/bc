package com.cyberpeace.oj.course.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.course.service.bo.LessonPracticeWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.constant.SceneShare;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class LessonPracticeEventListener extends MessageListener {

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.LESSON_PRACTICE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		// 删除本地数据
		OjLocalScene ojLocalScene = ojLocalSceneService.deleteLocalScene(businessId);
		if (null == ojLocalScene) {
			return true;
		}

		// 推送删除数据
		JSONObject dataObj = new JSONObject();
		dataObj.put(JsonKeyConstant.CODE, OjEvent.SCENE_DELETE_SYNC);
		dataObj.put(JsonKeyConstant.DATA, CommonConstant.EMPTY_STRING);
		pushMessageToWeb(dataObj, ojLocalScene, context);
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
		pushMessageToWeb(dataObj, ojLocalScene, context);

		// 场景启动完成更新数据库 消息推送
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageToWeb(startSceneMessage, ojLocalScene, context);
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
		pushMessageToWeb(dataObj, ojLocalScene, context);
		return true;
	}

	/**
	 * websocket消息推送
	 * @param data
	 * @param ojLocalScene
	 * @param context
	 */
	private void pushMessageToWeb(JSONObject data, OjLocalScene ojLocalScene, MqContext context) {

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
}
