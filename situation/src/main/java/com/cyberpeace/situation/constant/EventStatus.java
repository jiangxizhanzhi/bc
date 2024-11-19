package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 事件状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum EventStatus {

	/**
	 * 初始化
	 *
	 * @see EventStatus
	 */
	INITIAL(0, "初始化"),
	/**
	 * 开始
	 *
	 * @see EventStatus
	 */
	START(1, "开始"),
	/**
	 * 进行中
	 *
	 * @see EventStatus
	 */
	IN_PROGRESS(2, "进行中"),
	/**
	 * 暂停
	 *
	 * @see EventStatus
	 */
	PAUSE(3, "暂停"),
	/**
	 * 已结束
	 *
	 * @see EventStatus
	 */
	OVER(4, "已结束"),
	/**
	 * 异常
	 *
	 * @see EventStatus
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

	EventStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
