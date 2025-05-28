/**
 * 这段代码定义了一个名为 `DefenseCategoryBo` 的 Java 类，属于 `com.cyberpeace.space.service.context.tactic` 包。以下是代码的主要分析： 
 * 1. **注解与功能**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化了代码书写。 
 * 2. **类的用途**： 
 * - 这是一个业务对象（BO, Business Object），通常用于封装数据。 
 * - 注释表明该类是用于国防相关的业务逻辑。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，表示某个资源的唯一标识。 
 * - `stage`：阶段，可能表示某种流程或步骤。 
 * - `cover`：盖，具体含义需结合上下文理解，可能是某种覆盖物或状态。 
 * 总结：这是一个简单的数据传输对象（DTO），主要用于存储与国防相关的基本信息。 
**/
package com.cyberpeace.space.service.context.tactic;

import lombok.Getter;
import lombok.Setter;

/**
 * 国防类 bo
 *
 * @author SN
 * @date 2024/11/07
 */
@Getter
@Setter
public class DefenseCategoryBo {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 阶段
	 */
	private Integer stage;

	/**
	 * 盖
	 */
	private String cover;
}
