package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景共享
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum SceneShare implements GenericEnum {

	/**
	 * 私人
	 *
	 * @see SceneShare
	 */
	PRIVATE(1, "个人"),
	/**
	 * 共享
	 *
	 * @see SceneShare
	 */
	SHARED(2, "共享"),
	/**
	 * 群
	 *
	 * @see SceneShare
	 */
	GROUP(3, "小组");

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

	SceneShare(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
