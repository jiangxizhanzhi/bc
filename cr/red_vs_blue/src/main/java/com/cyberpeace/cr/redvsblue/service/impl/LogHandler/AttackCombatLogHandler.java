package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

//	定义攻击日志处理类
@Data
@Service
public class AttackCombatLogHandler extends BaseCombatLogHandler {

	Integer eventCategory = CrCommonConstants.EventCategory.ATTACK.getCode();

	@Override
	public String getCode() {
		return "attack_combat";
	}
}
