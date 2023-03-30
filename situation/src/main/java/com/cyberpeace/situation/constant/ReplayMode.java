package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum ReplayMode {

	/**
	 * 重演模式
	 */

	NONE(0, "非重演"),
	CONTRAST(1, "对比重演"),
	SYNCHRONISM(2, "同步重演");

	@Getter
	private Integer code;

	@Getter
	private String name;

	ReplayMode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<IntType> getList() {
		return StreamApiUtils.map(Lists.newArrayList(ReplayMode.values()),
				mode -> new IntType(mode.getCode(), mode.getName()));
	}

}
