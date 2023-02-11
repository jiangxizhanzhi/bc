package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import lombok.Data;
import org.springframework.stereotype.Service;

// 定义其它日志处理类
@Data
@Service
public class CommonCombatLogHandler extends BaseCombatLogHandler {

	@Override
	public String getCode() {
		return "common_combat";
	}
}
