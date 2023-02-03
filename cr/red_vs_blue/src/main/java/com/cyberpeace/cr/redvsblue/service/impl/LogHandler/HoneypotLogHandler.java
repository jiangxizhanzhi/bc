package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class HoneypotLogHandler extends AttackCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.HONEYPOT.getCode();
	Integer attackPhase = CrCommonConstants.AttackPhase.RECONNAISSANCE.getCode();
	Integer attackAction = CrCommonConstants.AttackAction.NET_SCAN.getCode();
	Integer actionType = CrCommonConstants.ActionType.RECONNAISSANCE.getCode();

	@Override
	public String getCode() {
		return "honeypot";
	}
}
