package com.cyberpeace.common.mdparser.convert;

/**
 * @author chenzhuo
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	/** 难度值*/
	PRIMARY(0, "初级"), INTERMEDIATE(1, "中级"), ADVANCED(2, "高级");

	private Integer difficulty;

	private String desc;

	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	@Override
	public Integer to() {
		return difficulty;
	}

	@Override
	public String reTo() {
		return desc;
	}


}
