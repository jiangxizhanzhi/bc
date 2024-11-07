package com.cyberpeace.common.mdparser.convert;


/**
 * 问题类型转换
 *
 * @author SN
 * @date 2024/11/07
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
	 * @param desc     描述
	 */
	QuestionTypeConvert(Integer taskType, String desc) {
		this.taskType = taskType;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return taskType;
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
