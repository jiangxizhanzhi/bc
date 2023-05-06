package com.cyberpeace.situation.constant;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 链路效果
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum LineEffect {

	/**
	 * 流动
	 */
	STREAM("stream", "流动"),

	/**
	 * 发光
	 */
	SHINE("shine", "发光");

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

	LineEffect(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取随机效果
	 *
	 * @param values 值
	 * @return {@code List<String>}
	 */
	public static List<String> getRandomEffect(LineEffect[] values) {

		List<String> effect = Lists.newArrayList();

		Integer n = (int) (Math.random() * (values.length + 1));

		for (int i = 1; i <= n; i++) {
			effect.add(values[i - 1].code);
		}

		return effect;

	}

}
