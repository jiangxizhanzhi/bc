package com.cyberpeace.situation.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.integratation.model.situation.AdEventContext;
import com.cyberpeace.integratation.model.situation.ElementSlimContext;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.EventMessageReq;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.SituationHandlerService;
import com.cyberpeace.situation.service.SituationPublishInstanceService;
import com.cyberpeace.situation.service.TopoHandlerService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "推送态势事件")
@RestController
@RequestMapping("/situation/simulation")
public class SituationEventController {

	@Autowired
	private SituationHandlerService situationHandlerService;

	@Autowired
	private TopoHandlerService topoHandlerService;

	@Autowired
	private SituationPublishInstanceService situationPublishInstanceService;

	@ApiOperation("获取随机攻防动效")
	@GetMapping("attack_event")
	public BaseRsp<List<WsEventAd>> randomEvent(
			@ApiParam(name = "dashboard_id", value = "大屏ID") @RequestParam(value = "dashboard_id",
					required = true) String dashboardId,
			@ApiParam(name = "event_type", value = "事件类型") @RequestParam(value = "event_type",
					required = false) String eventType,
			@ApiParam(name = "event_name", value = "事件名称") @RequestParam(value = "event_name",
					required = false) String eventName) {

		// 获取随机两个节点ID
		String envId = situationPublishInstanceService.getEnvIdByDashboardId(dashboardId);
		Map<String, ElementSlimContext> elementSlimContextMap = topoHandlerService
				.getTopoConfigByEnvId(envId);
		Random random = new Random();
		String[] elementIds = elementSlimContextMap.keySet().toArray(new String[0]);
		String sourceId = elementIds[random.nextInt(elementIds.length)];
		String targetId = elementIds[random.nextInt(elementIds.length)];

		// 构造事件数据
		AdEventContext adEventContext = new AdEventContext(sourceId, targetId, eventName,
				eventType);

		// 处理事件数据
		List<WsEventAd> eventAds = situationHandlerService
				.eventContext2EventAd(Lists.newArrayList(adEventContext), elementSlimContextMap);

		// 推送攻防事件数据
		situationHandlerService.pushEventAds(eventAds, dashboardId, null);

		// 返回消息数据
		BaseRsp<List<WsEventAd>> rsp = new BaseRsp<>(eventAds);
		rsp.setMessage("成功推送事件");
		return rsp;
	}

	@ApiOperation("推送多节点动效")
	@PostMapping("multi_event")
	public BaseRsp<WsMessage<WsMultiNodeDynamic>>
			multiEvent(@RequestBody EventMessageReq eventMessageReq) {
		String code = eventMessageReq.getCode();
		String sceneId = eventMessageReq.getSceneId();
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(code, eventMessageReq.getData());
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}

	@ApiOperation("推送单节点动效")
	@PostMapping("single_event")
	public BaseRsp<WsMessage<WsSingleNodeDynamic>>
			singleEvent(@RequestBody WsSingleNodeDynamic eventMessageReq) {
		String code = MessageType.SingleNodeDynamic.getDesc();
		String sceneId = eventMessageReq.getSceneId();
		WsMessage<WsSingleNodeDynamic> wsMessage = new WsMessage<>(code, eventMessageReq);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}

	@ApiOperation("推送多媒体动效")
	@PostMapping("media_event")
	public BaseRsp<WsMessage<WsAttackEffectDynamic>>
			mediaEvent(@RequestBody WsAttackEffectDynamic wsAttackEffectDynamic) {
		String code = MessageType.AttackEffect.getDesc();
		String sceneId = wsAttackEffectDynamic.getSceneId();
		WsMessage<WsAttackEffectDynamic> wsMessage = new WsMessage<>(code, wsAttackEffectDynamic);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);
		return new BaseRsp<>(wsMessage);
	}
}
