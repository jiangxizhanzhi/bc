package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 场景共享
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SceneShare` 的枚举类，用于表示不同类型的场景共享。下面是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.ojbase.constant` 
 * - **导入**： 
 * - `com.cyberpeace.common.baseinterface.GenericEnum`：接口，可能用于定义枚举的行为。 
 * - `lombok.Getter`：Lombok 注解，用于自动生成 getter 方法。 
 * ### 枚举定义 
 * - **继承**：`SceneShare` 实现了 `GenericEnum` 接口。 
 * - **枚举常量**： 
 * - `PRIVATE`：代码为 1，描述为“个人”。 
 * - `SHARED`：代码为 2，描述为“共享”。 
 * - `GROUP`：代码为 3，描述为“小组”。 
 * ### 字段 
 * - `code`：整数类型，表示枚举值的代码。 
 * - `desc`：字符串类型，表示枚举值的描述。 
 * ### 构造方法 
 * - `SceneShare(Integer code, String desc)`：私有构造方法，用于初始化 `code` 和 `desc` 字段。 
 * ### Getter 方法 
 * - 使用 Lombok 的 `@Getter` 注解，自动生成了 `code` 和 `desc` 字段的 getter 方法。 
 * ### 总结 
 * - `SceneShare` 是一个枚举类，用于表示不同类型的场景共享（私人、共享、小组）。 
 * - 每个枚举常量都有一个唯一的代码和描述。 
 * - 通过实现 `GenericEnum` 接口，`SceneShare` 可能具有某些通用行为。 
 * - 使用 Lombok 的 `@Getter` 注解简化了代码，自动生成了字段的 getter 方法。 
 * 这个枚举类可以用于在应用程序中表示和处理不同类型的场景共享，提供了一种方便且类型安全的方式来管理这些共享类型。 
**/
public enum SceneShare implements GenericEnum {

	/**
	 * 私人
	 *
	 * @see SceneShare
	 */
	PRIVATE(1, "个人"),
	/**
	 * 共享
	 *
	 * @see SceneShare
	 */
	SHARED(2, "共享"),
	/**
	 * 群
	 *
	 * @see SceneShare
	 */
	GROUP(3, "小组");

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

	SceneShare(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}

