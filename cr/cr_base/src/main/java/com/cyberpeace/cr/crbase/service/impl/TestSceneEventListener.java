package com.cyberpeace.cr.crbase.service.impl;

import com.cyberpeace.common.enums.BusinessCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.scene.MqContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.BusinessTemplateService;
import com.cyberpeace.cr.crbase.service.context.scene.CrSceneWsMessageIdentifier;
import com.cyberpeace.cs.service.impl.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestSceneEventListener extends MessageListener {

	@Autowired
	private MyWebSocketHandler webSocketHandler;

	@Autowired
	private BusinessTemplateService businessTemplateService;

	@Override
	public Integer declareMessageType() {
		return BusinessCode.TEST_SCENE.getCode();
	}

	@Override
	public Boolean onSceneDelete(String data, String businessId, MqContext context) {
		BusinessTemplate template = businessTemplateService.getById(businessId);
		businessTemplateService.clearLocal(template);
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

	private void pushDataToWeb(String data, String businessId) {
		// 构建需要向页面推送的消息上下文
		CrSceneWsMessageIdentifier identifier = new CrSceneWsMessageIdentifier();
		identifier.setPath(WsPath.CR_SCENE);
		identifier.setBusinessId(businessId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder().data(data)
				.messageIdentifier(identifier).build();

		webSocketHandler.pushMessage(messageContext, true);
	}
}
