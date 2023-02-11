package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbCombatRecordsAllDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackDefenseStage;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbDefenseEvent;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义防御任务日志处理类
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
		RbAttackDefenseStage rbDefenseStage = RbAttackDefenseStage.builder().resourceId(rbCombatLog.getResourceId()).group(rbCombatLog.getDestGroup())
													  .attackPhase(rbCombatLog.getDefensePhase())
													  .content(String.format("【%s】%s", CrCommonConstants.EventType.getEnumByCode(rbCombatLog.getEventType(),
															  CrCommonConstants.EventType.class).getDesc(), rbCombatLog.getEventContent()))
													  .build();
		data.getRbDefenseStages().add(rbDefenseStage);
		return data;
	}

	public RbDefenseEvent updateDefenseEvent(RbDefenseEvent rbDefenseEvent, RbCombatLog rbCombatLog) {
		rbDefenseEvent.setEventDescribe(rbCombatLog.getEventContent());
		return rbDefenseEvent;
	}
}
