package com.cyberpeace.oj.course.service.impl;

import com.cyberpeace.common.enums.SceneEvent;
import com.cyberpeace.common.enums.SceneEventStatus;
import com.cyberpeace.common.enums.BusinessCode;
import com.cyberpeace.oj.course.dao.entity.ClassroomGroupInfo;
import com.cyberpeace.oj.course.dao.mapper.ClassroomGroupInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.oj.course.service.ClassroomSceneService;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.dao.mapper.OjLocalSceneMapper;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ClassroomEventListener extends MessageListener {

	@Autowired
	private ClassroomSceneService classroomSceneService;

	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	@Autowired
	private PushMessageHandler pushMessageHandler;

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.CLASSROOM.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(businessId);
		if (null == ojLocalScene) {
			return true;
		}

		try {
			UserInfo userInfo = remoteUserService.getById(context.getUserId());
			UserInfoContext.setUserContext(userInfo);
			classroomSceneService.deleteScene(ojLocalScene.getBusinessId(), null);
		} catch (Exception e) {
			log.error("ClassroomEventListener.onSceneDelete error:", e);
			throw e;
		} finally {
			UserInfoContext.removeUserContext();
		}

		// 消息推送
		pushMessageHandler.pushClassroomMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);

		return CommonConstant.TRUE;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {

		// 数据组装
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());

		// 数据校验
		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);
		pushMessageHandler.pushClassroomMessage(dataObj, ojLocalScene);

		// 场景启动完成更新数据库 重新设置code值
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageHandler.pushClassroomMessage(startSceneMessage, ojLocalScene);
		}

		// 启动完成和失败，用于批量启动
		onSceneSuccess(data, businessId, context);

		return CommonConstant.TRUE;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {

		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);

		// 更新消息组装
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());

		pushMessageHandler.pushClassroomMessage(dataObj, ojLocalScene);
		return CommonConstant.TRUE;
	}

	@Override
	public Boolean onSceneSuccess(String data, String businessId, MqContext context) {

		// 场景启动完成更新数据库
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		JSONObject scene = dataObj.getJSONObject(JsonKeyConstant.DATA)
				.getJSONObject(JsonKeyConstant.DATA).getJSONObject(JsonKeyConstant.SCENE_DATA);

		int event = scene.getIntValue(JsonKeyConstant.SCENE_EVENT);
		int eventStatus = scene.getIntValue(JsonKeyConstant.SCENE_EVENT_STATUS);

		if (event != SceneEvent.CREATE.getCode().intValue()) {
			return false;
		}

		if (eventStatus != SceneEventStatus.FINISH.getCode().intValue()
				&& eventStatus != SceneEventStatus.FAILED.getCode().intValue()) {
			return false;
		}

		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);

		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		if (null == groupInfo) {
			return false;
		}

		if (eventStatus == SceneEventStatus.FINISH.getCode().intValue()) {
			classroomSceneService.finishStart(groupInfo.getClassroomId(),
					groupInfo.getResourceId());
		} else {
			classroomSceneService.clearBatchStartScene(groupInfo.getClassroomId());
		}

		return Boolean.TRUE;
	}

	/**
	 * 数据校验
	 * @param ojLocalSceneId
	 * @return
	 */
	private OjLocalScene validateOjLocalScene(String ojLocalSceneId) {

		OjLocalScene ojLocalScene = ojLocalSceneMapper.selectById(ojLocalSceneId);
		if (null == ojLocalScene) {
			log.error("OjLocalScene not found! oj_local_scene_id=[{}]", ojLocalSceneId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		return ojLocalScene;
	}

}
