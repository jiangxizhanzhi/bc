package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管难度转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	/**
	 * 低
	 *
	 * @see DifficultyConvert
	 */
	PRIMARY(1, "低"),
	/**
	 * 中
	 *
	 * @see DifficultyConvert
	 */
	INTERMEDIATE(2, "中"),
	/**
	 * 高
	 *
	 * @see DifficultyConvert
	 */
	ADVANCED(3, "高");;

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

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 描述
	 *
	 * @see String
	 */
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
