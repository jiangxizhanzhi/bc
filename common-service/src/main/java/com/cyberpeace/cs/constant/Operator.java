/**
 * 该代码定义了一个枚举类 `Operator`，实现了接口 `GenericEnum`。以下是简要分析： 
 * 1. **功能**： 
 * - 枚举类 `Operator` 表示操作对象，包含一个操作对象“公元前”。 
 * 2. **字段**： 
 * - `code`：操作对象的代号，使用 `@Getter` 注解表明可以通过 getter 方法访问。 
 * - `desc`：操作对象的描述，同样使用 `@Getter` 注解。 
 * 3. **构造方法**： 
 * - 私有构造方法 `Operator(Integer code, String desc)` 用于初始化 `code` 和 `desc` 字段。 
 * 4. **实现接口**： 
 * - 实现了 `GenericEnum` 接口，具体实现细节未在代码中展示，但通常用于提供通用的枚举功能。 
 * 5. **枚举常量**： 
 * - 定义了一个枚举常量 `BC`，表示“公元前”，其值为 `(1, "bc")`。 
 * 总结：这是一个简单的枚举类，用于表示特定的操作对象，并提供了代码和描述的访问方法。 
**/
package com.cyberpeace.cs.constant;

import com.cyberpeace.common.baseinterface.GenericEnum;

import lombok.Getter;

/**
 * 操作对象
 *
 * @author SN
 * @date 2024/11/08
 */
public enum Operator implements GenericEnum {

	/**
	 * 公元前
	 */
	BC(1, "bc");

	/**
	 * 代号
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	Operator(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
