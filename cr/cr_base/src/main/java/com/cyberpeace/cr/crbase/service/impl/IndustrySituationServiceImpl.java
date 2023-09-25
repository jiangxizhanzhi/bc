package com.cyberpeace.cr.crbase.service.impl;

import java.util.List;
import java.util.concurrent.Executor;

import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.PropertyNamingStrategy;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.crbase.dao.entity.Mission;
import com.cyberpeace.cr.crbase.dao.entity.MissionSituation;
import com.cyberpeace.cr.crbase.model.situation.IndustrySituationEvent;
import com.cyberpeace.cr.crbase.model.situation.IndustrySituationWsMessageIdentifier;
import com.cyberpeace.cr.crbase.service.IndustrySituationService;
import com.cyberpeace.cr.crbase.service.MissionService;
import com.cyberpeace.cr.crbase.service.MissionSituationService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IndustrySituationServiceImpl implements IndustrySituationService {

	@Autowired
	private MissionSituationService missionSituationService;

	@Autowired
	private MissionService missionService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	@Qualifier("taskExecutor")
	private Executor executor;

	@Override
	public boolean pushSituationWs(String roomId, String missionTemplateId) {
		MissionSituation missionSituation = missionSituationService.getById(missionTemplateId);
		// 未配置态势
		if (null == missionSituation) {
			return false;
		}
		return pushSituationWs(roomId, missionSituation.getTsId());
	}

	@Override
	public boolean pushSituationWs(String businessId, Integer tsId) {
		IndustrySituationWsMessageIdentifier identifier = new IndustrySituationWsMessageIdentifier();
		identifier.setPath(WsPath.INDUSTRY_SITUATION);
		identifier.setBusiness_id(businessId);

		IndustrySituationEvent event = new IndustrySituationEvent();
		event.setCode(tsId);

		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.data(JsonUtil.toJsonString(event, PropertyNamingStrategy.SnakeCase))
				.messageIdentifier(identifier).build();

		myWebSocketHandler.pushMessage(messageContext, true);
		return true;
	}

	@Override
	public boolean doDemoEffect(String businessId, Integer tsId) {
		if (StringUtils.isBlank(businessId) || null == tsId) {
			return false;
		}
		if (tsId != 0) {
			return this.pushSituationWs(businessId, tsId);
		}
		List<Mission> missionList = missionService.batchGetByRoom(businessId);
		List<String> mtIds = StreamApiUtils.map(missionList, Mission::getMissionTemplateId);
		List<MissionSituation> situationList = missionSituationService.getBatchByIds(mtIds);
		executor.execute(() -> {
			for (MissionSituation situation : situationList) {
				this.pushSituationWs(businessId, situation.getTsId());
				try {
					Thread.sleep(5000L);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		});
		return true;
	}

}
