/**
 * 这段代码定义了一个名为 `SpaceActionTool` 的 Java 类，位于包 `com.cyberpeace.space.service.context.frame` 中。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - `SpaceActionTool` 是一个工具类，用于支持与空间相关的操作（如新增或修改）。 
 * - 它包含了一些字段，这些字段通常用于描述某个资源的相关信息。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化了代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性信息，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `resourceId`：表示资源 ID，用于标识特定资源，前端在新增或修改时需要传递该值。 
 * - `name`：资源的名称。 
 * - `introduction`：资源的简短描述。 
 * - `platforms`：资源所属的平台。 
 * 4. **设计意图**： 
 * - 该类旨在封装与空间操作相关的信息，便于在前后端交互中传递数据。 
 * 总结来说，这是一个简单的工具类，主要用于封装与空间操作相关的数据结构，并通过注解增强了代码的可读性和文档生成能力。 
**/
package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Space Action 工具
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionTool {
	@ApiModelProperty(value = "新增、修改，前端只用传id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "简述")
	private String introduction;

	@ApiModelProperty(value = "平台")
	private String platforms;
}
