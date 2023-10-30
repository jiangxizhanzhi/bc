package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum SessionType {

	ALL("all", "监控路径和与远程协助路径"), ASSISTANCE("assistance", "仅获取远程协助路径");

	@Getter
	private String type;

	private String desc;

	SessionType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}