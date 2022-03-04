package com.cyberpeace.situation.controller;

import java.util.List;

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
import com.cyberpeace.situation.constant.ws.ActionType;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.ws.WsEvent;
import com.cyberpeace.situation.model.ws.WsMultiNodeEffect;
import com.cyberpeace.situation.model.ws.WsSingleNodeEffect;
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
	public BaseRsp<WsSingleNodeEffect> sendSingleMessage(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id", required = true) String sceneId) {

		JSONArray elements = getElements(sceneId);
		WsSingleNodeEffect wsSingleNodeEffect = new WsSingleNodeEffect();
		wsSingleNodeEffect.setSceneId(sceneId);
		wsSingleNodeEffect.setCode(MessageType.SingleNodeEffect.getDesc());
		wsSingleNodeEffect.setElementInfo(topoHandlerService.getRandomElements(elements, 1).get(0));
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsSingleNodeEffect),
				JSONObject.class);
		topoHandlerService.pushDataToRoom(data, sceneId);
		return new BaseRsp<>(wsSingleNodeEffect);
	}

	@ApiOperation("随机推送多点动效信息")
	@GetMapping("multi_message")
	public BaseRsp<WsMultiNodeEffect> sendMultiMessage(@ApiParam(name = "scene_id",
			value = "场景编号") @RequestParam(value = "scene_id", required = true) String sceneId) {

		JSONArray elements = getElements(sceneId);
		WsMultiNodeEffect wsMultiNodeEffect = new WsMultiNodeEffect();
		wsMultiNodeEffect.setSceneId(sceneId);
		wsMultiNodeEffect.setCode(MessageType.MultiNodeEffect.getDesc());
		List<ElementInfo> randomElements = topoHandlerService.getRandomElements(elements, 2);
		wsMultiNodeEffect.setSourceElementInfo(randomElements.get(0));
		wsMultiNodeEffect.setDestElementInfo(randomElements.get(1));
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMultiNodeEffect),
				JSONObject.class);
		topoHandlerService.pushDataToRoom(data, sceneId);
		return new BaseRsp<>(wsMultiNodeEffect);
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
