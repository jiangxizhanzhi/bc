package com.cyberpeace.cr.redvsblue.service.impl;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.cyberpeace.cr.redvsblue.service.impl.LogHandler.BaseCombatLogHandler;

//其他日志处理类
//@Service("operation")
public class RbCommonCombatLogHandler extends BaseCombatLogHandler {

	static {
		 Integer eventCategory = CrCommonConstants.EventCategory.ATTACK.getCode();
	}


//	public RbCommonCombatLogHandler(Map<String, String> initMap) {
//		super(initMap);
//	}
}
