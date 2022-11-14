package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum SituationInstanceStatus {

	/**
	 * 过程状态
	 */

	DRAFT("1", "草稿"),
	RELEASE("2", "发布");

	@Getter
	private String code;

	@Getter
	private String desc;

	SituationInstanceStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
