package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义防守日志处理类
@Data
@Service
public class DefenseCombatLogHandler extends BaseCombatLogHandler {

	Integer eventCategory = CrCommonConstants.EventCategory.DEFENSE.getCode();

	@Override
	public String getCode() {
		return "defense_combat";
	}
}
