package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum TaskMode {

	/**
	 * 任务模式
	 */
	QT("0", "其它"),
	DL("1", "独立开展"),
	LH("2", "联合开展"),
	XT("3", "协同开展");

	@Getter
	private String code;

	@Getter
	private String name;

	TaskMode(String code, String name) {
		this.code = code;
		this.name = name;
	}

}
