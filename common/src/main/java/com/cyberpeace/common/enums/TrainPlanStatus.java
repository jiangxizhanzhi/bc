package com.cyberpeace.common.enums;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.constant.BaseI18n;

import lombok.Getter;

public enum TrainPlanStatus implements GenericEnum, BaseI18n {

    /**
     * 未开始
     *
     * @see TrainPlanStatus
     */
    NOT_STARTED(1, "未开始", "Not Started"),
    /**
     * 进行中
     *
     * @see TrainPlanStatus
     */
    ONGOING(2, "进行中", "Ongoing"),
    /**
     * 已结束
     *
     * @see TrainPlanStatus
     */
    ENDED(3, "已结束", "Ended"),
    ;

	@Getter
	private final Integer code;

	@Getter
	private final String chinese;

	@Getter
	private final String english;

	TrainPlanStatus(Integer code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return this.getMsg();
	}

}
