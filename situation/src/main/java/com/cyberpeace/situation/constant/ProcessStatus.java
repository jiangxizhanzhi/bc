package com.cyberpeace.situation.constant;

import lombok.Getter;

/**
 * 场景进程状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum ProcessStatus {

	/**
	 * 开始
	 */
	START(1, "开始"),

	/**
	 * 进行中
	 */
	IN_PROGRESS(2, "进行中"),

	/**
	 * 暂停
	 */
	PAUSE(3, "暂停"),

	/**
	 * 已结束
	 */
	OVER(4, "已结束"),

	/**
	 * 异常
	 */
	ABNORMAL(5, "异常");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	ProcessStatus(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
