package com.cyberpeace.cr.hld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.service.impl.CrEventListenerHelper;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.context.WsSceneReportBO;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HldSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private CrEventListenerHelper crEventListenerHelper;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.HLD.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		HldRoom room = hldRoomService.getById(businessId);
		if (room != null) {
			hldSceneService.clearLocal(room);
		}
		return true;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return true;
	}

	/**
	 * 场景消息统一推送
	 * @param data
	 * @param businessId
	 */
	private void pushDataToWeb(String data, String businessId) {
		WebSocketMessageContext messageContext = crEventListenerHelper.makeContext(data,
				businessId);
		webSocketHandler.pushMessage(messageContext, true);
	}

}
