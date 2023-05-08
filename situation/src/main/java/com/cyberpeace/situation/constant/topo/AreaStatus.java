package com.cyberpeace.situation.constant.topo;

import lombok.Getter;

/**
 * 区域状态
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum AreaStatus {

	/**
	 * 正常
	 */
	NORMAL("normal", "正常");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	AreaStatus(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
