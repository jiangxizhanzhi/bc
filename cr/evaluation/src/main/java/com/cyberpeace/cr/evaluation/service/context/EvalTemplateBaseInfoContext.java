/**
 * 这段代码定义了一个名为 `EvalTemplateBaseInfoContext` 的 Java 类，位于包 `com.cyberpeace.cr.evaluation.service.context` 中。该类主要用于存储评估模板的基础信息上下文。以下是详细的分析和总结： 
 * ### 类定义 
 * - **包声明**：`package com.cyberpeace.cr.evaluation.service.context;` 
 * - 表示该类属于 `com.cyberpeace.cr.evaluation.service.context` 包。 
 * - **导入语句**： 
 * ```java 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - 导入了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，用于自动生成属性的 getter 和 setter 方法。 
 * ### 类注释 
 * ```java 
 * /** 
 * * eval 模板 base info 上下文 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * */ 
 * ``` 
 * - 这段注释说明了类的功能（评估模板基础信息上下文）以及作者和日期。 
 * ### 类属性 
 * - **资源 ID** 
 * ```java 
 * private String resourceId; 
 * ``` 
 * - 存储资源的唯一标识符。 
 * - **名称** 
 * ```java 
 * private String name; 
 * ``` 
 * - 存储资源的名称。 
 * - **描述** 
 * ```java 
 * private String description; 
 * ``` 
 * - 存储资源的描述信息。 
 * - **图标 ID** 
 * ```java 
 * private String logoId; 
 * ``` 
 * - 存储与资源关联的图标标识符。 
 * - **类别代号** 
 * ```java 
 * private Integer categoryCode; 
 * ``` 
 * - 存储资源所属的类别代号。 
 * ### Getter 和 Setter 方法 
 * - 通过 `@Getter` 和 `@Setter` 注解，Lombok 自动生成了所有属性的 getter 和 setter 方法。 
 * ### 总结 
 * - **功能**：该类主要用于封装评估模板的基础信息，包括资源 ID、名称、描述、图标 ID 和类别代号。 
 * - **特点**：使用 Lombok 来简化代码，自动提供 getter 和 setter 方法。 
 * - **用途**：作为数据传输对象（DTO），在不同模块或层之间传递评估模板的基本信息。 
**/
package com.cyberpeace.cr.evaluation.service.context;

import lombok.Getter;
import lombok.Setter;

/**
 * eval 模板 base info 上下文
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class EvalTemplateBaseInfoContext {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 图标id
	 */
	private String logoId;

	/**
	 * 类别代号
	 */
	private Integer categoryCode;

}
