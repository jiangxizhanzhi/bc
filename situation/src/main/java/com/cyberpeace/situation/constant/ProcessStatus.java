package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 过程状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum ProcessStatus {

	/**
	 * 开始
	 *
	 * @see ProcessStatus
	 */
	START(1, "开始"),
	/**
	 * 进行中
	 *
	 * @see ProcessStatus
	 */
	IN_PROGRESS(2, "进行中"),
	/**
	 * 暂停
	 *
	 * @see ProcessStatus
	 */
	PAUSE(3, "暂停"),
	/**
	 * 已结束
	 *
	 * @see ProcessStatus
	 */
	OVER(4, "已结束"),
	/**
	 * 异常
	 *
	 * @see ProcessStatus
	 */
	ABNORMAL(5, "异常");

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

	ProcessStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
