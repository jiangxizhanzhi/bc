package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义win反弹Shell日志处理类
@Data
@Service
public class WinBounceShellLogHandler extends BounceShellLogHandler {

	Integer eventType = CrCommonConstants.EventType.WIN_BOUNCE_SHELL.getCode();

	@Override
	public String getCode() {
		return "win_bounce_shell";
	}
}
