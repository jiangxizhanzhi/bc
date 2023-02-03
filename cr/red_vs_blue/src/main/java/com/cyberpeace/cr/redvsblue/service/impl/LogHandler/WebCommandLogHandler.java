package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class WebCommandLogHandler extends PrivilegeEscalationLogHandler {

	Integer eventType = CrCommonConstants.EventType.WEB_COMMAND.getCode();
	Integer actionType = CrCommonConstants.ActionType.COMMAND_CONTROL.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.WEB_COMMAND.getCode();

	@Override
	public String getCode() {
		return "web_command";
	}

	@Override
	public String getAttackStageContent(RbCombatLog rbCombatLog) {
		return String.format("【%s】", CrCommonConstants.EventType.getEnumByCode(rbCombatLog.getEventType(), CrCommonConstants.EventType.class).getDesc());
	}

}
