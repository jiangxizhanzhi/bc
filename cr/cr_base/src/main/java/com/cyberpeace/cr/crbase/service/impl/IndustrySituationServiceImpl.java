package com.cyberpeace.cr.crbase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;
import com.cyberpeace.cr.crbase.model.situation.IndustrySituationEvent;
import com.cyberpeace.cr.crbase.model.situation.IndustrySituationWsMessageIdentifier;
import com.cyberpeace.cr.crbase.service.IndustrySituationService;
import com.cyberpeace.cr.crbase.service.MissionSituationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndustrySituationServiceImpl implements IndustrySituationService {

	@Autowired
	private MissionSituationService missionSituationService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Override
	public boolean pushSituationWs(String roomId, String missionTemplateId) {
		MissionSituation missionSituation = missionSituationService.getById(missionTemplateId);
		// 未配置态势
		if (null == missionSituation) {
			return false;
		}

		IndustrySituationWsMessageIdentifier identifier = new IndustrySituationWsMessageIdentifier();
		identifier.setPath(WsPath.INDUSTRY_SITUATION);
		identifier.setBusiness_id(roomId);

		IndustrySituationEvent event = new IndustrySituationEvent();
		event.setCode(missionSituation.getTsId());

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.data(JsonUtil.toJsonString(event, PropertyNamingStrategy.SnakeCase))
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
		return true;
	}

}
