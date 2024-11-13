package com.cyberpeace.common.mdparser.convert;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 难度转换
 *
 * @author chenzhuo@date 2024/11/08
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String>, GenericEnum {

	/** 难度值*/
	PRIMARY(0, "初级"), INTERMEDIATE(1, "中级"), ADVANCED(2, "高级");

	private Integer difficulty;

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
