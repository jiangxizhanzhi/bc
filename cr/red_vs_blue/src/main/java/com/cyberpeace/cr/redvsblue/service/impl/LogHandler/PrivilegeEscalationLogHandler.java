package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbAttackEvent;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class PrivilegeEscalationLogHandler extends AttackCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.PRIVILEGE_ESCALATION.getCode();
	Integer attackPhase = CrCommonConstants.AttackPhase.COMMAND.getCode();
	Integer attackAction = CrCommonConstants.AttackAction.HOST_FALL.getCode();
	Integer actionType = CrCommonConstants.ActionType.PRIVILEGE_ESCALATION.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.PRIVILEGE_ESCALATION.getCode();
	Boolean isLostHostSign = true;

	@Override
	public String getCode() {
		return "privilege_escalation";
	}

	@Override
	public RbAttackEvent updateAttackEvent(RbAttackEvent rbAttackEvent, RbCombatLog rbCombatLog) {
		String sourceGroup = rbAttackEvent.getSourceGroup();
		String destMachine = rbAttackEvent.getDestMachine();
		rbAttackEvent.setEventDescribe(String.format("%s拿到%s的【命令执行权限】", sourceGroup, destMachine));
		return rbAttackEvent;
	}
}
