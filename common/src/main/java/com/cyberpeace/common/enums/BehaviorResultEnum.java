package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

public enum BehaviorResultEnum implements GenericEnum, BaseI18n {

	/**
	 * 行为结果
	 */
	DEFAULT(2, "默认", ""),
    CORRECT(1, "答题正确", ""),
    WRONG(0, "答题错误", "");

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	BehaviorResultEnum(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return getMsg();
	}
}
