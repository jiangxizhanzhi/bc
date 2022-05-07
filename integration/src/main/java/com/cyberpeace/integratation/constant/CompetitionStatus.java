package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

public enum CompetitionStatus implements GenericEnum {

	/** 比赛状态*/
	NO_START(0, "未开赛"),
	START(1, "进行中"),
	PAUSE(2, "暂停"),
	END(3, "结束");

	@Getter
    private Integer code;

	@Getter
	private String desc;

	CompetitionStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
