package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class CommonCombatLogHandler extends BaseCombatLogHandler {

	@Override
	public String getCode() {
		return "common_combat";
	}
}
