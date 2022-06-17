package com.cyberpeace.situation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsMultiAreaDynamic;
import com.cyberpeace.situation.model.ws.WsMultiElementDynamic;
import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;
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
	public BaseRsp<String> sendAttackEvent(
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
		BaseRsp<String> rsp = new BaseRsp<>(JsonUtil.toJsonString(wsMessage));
		rsp.setMessage("成功推送事件");
		return rsp;
	}

}
