package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 难度
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `Difficulty` 的枚举类，用于表示题目难度。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.constant` 
 * - **导入**： 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：表示该枚举类实现了 `GenericEnum` 接口。 
 * - `lombok.Getter`：使用 Lombok 的 `@Getter` 注解来自动生成属性的 getter 方法。 
 * ### 枚举常量 
 * 枚举类中定义了三个常量，分别表示不同的难度等级： 
 * - `PRIMARY`（代码为 `0`，描述为 `"初级"`） 
 * - `MIDDLE`（代码为 `1`，描述为 `"中级"`） 
 * - `HIGH`（代码为 `2`，描述为 `"高级"`） 
 * ### 属性 
 * - `code`：表示难度的代码值（整数）。 
 * - `desc`：表示难度的描述（字符串）。 
 * ### 构造方法 
 * - 枚举类的构造方法接受两个参数：`code` 和 `desc`，并将其赋值给对应的属性。 
 * ### 方法 
 * - `ofCode(Integer code)`：这是一个静态方法，根据传入的代码值返回相应的难度描述。如果传入的代码值不是 `0`、`1` 或 `2`，则返回空字符串。 
 * ### 注释 
 * - 类和字段上有详细的注释，解释了每个部分的功能和用途。 
 * ### 总结 
 * 这段代码定义了一个枚举类 `Difficulty`，用于表示题目的难度等级，并提供了一种通过代码值获取对应难度描述的方法。这个枚举类实现了一个通用接口 `GenericEnum`，并且使用 Lombok 简化了 getter 方法的编写。 
**/
public enum Difficulty implements GenericEnum {

	/**
	 * 主要
	 *
	 * @see Difficulty
	 */
	PRIMARY(0, "初级"),
	/**
	 * 中间
	 *
	 * @see Difficulty
	 */
	MIDDLE(1, "中级"),
	/**
	 * 高
	 *
	 * @see Difficulty
	 */
	HIGH(2, "高级");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	Difficulty(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String ofCode(Integer code) {
		if(code == 0) {
			return PRIMARY.getDesc();
		}
		if(code == 1) {
			return MIDDLE.getDesc();
		}
		if(code == 2) {
			return HIGH.getDesc();
		}
		return "";
	}
}

