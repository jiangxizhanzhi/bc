package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum PublicStatus {

	/**
	 * 大屏下发状态
	 */

	ON(1, "已下发"),
	OFF(2, "未下发");

	@Getter
	private Integer code;

	@Getter
	private String name;

	PublicStatus(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

}
