package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义Windows病毒查杀日志处理类
@Data
@Service
public class VirusDetectWinLogHandler extends VirusDetectLogHandler {

	Integer eventType = CrCommonConstants.EventType.VIRUS_DETECT_WIN.getCode();

	@Override
	public String getCode() {
		return "virus_detect_win";
	}
}
