package com.cyberpeace.situation.controller;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.integratation.service.RemoteSceneService;
import com.cyberpeace.situation.constant.Color;
import com.cyberpeace.situation.constant.LineType;
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.LineStyle;
import com.cyberpeace.situation.model.ws.WsEvent;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
import com.cyberpeace.situation.model.ws.WsSingleNodeDynamic;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势消息推送测试")
@RestController
@RequestMapping("/situation/ws")
public class TopoTestController {

	@Autowired
	private RemoteSceneService remoteSceneService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@ApiOperation("随机推送单点动效信息")
	@GetMapping("single_message")

	public JSONObject sendSingleMessage(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id", required = true) String sceneId) {

		JSONArray elements = getElements(sceneId);
		WsSingleNodeDynamic wsEvent = new WsSingleNodeDynamic();
		wsEvent.setSceneId(sceneId);
		ElementInfo elementInfo = topoHandlerService.getRandomElements(elements, 1).get(0);
		wsEvent.setElementInfo(elementInfo);
		wsEvent.setElementId(elementInfo.getElementId());
		WsMessage<WsSingleNodeDynamic> wsMessage = new WsMessage<>(
				MessageType.SingleNodeDynamic.getDesc(), wsEvent);
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
		topoHandlerService.pushDataToRoom(data, sceneId);
		return data;
	}

	@ApiOperation("随机推送多点动效信息")
	@GetMapping("multi_message")
	public JSONObject sendMultiMessage(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id", required = true) String sceneId) {

		JSONArray elements = getElements(sceneId);
		WsMultiNodeDynamic wsEvent = new WsMultiNodeDynamic();
		wsEvent.setSceneId(sceneId);
		wsEvent.setTime((new Date()).toString());
		wsEvent.setCategory("attack");
		ActionType actionType = ActionType.getRandom(ActionType.values());
		wsEvent.setType(actionType.getCode());
		wsEvent.setName(actionType.getDesc());

		List<ElementInfo> randomElements = topoHandlerService.getRandomElements(elements, 2);
		ElementInfo sourceElement = randomElements.get(0);
		ElementInfo targetElement = randomElements.get(1);
		wsEvent.setSourceElementInfo(sourceElement);
		wsEvent.setSourceId(sourceElement.getElementId());
		wsEvent.setTargetElementInfo(targetElement);
		wsEvent.setTargetId(targetElement.getElementId());

		// 设置动效配置
		wsEvent.setLineStyle(new LineStyle(Color.RED.getCode(), 5, LineType.DASHED_FLY.getCode()));
		wsEvent.setLookAtTarget(new Random().nextBoolean());

		// 设置事件描述
		String sourceName = sourceElement.getName();
		String targetName = targetElement.getName();
		wsEvent.setDescription(
				String.format("%s对%s发起%s", sourceName, targetName, actionType.getDesc()));

		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(
				MessageType.MultiNodeDynamic.getDesc(), wsEvent);
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
		topoHandlerService.pushDataToRoom(data, sceneId);
		return data;
	}

	@ApiOperation("随机推送事件信息")
	@GetMapping("event")
	public BaseRsp<WsEvent> sendEventMessage(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id", required = true) String sceneId) {

		JSONArray elements = getElements(sceneId);
		WsEvent wsEvent = new WsEvent();
		wsEvent.setAttackType(ActionType.getRandomValue(ActionType.values()));
		List<ElementInfo> randomElements = topoHandlerService.getRandomElements(elements, 2);
		wsEvent.setSourceId(randomElements.get(0).getElementId());
		wsEvent.setTargetId(randomElements.get(1).getElementId());
		wsEvent.setLineStyle(topoHandlerService.getLineStyle());
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsEvent), JSONObject.class);
		topoHandlerService.pushDataToRoom(data, sceneId);
		return new BaseRsp<>(wsEvent);
	}

	private JSONArray getElements(String sceneId) {
		JSONObject sceneTopo = remoteSceneService.getById(sceneId, null);
		JSONObject topology = sceneTopo.getJSONObject("scene").getJSONObject("topology");
		return (JSONArray) topology.getOrDefault("elements",
				JSONArray.parseArray(Lists.newArrayList().toString()));
	}
}
