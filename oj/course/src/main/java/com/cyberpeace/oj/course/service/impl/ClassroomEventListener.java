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

/**
 * 课堂事件监听器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see MessageListener
 * @since 1.0.0
 */
@Slf4j
@Service
public class ClassroomEventListener extends MessageListener {

	/**
	 * 课堂场景Service
	 *
	 * @see ClassroomSceneService
	 */
	@Autowired
	private ClassroomSceneService classroomSceneService;

	/**
	 * OJ当地场景Service
	 *
	 * @see OjLocalSceneService
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * 课堂组信息Mapper
	 *
	 * @see ClassroomGroupInfoMapper
	 */
	@Autowired
	private ClassroomGroupInfoMapper classroomGroupInfoMapper;

	/**
	 * OJ当地场景Mapper
	 *
	 * @see OjLocalSceneMapper
	 */
	@Autowired
	private OjLocalSceneMapper ojLocalSceneMapper;

	/**
	 * 推送消息处理器
	 *
	 * @see PushMessageHandler
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * 远程用户Service
	 *
	 * @see RemoteUserService
	 */
	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 * @throws
	 * @see Integer
	 */
	@Override
	public Integer declareMessageType() {
		return BusinessCode.CLASSROOM.getCode();
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

		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);

		// 更新消息组装
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());

		pushMessageHandler.pushClassroomMessage(dataObj, ojLocalScene);
		return CommonConstant.TRUE;
	}

	/**
	 * 上场景成功
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
	public Boolean onSceneSuccess(String data, String businessId, MqContext context) {

		// 场景启动完成更新数据库
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		JSONObject scene = dataObj.getJSONObject(JsonKeyConstant.DATA)
				.getJSONObject(JsonKeyConstant.DATA).getJSONObject(JsonKeyConstant.SCENE_DATA);

		int event = scene.getIntValue(JsonKeyConstant.SCENE_EVENT);
		int eventStatus = scene.getIntValue(JsonKeyConstant.SCENE_EVENT_STATUS);

		if (event != SceneEvent.CREATE.getCode().intValue()) {
			log.warn("ClassroomEventListener.onSceneSuccess error: scene event is not create [{}]", event);
			return false;
		}

		if (eventStatus != SceneEventStatus.FINISH.getCode().intValue()
				&& eventStatus != SceneEventStatus.FAILED.getCode().intValue()) {
			log.warn("ClassroomEventListener.onSceneSuccess error: scene event status is not finish or failed, eventStatus=[{}]", eventStatus);
			return false;
		}

		OjLocalScene ojLocalScene = validateOjLocalScene(businessId);
		ClassroomGroupInfo groupInfo = classroomGroupInfoMapper
				.selectById(ojLocalScene.getBusinessId());
		if (null == groupInfo) {
			log.warn("ClassroomEventListener.onSceneSuccess error: classroom_group_info not found, businessid=[{}]", ojLocalScene.getBusinessId());
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
	 *
	 * @param ojLocalSceneId
	 * @return
	 * @throws
	 * @see String
	 * @see OjLocalScene
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
