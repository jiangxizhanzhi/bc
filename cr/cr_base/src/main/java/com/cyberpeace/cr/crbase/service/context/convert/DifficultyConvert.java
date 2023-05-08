package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管难度转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum DifficultyConvert implements MdConvertEnum<Integer, String> {

	PRIMARY(1, "低"), INTERMEDIATE(2, "中"), ADVANCED(3, "高");
	;

	DifficultyConvert(Integer difficulty, String desc) {
		this.difficulty = difficulty;
		this.desc = desc;
	}

	private Integer difficulty;

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
