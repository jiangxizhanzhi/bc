package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum BcStatus {

	/**
	 * 站点状态
	 */
	YJC("1", "已建成"), WJC("2", "未建成");

	@Getter
	private String code;

	@Getter
	private String name;

	BcStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

}
