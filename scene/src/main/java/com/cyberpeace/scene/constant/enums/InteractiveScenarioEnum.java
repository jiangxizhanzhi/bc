package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum InteractiveScenarioEnum implements GenericEnum {

	RANSOMWARE(1, "勒索病毒"), U_DISK(2, "U盘攻击"), PHISHING(3, "钓鱼网站"), SHEEP_WALL(4, "绵羊墙");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	InteractiveScenarioEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
