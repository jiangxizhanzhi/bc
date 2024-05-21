package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * @author SN
 * @date 2024/05/20
 */
public enum BehaviorTypeEnum implements GenericEnum, BaseI18n {

	/**
	 * 操作类型
	 */

	CREATE(1, "使用模板", ""),
	ACCESS(2, "访问", ""),
	ACCESS_LESSON(4, "访问课时", ""),
	ANSWER(5, "答题", ""),
	ADD_TO_FAVORITE(6, "收藏", ""),
	START_SCENE(7, "启动场景", "");

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	BehaviorTypeEnum(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return getMsg();
	}
}
