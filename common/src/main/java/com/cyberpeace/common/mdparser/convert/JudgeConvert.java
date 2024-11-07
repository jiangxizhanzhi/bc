package com.cyberpeace.common.mdparser.convert;


/**
 * 评判 CONVERT
 *
 * @author SN
 * @date 2024/11/07
 */
public enum JudgeConvert implements MdConvertEnum<String, String> {

	CORRECT("A", "正确"), ERROR("B", "错误");

	private String answer;

	private String desc;

	/**
	 * 评判 CONVERT
	 *
	 * @param answer 答案
	 * @param desc   描述
	 */
	JudgeConvert(String answer, String desc) {
		this.answer = answer;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code String }
	 */
	@Override
	public String to() {
		return answer;
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
