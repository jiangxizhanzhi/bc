package com.cyberpeace.common.mdparser.convert;


/**
 * 评判 CONVERT
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum JudgeConvert implements MdConvertEnum<String, String> {

	/**
	 * 正确
	 *
	 * @see JudgeConvert
	 */
	CORRECT("A", "正确"),
	/**
	 * 错误
	 *
	 * @see JudgeConvert
	 */
	ERROR("B", "错误");

	/**
	 * 答案
	 *
	 * @see String
	 */
	private String answer;

	/**
	 * 描述
	 *
	 * @see String
	 */
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
