package com.cyberpeace.situation.constant.topo;

import lombok.Getter;

/**
 * 动效类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DynamicType {

	/**
	 * 预置
	 */
	PRESET(1, "预置"),

	/**
	 * 用户上传
	 */
	USER_UPLOAD(2, "用户上传");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	DynamicType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 根据编号查询描述
	 *
	 * @param code 编号
	 * @return {@code String}
	 */
	public static String getDescByCode(Integer code) {
		for (DynamicType dynamicType : DynamicType.values()) {
			if (dynamicType.getCode().equals(code)) {
				return dynamicType.getDesc();
			}
		}
		return null;
	}

}
