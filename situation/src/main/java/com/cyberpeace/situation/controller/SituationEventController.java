package com.cyberpeace.situation.controller;

import com.cyberpeace.situation.dao.entity.ScenePro;
import com.cyberpeace.situation.req.SceneProReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.situation.constant.ws.MessageType;
import com.cyberpeace.situation.controller.req.EventMessageReq;
import com.cyberpeace.situation.model.ws.*;
import com.cyberpeace.situation.service.SituationHandlerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

/**
 * 态势事件Controller
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Api(tags = "推送态势事件")
@RestController
@RequestMapping("/situation/simulation")
public class SituationEventController {

	/**
	 * 态势处理器Service
	 *
	 * @see SituationHandlerService
	 */
	@Autowired
	private SituationHandlerService situationHandlerService;

	/**
	 * 随机事件
	 *
	 * @param sceneId    场景id
	 * @param actionCode 行动代号
	 * @return {@code BaseRsp<WsMessage<WsMultiNodeDynamic>> }
	 * @throws
	 * @see String
	 * @see Integer
	 * @see BaseRsp<WsMessage<WsMultiNodeDynamic>>
	 */
	@ApiOperation("获取随机普通动效")
	@GetMapping("attack_event")
	public BaseRsp<WsMessage<WsMultiNodeDynamic>> randomEvent(
			@ApiParam(name = "scene_id", value = "场景编号") @RequestParam(value = "scene_id",
					required = true) String sceneId,
			@ApiParam(name = "action_code", value = "事件类型") @RequestParam(value = "action_code",
					required = false) Integer actionCode) {

		// 获取动效数据
		WsMultiNodeDynamic wsEvent = situationHandlerService.getRandomWsMultNodeDynamic(sceneId,
				actionCode);

		// 设置消息代码
		String messageType = MessageType.AttackEvent.getDesc();
		if (wsEvent instanceof WsMultiElementDynamic) {
			messageType = MessageType.AttackEventEE.getDesc();
		} else if (wsEvent instanceof WsMultiAreaDynamic) {
			messageType = MessageType.AttackEventEA.getDesc();
		}

		// 推送ws消息
		WsMessage<WsMultiNodeDynamic> wsMessage = new WsMessage<>(messageType, wsEvent);
		situationHandlerService.pushDataToRoom(wsMessage, sceneId);

		// 返回消息数据
		BaseRsp<WsMessage<WsMultiNodeDynamic>> rsp = new BaseRsp<>(wsMessage);
		rsp.setMessage("成功推送事件");
		return rsp;
	}

	/**
	 * 多事件
	 *
	 * @param eventMessageReq 事件消息请求体
	 * @return {@code BaseRsp<WsMessage<WsMultiNodeDynamic>> }
	 * @throws
	 * @see EventMessageReq
	 * @see BaseRsp<WsMessage<WsMultiNodeDynamic>>
	 */
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

	/**
	 * 单个事件
	 *
	 * @param eventMessageReq 事件消息请求体
	 * @return {@code BaseRsp<WsMessage<WsSingleNodeDynamic>> }
	 * @throws
	 * @see WsSingleNodeDynamic
	 * @see BaseRsp<WsMessage<WsSingleNodeDynamic>>
	 */
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

	/**
	 * 媒体事件
	 *
	 * @param wsAttackEffectDynamic WS攻击动效动态
	 * @return {@code BaseRsp<WsMessage<WsAttackEffectDynamic>> }
	 * @throws
	 * @see WsAttackEffectDynamic
	 * @see BaseRsp<WsMessage<WsAttackEffectDynamic>>
	 */
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

	@PostMapping("write/scene/property")
	public BaseRsp writePro(@RequestBody SceneProReq req) {
		situationHandlerService.writePro(req);
		return new BaseRsp<>();
	}

	@GetMapping("read/scene/property")
	public BaseRsp readPro(@RequestParam(value = "scene") String scene) {
		List<ScenePro> result = situationHandlerService.readPro(scene);
		return new BaseRsp<>(result);
	}
}
