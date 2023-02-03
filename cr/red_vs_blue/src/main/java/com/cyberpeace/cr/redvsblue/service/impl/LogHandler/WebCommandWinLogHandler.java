package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class WebCommandWinLogHandler extends WebCommandLogHandler {

	Integer eventType = CrCommonConstants.EventType.WEB_COMMAND_WIN.getCode();

	@Override
	public String getCode() {
		return "web_command_win";
	}
}
