package com.cyberpeace.scene.constant.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

public enum ImageRoleEnum implements GenericEnum {

	HACKER(1, "黑客机器"), VICTIM(2, "受害人机器");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	ImageRoleEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
