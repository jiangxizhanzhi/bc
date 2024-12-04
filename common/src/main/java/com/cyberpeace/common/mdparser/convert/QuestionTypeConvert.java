package com.cyberpeace.common.mdparser.convert;


/**
 * 问题类型转换
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see MdConvertEnum
 * @since 1.0.0
 */
public enum QuestionTypeConvert implements MdConvertEnum<Integer, String> {

	/**
	 * 法官
	 *
	 * @see QuestionTypeConvert
	 */
	JUDGE(0, "判断"),
	/**
	 * 单选
	 *
	 * @see QuestionTypeConvert
	 */
	SINGLE_CHOICE(1, "单选"),
	/**
	 * 多项选择
	 *
	 * @see QuestionTypeConvert
	 */
	MULTIPLE_CHOICE(2, "多选"),
	/**
	 * 空白
	 *
	 * @see QuestionTypeConvert
	 */
	BLANK(3, "填空"),
	/**
	 * 常见问题
	 *
	 * @see QuestionTypeConvert
	 */
	FAQ(4,
			"问答"),
	/**
	 * 攻击
	 *
	 * @see QuestionTypeConvert
	 */
	ATTACK(5, "攻击"),
	/**
	 * 防御
	 *
	 * @see QuestionTypeConvert
	 */
	DEFENCE(6, "防御"),
	/**
	 * 文件
	 *
	 * @see QuestionTypeConvert
	 */
	FILE(7, "文件");

	/**
	 * 任务类型
	 *
	 * @see Integer
	 */
	private Integer taskType;

	/**
	 * 描述
	 *
	 * @see String
	 */
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
