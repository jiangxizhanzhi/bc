package com.cyberpeace.cr.redvsblue.service.impl.LogHandler;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import lombok.Data;
import org.springframework.stereotype.Service;

@Data
@Service
public class WebshellLogHandler extends BackdoorDiagnoseLogHandler {

	Integer eventType = CrCommonConstants.EventType.WEBSHELL.getCode();

	@Override
	public String getCode() {
		return "webshell";
	}

	@Override
	public void save() {
		super.save();
	}
}
