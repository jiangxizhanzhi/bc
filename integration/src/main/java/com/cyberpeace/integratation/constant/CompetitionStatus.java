package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 比赛状态
 *
 * @author SN
 * @date 2024/11/14
 */
public enum CompetitionStatus implements GenericEnum {

	/**
	 * 未开赛
	 */

	NO_START(0, "未开赛"),
	/**
	 *进行中
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

	/**
	 * 比赛状态
	 *
	 * @param code 代号
	 * @param desc 描述
	 */
	CompetitionStatus(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
