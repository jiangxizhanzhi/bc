package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;


import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class ExcepLoginLogHandler extends DefenseCombatLogHandler {

	Integer eventType = CrCommonConstants.EventType.EXCEP_LOGIN.getCode();
	Integer defenseAction = CrCommonConstants.DefenseAction.ATTACK_SOURCE_DETECTED.getCode();

	@Override
	public String getCode(){
		return "excep_login";
	}

	@Override
	public Integer getSourceGroup() {
		return null;
	}
}
