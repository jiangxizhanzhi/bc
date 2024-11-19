package com.cyberpeace.integratation.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 比赛状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum CompetitionStatus implements GenericEnum {

	/**
	 * 未开赛
	 *
	 * @see CompetitionStatus
	 */

	NO_START(0, "未开赛"),
	/**
	 * 进行中
	 *
	 * @see CompetitionStatus
	 */
	START(1, "进行中"),
	/**
	 * 暂停
	 *
	 * @see CompetitionStatus
	 */
	PAUSE(2, "暂停"),
	/**
	 * 结束
	 *
	 * @see CompetitionStatus
	 */
	END(3, "结束");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
    private Integer code;

	/**
	 * 描述
	 *
	 * @see String
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
