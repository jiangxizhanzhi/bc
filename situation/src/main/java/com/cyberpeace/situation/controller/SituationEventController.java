package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.EventMessageReq;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "态势事件")
@RestController
@RequestMapping("/situation/simulation")
public class SituationEventController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@ApiOperation("随机推送攻击动效信息")
	@GetMapping("attack_event")
	public BaseRsp<WsMessage<WsMultiNodeDynamic>> sendAttackEvent(
			@ApiParam(name = "scene_id", value = "场景编号") @RequestParam(value = "scene_id",
					required = true) String sceneId,
			@ApiParam(name = "action_code", value = "事件类型") @RequestParam(value = "action_code",
					required = false) Integer actionCode) {

		// 获取动效数据
		WsMultiNodeDynamic wsEvent = situationHandlerService.getRandomWsMultNodeDynamic(sceneId,
				actionCode);

		// 设置消息代码
		String messageType = MessageType.AttackEvent.getDesc();
		if (WsMultiElementDynamic.class.isInstance(wsEvent)) {
			messageType = MessageType.AttackEventEE.getDesc();
		} else if (WsMultiAreaDynamic.class.isInstance(wsEvent)) {
			messageType = MessageType.AttackEventEA.getDesc();
		}

		// 推送ws消息
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(messageType, wsEvent);
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
		situationHandlerService.pushDataToRoom(data, sceneId);

		// 返回消息数据
		BaseRsp<WsMessage<WsMultiNodeDynamic>> rsp = new BaseRsp<>(wsMessage);
		rsp.setMessage("成功推送事件");
		return rsp;
	}

	@ApiOperation("推送指定攻击动效信息")
	@PostMapping("event_message")
	public BaseRsp<Boolean> sendEventMessage(@RequestBody EventMessageReq eventMessageReq) {
		String code = eventMessageReq.getCode();
		String sceneId = eventMessageReq.getSceneId();
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(code, eventMessageReq.getData());
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
		situationHandlerService.pushDataToRoom(data, sceneId);
		return new BaseRsp<>(true);
	}

	@ApiOperation("推送指定攻击效果信息")
	@PostMapping("event_effect")
	public BaseRsp<Boolean>
			sendEventEffectMessage(@RequestBody WsAttackEffectDynamic wsAttackEffectDynamic) {
		String code = MessageType.AttackEffect.getDesc();
		String sceneId = wsAttackEffectDynamic.getSceneId();
		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(code, wsAttackEffectDynamic);
		JSONObject data = JsonUtil.toJsonObject(JsonUtil.toJsonString(wsMessage), JSONObject.class);
		situationHandlerService.pushDataToRoom(data, sceneId);
		return new BaseRsp<>(true);
	}
}
