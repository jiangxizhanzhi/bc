package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管难度转换
 *
 * @author SN
 * @date 2024/11/08
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	/**
	 * 主要
	 */
	PRIMARY(1, "低"),
	/**
	 * 中间
	 */
	INTERMEDIATE(2, "中"),
	/**
	 * 高深
	 */
	ADVANCED(3, "高");;

	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 描述
	 */
	private String desc;

	@Override
	public Integer to() {
		return difficulty;
	}

	@Override
	public String reTo() {
		return desc;
	}
}
