package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 竞争状态
 *
 * @author SN
 * @date 2024/11/11
 */
public enum CompetitionStatus implements GenericEnum {

	/**
	 * 比赛状态
	 */
	NO_START(0, "未开赛"),
	/**
	 * 开始
	 */
	START(1, "进行中"),
	/**
	 * 暂停
	 */
	PAUSE(2, "暂停"),
	/**
	 * 结束
	 */
	END(3, "结束");

	/**
	 * 代号
	 */
	@Getter
    private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	CompetitionStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
