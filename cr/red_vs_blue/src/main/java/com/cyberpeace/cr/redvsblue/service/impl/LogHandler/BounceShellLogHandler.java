package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class BounceShellLogHandler extends AttackCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.BOUNCE_SHELL.getCode();
	Integer attackPhase = CrCommonConstants.AttackPhase.ACTIONS.getCode();
	Integer attackAction = CrCommonConstants.AttackAction.BOUNCE_SHELL.getCode();
	Integer actionType = CrCommonConstants.ActionType.COMMAND_CONTROL.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.BOUNCE_SHELL.getCode();
	Boolean isLostHostSign = true;

	@Override
	public String getCode() {
		return "bounce_shell";
	}

	@Override
	public String getSourceIp() {
		return getInitData().get("detail.dst_ip");
	}
}
