/**
 * 这段代码定义了一个枚举类 `QuestionTypeConvert`，用于表示不同类型的问题，并提供了将问题类型从一种形式转换为另一种形式的功能。以下是代码的主要分析： 
 * 1. **功能概述**： 
 * - 该枚举类实现了接口 `MdConvertEnum<Integer, String>`，提供了一种通用的方式来处理问题类型的转换。 
 * - 每个枚举值代表一种问题类型，包含一个整数值（`taskType`）和对应的描述字符串（`desc`）。 
 * 2. **枚举值**： 
 * - 枚举类中定义了多种问题类型，如法官、单选、多选、填空、问答、攻击、防御和文件等。 
 * - 每个枚举值都有一个唯一的整数值和对应的描述字符串。 
 * 3. **构造方法**： 
 * - 提供了一个私有的构造方法，用于初始化每个枚举值的 `taskType` 和 `desc` 属性。 
 * 4. **接口实现**： 
 * - 实现了两个方法： 
 * - `to()`：返回问题类型的整数值（`taskType`）。 
 * - `reTo()`：返回问题类型的描述字符串（`desc`）。 
 * 5. **用途**： 
 * - 该枚举类可以用于在不同上下文中统一管理问题类型的转换，例如在前端显示问题类型描述或在后端处理问题类型时使用。 
 * 总结来说，这段代码通过枚举类的方式封装了问题类型的定义及其转换逻辑，使得代码更加清晰和易于维护。 
**/
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
