package com.cyberpeace.oj.course.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.course.service.bo.ClassroomWsMessageIdentifier;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.oj.ojbase.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.constant.OjEvent;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassroomEventListener extends MessageListener {

	@Autowired
	private ClassroomSceneService classroomSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.CLASSROOM.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(businessId);
		if (null == ojLocalScene) {
			return true;
		}

		// 本地场景信息删除
		classroomSceneService.deleteScene(ojLocalScene.getBusinessId(), null);

		// 推送数据组装
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, OjEvent.SCENE_DELETE_SYNC.getCode());

		// 消息推送
		pushMessageToWeb(dataObj, ojLocalScene);

		return CommonConstant.TRUE;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(businessId);

		// 推送code更新
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageToWeb(dataObj, ojLocalScene);

		// 场景启动完成更新数据库 重新设置code值
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageToWeb(startSceneMessage, ojLocalScene);
		}

		return CommonConstant.TRUE;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(businessId);
		if (null == ojLocalScene) {
			log.error("OjLocalScene not found! oj_local_scene_id=[{}]", businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 更新消息组装
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());

		pushMessageToWeb(dataObj, ojLocalScene);

		return CommonConstant.TRUE;
	}

	/**
	 * 消息数据推送
	 * @param data
	 * @param ojLocalScene
	 * @return
	 */
	private void pushMessageToWeb(JSONObject data, OjLocalScene ojLocalScene) {
		data.getJSONObject(JsonKeyConstant.DATA).put(JsonKeyConstant.GROUP_ID,
				ojLocalScene.getResourceId());
		pushMessageToClassroom(data, ojLocalScene);
		pushMessageToUser(data, ojLocalScene);
	}

	/**
	 * 推送消息给前台正在使用的用户
	 * @param data
	 * @param ojLocalScene
	 */
	private void pushMessageToUser(JSONObject data, OjLocalScene ojLocalScene) {

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
	 * @param data
	 * @param ojLocalScene
	 * @return
	 */
	private void pushMessageToClassroom(JSONObject data, OjLocalScene ojLocalScene) {

		ClassroomWsMessageIdentifier identifier = new ClassroomWsMessageIdentifier();
		identifier.setUserId(ojLocalScene.getUserId());

		// 组装课堂websocket业务类
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		identifier.setClassroomId(groupInfo.getClassroomId());
		identifier.setPath(WsPath.CLASSROOM_SCENE);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();

		myWebSocketHandler.pushMessage(messageContext, true);

	}

}
