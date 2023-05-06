package com.cyberpeace.common.mdparser.convert;

/**
 * 法官把
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum JudgeConvert implements MdConvertEnum<String, String> {

	CORRECT("A", "正确"), ERROR("B", "错误");

	private String answer;

	private String desc;

	/**
	 * 判断转换
	 *
	 * @param answer 回答
	 * @param desc   desc
	 */
	JudgeConvert(String answer, String desc) {
		this.answer = answer;
		this.desc = desc;
	}

	@Override
	public String to() {
		return answer;
	}

	@Override
	public String reTo() {
		return desc;
	}
}
