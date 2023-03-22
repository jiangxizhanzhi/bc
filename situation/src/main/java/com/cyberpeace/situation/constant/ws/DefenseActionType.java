package com.cyberpeace.situation.constant.ws;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum DefenseActionType implements GenericEnum {

	VIRUS_DETECTION_AND_TREATMENT(1, "病毒检测与处理"),
	SYSTEM_TROUBLESHOOTING_AND_ANALYSIS(2, "系统排查与分析"),
	SECURITY_FORENSICS(3, "安全取证"),
	SECURITY_REINFORCEMENT(4, "安全加固"),
	SYSTEM_REPAIR(5, "系统修复"),
	DEFENSE_DEPLOYMENT(6, "防御部署");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	DefenseActionType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
