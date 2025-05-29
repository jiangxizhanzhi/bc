package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 战法攻击类别Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TacticAttackCategoryBo` 的 Java 类，位于包 `com.cyberpeace.space.service.context.tactic` 下。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * 该类用于表示“战法攻击类别”的业务对象（BO，Business Object），主要用于封装与战法攻击类别相关的数据。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成了字段的 getter 和 setter 方法，简化了代码量。 
 * 3. **字段说明**： 
 * - `resourceId`：表示资源 ID，类型为字符串。 
 * - `name`：表示名称，类型为字符串。 
 * 4. **文档注释**： 
 * - 类上提供了详细的 Javadoc 文档，描述了类的作用、作者信息、版本信息以及日期等。 
 * - 每个字段也有对应的 Javadoc 注释，说明了字段的用途及其类型。 
 * 5. **设计特点**： 
 * - 这是一个简单的 POJO（Plain Old Java Object）类，仅包含数据字段和自动生成的访问器方法，适合用于数据传输或存储场景。 
 * 总结：这是一个轻量级的数据传输对象（DTO），用于存储和传递战法攻击类别的基本信息，结构清晰且易于扩展。 
**/
@Getter
@Setter
public class TacticAttackCategoryBo {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;
}

