package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import com.cyberpeace.constant.BaseI18n;
import lombok.Getter;

/**
 * 课堂岗位
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @see BaseI18n
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ClassroomRole` 的枚举类，用于表示课堂中的不同角色。以下是对该代码的简要分析： 
 * ### 类定义与继承 
 * - `ClassroomRole` 实现了两个接口：`GenericEnum` 和 `BaseI18n`。 
 * - 使用了 Lombok 的 `@Getter` 注解来自动生成属性的 getter 方法。 
 * ### 枚举常量 
 * - 定义了三个枚举常量： 
 * - `TEACHER`：代表教员，代码为 0，中文名称为“教员”，英文名称为 “teacher”。 
 * - `STUDENT`：代表学员，代码为 1，中文名称为“学员”，英文名称为 “student”。 
 * - `CLASSROOM`：代表课堂，代码为 2，中文名称为“课堂”，英文名称为 “classroom”。 
 * ### 属性 
 * - 每个枚举常量都有三个属性： 
 * - `code`：表示角色的唯一标识码（整数）。 
 * - `chinese`：表示角色的中文名称（字符串）。 
 * - `english`：表示角色的英文名称（字符串）。 
 * ### 构造方法 
 * - 构造方法 `ClassroomRole(int code, String chinese, String english)` 用于初始化每个枚举常量的属性。 
 * ### 方法实现 
 * - 实现了 `BaseI18n` 接口中的 `getDesc()` 方法，该方法返回 `getMsg()` 方法的结果。从代码来看，`getMsg()` 方法并未在当前类中定义，因此可以推测 `getMsg()` 应该是在父接口或基类中定义的方法。 
 * ### 总结 
 * - `ClassroomRole` 枚举类用于定义课堂中的不同角色，并提供了国际化支持（通过 `BaseI18n` 接口）。 
 * - 每个枚举常量都有代码、中文名称和英文名称，便于在多语言环境中使用。 
 * - 通过实现 `GenericEnum` 接口，可能还支持一些通用的枚举操作功能。 
 * 这段代码简洁明了，通过枚举类型和 Lombok 注解提高了代码的可读性和维护性。 
**/
public enum ClassroomRole implements GenericEnum, BaseI18n {

	/**
	 * 课堂角色枚举
	 *
	 * @see ClassroomRole
	 */
	TEACHER(0, "教员", "teacher"),
	/**
	 * 学员
	 *
	 * @see ClassroomRole
	 */
	STUDENT(1, "学员", "student"),
	/**
	 * 课堂
	 *
	 * @see ClassroomRole
	 */
	CLASSROOM(2, "课堂", "classroom");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 中文
	 *
	 * @see String
	 */
	@Getter
	private String chinese;

	/**
	 * 英语
	 *
	 * @see String
	 */
	@Getter
	private String english;

	ClassroomRole(int code, String chinese, String english) {
		this.code = code;
		this.chinese = chinese;
		this.english = english;
	}

	@Override
	public String getDesc() {
		return getMsg();
	}
}

