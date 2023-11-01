package com.cyberpeace.situation.constant;

import java.util.List;

import com.cyberpeace.common.util.MathUtil;
import com.google.common.collect.Lists;

import lombok.Getter;

public enum LineEffect {

	/**
	 * 特效
	 */

	STREAM("stream", "流动"),
	SHINE("shine", "发光");

	@Getter
	private String code;

	@Getter
	private String desc;

	LineEffect(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static List<String> getRandomEffect(LineEffect[] values) {

		List<String> effect = Lists.newArrayList();

		Integer n = MathUtil.getRandomInt(values.length + 1);

		for (int i = 1; i <= n; i++) {
			effect.add(values[i - 1].code);
		}

		return effect;

	}

}
