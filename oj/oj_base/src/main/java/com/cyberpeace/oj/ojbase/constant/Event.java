package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 事件
 *
 * @author SN
 * @date 2024/11/14
 */
public enum Event implements GenericEnum {

	/**
	 * 场景更新
	 */
	SCENE_UPDATE(1, "场景启动中 状态持续更新");

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

	Event(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
