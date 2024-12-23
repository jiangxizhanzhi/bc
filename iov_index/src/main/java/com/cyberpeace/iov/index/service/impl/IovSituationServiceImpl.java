package com.cyberpeace.iov.index.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import com.cyberpeace.iov.index.service.IovSituationService;
import com.cyberpeace.iov.index.service.bo.can.IovSituationIdentifier;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IovSituationServiceImpl implements IovSituationService {

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

    /**
     * 推送态势WS消息
     */
    @Override
	public void pushSituationWsMsg(JSONObject data) {
		IovSituationIdentifier identifier = new IovSituationIdentifier();
		identifier.setPath(WsPath.IOV);
		// identifier.setFrame(frameId);

		WebSocketMessageContext<WsMessageIdentifier, Object> messageContext = WebSocketMessageContext
				.builder().messageIdentifier(identifier).data(data).build();
		myWebSocketHandler.pushMessage(messageContext, true);
	}
}
