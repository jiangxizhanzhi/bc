package com.cyberpeace.integratation.constant;

public enum SessionType {

	ALL("all", "监控路径和与远程协助路径"), ASSISTANCE("assistance", "仅获取远程协助路径");

	public String type;

	public String desc;

	SessionType(String type, String desc) {
		this.type = type;
		this.desc = desc;
	}

}