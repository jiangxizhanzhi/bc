package com.cyberpeace.situation.constant.topo;

import lombok.Getter;

/**
 * 区域形状
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum AreaShape {

	/**
	 * 矩形
	 */
	RECTANGLE("rectangle", "矩形");

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

	AreaShape(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
