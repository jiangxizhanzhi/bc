/**
 * 这段代码定义了一个名为 `Event` 的枚举类，该类实现了 `GenericEnum` 接口。以下是对其简要分析： 
 * ### 类定义与继承 
 * - **类名**：`Event` 
 * - **实现接口**：`GenericEnum` 
 * - **包名**：`com.cyberpeace.oj.ojbase.constant` 
 * ### 枚举实例 
 * - 枚举类中只有一个实例 `SCENE_UPDATE`，代表“场景更新”。 
 * - 实例属性包括： 
 * - `code`：表示场景更新的代号（整数类型）。 
 * - `desc`：描述场景更新的状态（字符串类型）。 
 * ### 属性与注解 
 * - 使用了 Lombok 的 `@Getter` 注解来自动生成 `code` 和 `desc` 属性的 getter 方法。 
 * - 枚举实例通过构造函数初始化其属性值。 
 * ### 构造函数 
 * - 定义了一个私有构造函数，接受两个参数：`code` 和 `desc`。用于初始化枚举实例的属性。 
 * ### 文档注释 
 * - 类级别的文档说明了这个枚举类的作用及版本信息。 
 * - 每个枚举实例都有详细的 Javadoc 注释，描述了其用途和含义。 
 * ### 总结 
 * - 这段代码定义了一个简单的枚举类 `Event`，用于表示某种特定类型的事件（如场景更新）。 
 * - 枚举实例通过构造函数初始化，并且使用了 Lombok 的 `@Getter` 注解来简化代码。 
 * - 代码具有良好的可读性和维护性，适合在需要表示特定事件类型的场景中使用。 
**/
package com.cyberpeace.oj.ojbase.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;
import lombok.Getter;

/**
 * 事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Enum
 * @see GenericEnum
 * @since 1.0.0
 */
public enum Event implements GenericEnum {

	/**
	 * 场景更新
	 *
	 * @see Event
	 */
	SCENE_UPDATE(1, "场景启动中 状态持续更新");

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

	Event(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}
