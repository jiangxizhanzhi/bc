package com.cyberpeace.integratation.model.evaluation;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 标记项目数据
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MarkItemData` 的 Java 类，该类位于包 `com.cyberpeace.integratation.model.evaluation` 中。以下是对此代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.integratation.model.evaluation; 
 * ``` 
 * - 这行代码指定了该类所在的包名。 
 * ### 导入语句 
 * ```java 
 * import java.io.Serializable; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `Serializable`：这是一个 Java 接口，用于实现对象的序列化功能。 
 * - `@Getter` 和 `@Setter`：这两个注解来自 Lombok 库，用于自动生成属性的 getter 和 setter 方法。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * public class MarkItemData implements Serializable { 
 * ``` 
 * - `MarkItemData` 类实现了 `Serializable` 接口，这意味着它可以被序列化。 
 * - `@Getter` 和 `@Setter` 注解会为类中的所有字段生成 getter 和 setter 方法。 
 * ### 字段定义 
 * ```java 
 * private Long t; 
 * private String v; 
 * private Integer m; 
 * ``` 
 * - `t`：一个 `Long` 类型的字段，注释为“t”。 
 * - `v`：一个 `String` 类型的字段，注释为“v”。 
 * - `m`：一个 `Integer` 类型的字段，注释为“m”。 
 * ### 总结 
 * - `MarkItemData` 是一个简单的数据传输对象（DTO），包含三个字段：`t`、`v` 和 `m`。 
 * - 这些字段分别表示不同类型的数据：`t` 是长整型，`v` 是字符串类型，`m` 是整型。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解可以简化代码，避免手动编写 getter 和 setter 方法。 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化，这在需要将对象保存到磁盘或通过网络传输时非常有用。 
**/
@Getter
@Setter
public class MarkItemData implements Serializable {

	/**
	 * t
	 */
	private Long t;

	/**
	 * v
	 */
	private String v;

	/**
	 * m
	 */
	private Integer m;
}

