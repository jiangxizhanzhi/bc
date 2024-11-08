package com.cyberpeace.oj.practice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;
import com.cyberpeace.oj.ojbase.constant.Event;
import com.cyberpeace.common.constant.JsonKeyConstant;
import com.cyberpeace.oj.ojbase.dao.entity.OjLocalScene;
import com.cyberpeace.oj.ojbase.service.OjLocalSceneService;
import com.cyberpeace.oj.ojbase.service.PushMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * 实操事件听者
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class OperationEventListener extends MessageListener {

	/**
	 * 推送消息处理程序
	 */
	@Autowired
	private PushMessageHandler pushMessageHandler;

	/**
	 * OJ当地场景Service
	 */
	@Autowired
	private OjLocalSceneService ojLocalSceneService;

	/**
	 * 声明消息类型
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.PRACTICE.getCode();
	}

	/**
	 * 上场景删除
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		// 本地数据删除
		OjLocalScene ojLocalScene = ojLocalSceneService.deleteLocalScene(businessId);
		if (null == ojLocalScene) {
			return CommonConstant.TRUE;
		}

		// 推送删除数据
		pushMessageHandler.pushOperationMessage(pushMessageHandler.getDeleteMessage(),
				ojLocalScene);
		return true;
	}

	/**
	 * 上场景开始过程
	 *
	 * @param data       数据
	 * @param businessId 业务id
	 * @param context    上下文
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error(
					"OperationSceneEventListener.onSceneStartProcess: OjLocalScene not found! oj_local_scene_id=[{}]",
					businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		// 场景启动code值
		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageHandler.pushOperationMessage(dataObj, ojLocalScene);

		// 场景启动完成更新数据库 消息推送
		JSONObject startSceneMessage = ojLocalSceneService.getStartSceneMessage(data, ojLocalScene);
		if (null != startSceneMessage) {
			pushMessageHandler.pushOperationMessage(startSceneMessage, ojLocalScene);
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
	 */
	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		OjLocalScene ojLocalScene = ojLocalSceneService.getById(businessId);
		if (null == ojLocalScene) {
			log.error(
					"OperationEventListener onSceneElement: OjLocalScene not found! oj_local_scene_id=[{}]",
					businessId);
			throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND);
		}

		JSONObject dataObj = JsonUtil.toJsonObject(data, JSONObject.class);
		dataObj.put(JsonKeyConstant.CODE, Event.SCENE_UPDATE.getCode());
		pushMessageHandler.pushOperationMessage(dataObj, ojLocalScene);
		return true;
	}

}
