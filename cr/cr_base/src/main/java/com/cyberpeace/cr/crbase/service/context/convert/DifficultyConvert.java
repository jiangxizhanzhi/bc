package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管难度转换
 *
 * @author SN
 * @date 2024/11/07
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	PRIMARY(1, "低"), INTERMEDIATE(2, "中"), ADVANCED(3, "高");;

	/**
	 * 难度转换
	 *
	 * @param difficulty 难度
	 * @param desc       描述
	 */
	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	private Integer difficulty;

	private String desc;

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return difficulty;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return desc;
	}
}
