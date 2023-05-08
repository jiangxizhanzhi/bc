package com.cyberpeace.situation.constant.dashboard;

import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 重演模式
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum ReplayMode {

	/**
	 * 非重演
	 */
	NONE(0, "非重演"),

	/**
	 * 对比重演
	 */
	CONTRAST(1, "对比重演"),

	/**
	 * 同步重演
	 */
	SYNCHRONISM(2, "同步重演");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	ReplayMode(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 获取重演模式列表
	 *
	 * @return {@code List<IntType>}
	 */
	public static List<IntType> getList() {
		return StreamApiUtils.map(Lists.newArrayList(ReplayMode.values()),
				mode -> new IntType(mode.getCode(), mode.getName()));
	}

}
