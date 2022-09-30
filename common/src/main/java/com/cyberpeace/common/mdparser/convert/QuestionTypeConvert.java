package com.cyberpeace.common.mdparser.convert;


public enum QuestionTypeConvert implements MdConvertEnum<Integer, String> {

	JUDGE(0, "判断"), SINGLE_CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
			"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

	private Integer taskType;

	private String desc;

	QuestionTypeConvert(Integer taskType, String desc) {
		this.taskType = taskType;
		this.desc = desc;
	}

	@Override
	public Integer to() {
		return taskType;
	}

	@Override
	public String reTo() {
		return desc;
	}
}
