package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

public enum TaskTypeConvert implements MdConvertEnum<String, String> {
	INTELLIGENCE_INVESTIGATION("05b739fc686a073756399f79c34039b5","情报侦查研判"),
	PASSWORD_CRACKING("1102f907c7650ad541c381f59c700f5f","密码破解"),
	TERMINAL_PENETRATION_CONTROL("2eec84346538e972a962b165e499f52d","终端渗透控制"),
	THREAT_DISCOVERY_AND_DETECTION("2f4f15de59cd2dbadc5d8a582e9f6849","威胁发现和检测");

	private String taskType;

	private String desc;

	TaskTypeConvert(String taskType, String desc) {
		this.taskType = taskType;
		this.desc = desc;
	}

	@Override
	public String to() {
		return taskType;
	}

	@Override
	public String reTo() {
		return desc;
	}
}
