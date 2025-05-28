/**
 * 这段代码定义了一个名为 `ActionFrameStep` 的 Java 类，属于 `com.cyberpeace.space.service.context.frame` 包。以下是对其的简要分析： 
 * 1. **类功能**： 
 * - `ActionFrameStep` 是一个用于表示“行动框架步骤”的实体类。 
 * - 它包含与步骤相关的属性，如名称、ID、描述、状态以及关联的战法列表。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了元数据描述，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `name`: 步骤的名称。 
 * - `resourceId`: 步骤的唯一标识 ID。 
 * - `description`: 对步骤的详细描述。 
 * - `status`: 步骤的状态（枚举值：0-未下发，1-已完成，2-已下发未完成），默认值为 0。 
 * - `tactics`: 关联的战法列表，类型为 `List<ActionFrameTactic>`。 
 * 4. **用途**： 
 * - 这个类可能是某种业务逻辑中的“行动框架”模块的一部分，用于描述具体的步骤及其相关属性。 
 * 总结：这是一个简单的实体类，主要用于封装行动框架步骤的相关信息，并支持序列化和文档生成。 
**/
package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 行动框架步骤
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class ActionFrameStep {

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "状态，0未下发，1已完成，2已下发未完成")
	private Integer status = 0;

	@ApiModelProperty(value = "行动战法")
	private List<ActionFrameTactic> tactics;
}
