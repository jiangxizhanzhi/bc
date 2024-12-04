package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 难度转换
 *
 * @author chenzhuo@date 2024/11/08@see Enum
 * @version 1.0.0
 * @date 2024/12/04
 * @see MdConvertEnum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String>, GenericEnum {

	/**
	 * 难度值
	 *
	 * @see DifficultyConvert
	 */
	PRIMARY(0, "初级"),
	/**
	 * 中间
	 *
	 * @see DifficultyConvert
	 */
	INTERMEDIATE(1, "中级"),
	/**
	 * 高深
	 *
	 * @see DifficultyConvert
	 */
	ADVANCED(2, "高级");

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


	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCode() {
		return difficulty;
	}

	/**
	 * 获取描述
	 *
	 * @return {@code String }
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}
