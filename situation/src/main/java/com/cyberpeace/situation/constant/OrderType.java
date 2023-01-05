package com.cyberpeace.situation.constant;

import lombok.Getter;

public enum OrderType {

	/**
	 * 指令类型
	 */

	CS("initialize", "初始化", null),
	KS("begin", "开始", null),
	ZT("stop", "暂停", "pause"),
	JX("continue", "继续", "recovery"),
	ZZ("termination", "终止", "stop"),
	HS("recycle", "资源回收", null),
	QT("other", "其它", null);

	@Getter
	private String code;

	@Getter
	private String name;

	@Getter
	private String action;

	OrderType(String code, String name, String action) {
		this.code = code;
		this.name = name;
		this.action = action;
	}
}
