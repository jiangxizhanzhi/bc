package com.cyberpeace.common.mdparser.convert;

/**
 * 难度转换
 *
 * @author chenzhuo
 * @date 2023/05/06
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	/**
	 * 难度值
	 */
	PRIMARY(0, "初级"), INTERMEDIATE(1, "中级"), ADVANCED(2, "高级");

	private Integer difficulty;

	private String desc;

	/**
	 * 难度转换
	 *
	 * @param desc       desc
	 * @param difficulty 困难
	 */
	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	/**
	 * 获取code
	 *
	 * @return {@link Integer}
	 */
	@Override
	public Integer to() {
		return difficulty;
	}

	/**
	 * 获取描述
	 *
	 * @return {@link String}
	 */
	@Override
	public String reTo() {
		return desc;
	}

}
