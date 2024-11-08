package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景共享
 *
 * @author SN
 * @date 2024/11/08
 */
public enum SceneShare implements GenericEnum {

	/**
	 * 私人
	 */
	PRIVATE(1, "个人"),
	/**
	 * 共享
	 */
	SHARED(2, "共享"),
	/**
	 * 群
	 */
	GROUP(3, "小组");

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

	SceneShare(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
