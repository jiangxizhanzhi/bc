package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

//	定义Windows后门检测日志处理类
@Data
@Service
public class BackdoorDiagnoseWinLogHandler extends BackdoorDiagnoseLogHandler {

	Integer eventType = CrCommonConstants.EventType.BACKDOOR_DIAGNOSE_WIN.getCode();

	@Override
	public String getCode() {
		return "backdoor_diagnose_win";
	}
}
