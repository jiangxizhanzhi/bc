package com.cyberpeace.common.mdparser.convert;


public enum JudgeConvert implements MdConvertEnum<String, String> {

	CORRECT("A", "正确"), ERROR("B", "错误");

	private String answer;

	private String desc;

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
