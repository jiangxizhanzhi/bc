package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.springframework.stereotype.Service;

//	定义攻击任务日志处理类
@Data
@Service
public class AttackMissionLogHandler extends DefenseCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.ATTACK_MISSION.getCode();

	@Override
	public String getCode() {
		return "attack_mission";
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent attackEvent, RbCombatLog rbCombatLog) {
		attackEvent.setEventDescribe(rbCombatLog.getEventContent());
		return attackEvent;
	}

	@Override
	public RbAttackEvent updateDefenseEvent(RbAttackEvent rbDefenseEvent, RbCombatLog rbCombatLog) {
		return null;
	}
}
