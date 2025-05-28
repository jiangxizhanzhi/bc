/**
 * ### 代码分析 
 * #### 类定义 
 * - **类名**：`IndexFrame` 
 * - **包名**：`com.cyberpeace.space.service.context.index` 
 * - **功能描述**：该类是一个编号框架（`IndexFrame`），用于封装与编号相关的数据结构。它包含了两个主要属性，分别表示框架的计数和趋势图。 
 * #### 属性说明 
 * 1. **`frameCount`** 
 * - 数据类型：`IndexFrameCount` 
 * - 注解： 
 * - `@Getter` 和 `@Setter`：表明该属性有自动生成的getter和setter方法。 
 * - `@ApiModelProperty(value = "计数")`：使用Swagger注解，表示该字段在API文档中会显示为“计数”。 
 * - 功能：表示框架中的计数信息，其具体实现类可能是`IndexFrameCount`。 
 * 2. **`trending`** 
 * - 数据类型：`IndexActionFrameChart` 
 * - 注解： 
 * - `@Getter` 和 `@Setter`：同样有自动生成的getter和setter方法。 
 * - `@ApiModelProperty(value = "趋势图")`：在API文档中显示为“趋势图”。 
 * - 功能：表示框架的趋势图信息，可能是一个图表对象，具体实现类可能是`IndexActionFrameChart`。 
 * #### 类注释 
 * - **作者**：SN 
 * - **版本**：1.0.0 
 * - **日期**：2025/05/28 
 * - **依赖**： 
 * - `IndexFrameCount`：用于计数的类。 
 * - `IndexActionFrameChart`：用于趋势图的类。 
 * #### 总结 
 * `IndexFrame` 是一个简单的数据传输对象（DTO），主要用于存储和传递编号框架的相关信息。它通过`@ApiModelProperty`注解增强了API文档的可读性，并通过`Lombok`注解简化了代码的冗余部分。该类的设计目的是为了方便在系统中管理和展示编号相关的计数和趋势信息。 
**/
package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号框架
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexFrame {

	/**
	 * 框架计数
	 *
	 * @see IndexFrameCount
	 */
	@ApiModelProperty(value = "计数")
	private IndexFrameCount frameCount;

	/**
	 * 趋势
	 *
	 * @see IndexActionFrameChart
	 */
	@ApiModelProperty(value = "趋势图")
	private IndexActionFrameChart trending;
}
