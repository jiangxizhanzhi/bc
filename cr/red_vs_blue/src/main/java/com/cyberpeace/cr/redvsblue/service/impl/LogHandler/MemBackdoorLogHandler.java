package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class MemBackdoorLogHandler extends WebshellLogHandler {

	Integer eventType = CrCommonConstants.EventType.MEM_BACKDOOR.getCode();

	@Override
	public String getCode() {
		return "mem_backdoor";
	}
}
