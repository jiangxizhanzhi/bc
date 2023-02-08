package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDefenseEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDefenseStage;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class DefenseMissionLogHandler extends DefenseCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.DEFENSE_MISSION.getCode();

	@Override
	public String getCode() {
		return "defense_mission";
	}

	@Override
	public RbCombatRecordsAllDataRsp addData(RbCombatRecordsAllDataRsp data) {
		if (data == null) {
			data = new RbCombatRecordsAllDataRsp();
		}
		addDefenseStage(data);
		addAttackDefenseEvent(data, getInstance());
		return data;
	}

	public RbCombatRecordsAllDataRsp addDefenseStage(RbCombatRecordsAllDataRsp data) {
		RbCombatLog rbCombatLog = getInstance();
		RbDefenseStage rbDefenseStage = RbDefenseStage.builder().resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getDestGroup())
													  .defensePhase(rbCombatLog.getDefensePhase())
													  .content(String.format("【%s】%s", CrCommonConstants.EventType.getEnumByCode(rbCombatLog.getEventType(),
															  CrCommonConstants.EventType.class).getDesc(), rbCombatLog.getEventContent()))
													  .build();
		data.getOther().put(CrCommonConstants.RedVsBlueDockingWebsocket.DEFENSE_STAGE, JsonUtil.java2JsonObject(rbDefenseStage));
		return data;
	}

	public RbDefenseEvent updateDefenseEvent(RbDefenseEvent rbDefenseEvent, RbCombatLog rbCombatLog) {
		rbDefenseEvent.setEventDescribe(rbCombatLog.getEventContent());
		return rbDefenseEvent;
	}
}
