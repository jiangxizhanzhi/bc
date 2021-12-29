package com.cyberpeace.cr.redvsblue.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RbSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbSceneService rbSceneService;

	@Override
	public Integer declareMessageType() {
		return LocalScene.BusinessCode.RED_VS_BLUE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		RbRoom room = rbRoomService.getById(businessId);
		rbSceneService.clearLocal(room);
		return false;
	}

	@Override
	public Boolean onSceneStartProcess(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);
		return false;
	}

	@Override
	public Boolean onSceneElement(String data, String businessId, MqContext context) {
		pushDataToWeb(data, businessId);

		return false;
	}

	private void pushDataToWeb(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusiness_id(businessId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		webSocketHandler.pushMessage(messageContext, true);
	}
}
