package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

/**
 * @author SN
 * @date 2024/05/20
 */
public enum BehaviorModuleEnum implements GenericEnum, BaseI18n {

	/***/
	COURSE(2, "课程训练", "course"),
    OPERATION(9, "实操题", "OPERATION"),
    THEORY(10, "理论题", "theory"),
    RED_VS_BLUE(16, "红蓝对抗", "cyber wargame"),
    SINGLE_TRAIN(17, "单兵训练", "individual train"),
    TEAM_TRAIN(18, "团队训练", "team train");

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	BehaviorModuleEnum(Integer code, String chinese) {
		this.code = code;
		this.chinese = chinese;
		this.english = this.name();
	}

	BehaviorModuleEnum(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return getMsg();
	}
}
