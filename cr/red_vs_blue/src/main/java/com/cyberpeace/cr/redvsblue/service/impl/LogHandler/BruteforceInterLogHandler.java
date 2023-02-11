package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

// 定义内网暴力破解日志处理类
@Data
@Service
public class BruteforceInterLogHandler extends BruteforceExtLogHandler{

	Integer eventType = CrCommonConstants.EventType.BRUTEFORCE_INTER.getCode();
	Integer attackPattern = CrCommonConstants.AttackPattern.BRUTEFORCE_INTER.getCode();

	@Override
	public String getCode() {
		return "bruteforce_inter";
	}
}
