package com.cyberpeace.common.mdparser.convert;

/**
 * 课类型转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum LessonTypeConvert implements MdConvertEnum<String, Integer> {

	/** 课时类型*/
	THEORY("理论课", 0), OPERATION("实验课", 1);

	private String name;

	private Integer type;

	@Override
	public String to() {
		return name;
	}

	@Override
	public Integer reTo() {
		return type;
	}

	/**
	 * 课类型转换
	 *
	 * @param name 名字
	 * @param type 类型
	 */
	LessonTypeConvert(String name, Integer type) {
		this.name = name;
		this.type = type;
	}
}
