package com.cyberpeace.common.mdparser.convert;

/**
 * 问题类型转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum QuestionTypeConvert implements MdConvertEnum<Integer, String> {

	JUDGE(0, "判断"), SINGLE_CHOICE(1, "单选"), MULTIPLE_CHOICE(2, "多选"), BLANK(3, "填空"), FAQ(4,
			"问答"), ATTACK(5, "攻击"), DEFENCE(6, "防御"), FILE(7, "文件");

	private Integer taskType;

	private String desc;

	/**
	 * 问题类型转换
	 *
	 * @param taskType 任务类型
	 * @param desc     desc
	 */
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
