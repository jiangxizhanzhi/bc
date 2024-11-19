package com.cyberpeace.situation.constant;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 特效
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum LineEffect {

	/**
	 * 流动
	 *
	 * @see LineEffect
	 */
	STREAM("stream", "流动"),
	/**
	 * 发光
	 *
	 * @see LineEffect
	 */
	SHINE("shine", "发光");

	/**
	 * 代号
	 *
	 * @see String
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	LineEffect(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static List<String> getRandomEffect(LineEffect[] values) {

		List<String> effect = Lists.newArrayList();

		Integer n = (int) (Math.random() * (values.length + 1));

		for (int i = 1; i <= n; i++) {
			effect.add(values[i - 1].code);
		}

		return effect;

	}

}
