/**
 * 这段代码定义了一个名为 `SpaceActionStrategy` 的 Java 类，用于表示“太空行动策略”。以下是对其的简要分析： 
 * 1. **类的功能**： 
 * - 该类是 `com.cyberpeace.space.service.context.frame` 包的一部分，主要用于描述与“太空行动”相关的策略。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了元数据说明，便于 API 文档生成。 
 * 3. **字段说明**： 
 * - `resourceId`：标识资源 ID，前端在新增或修改时需要传递此值。 
 * - `name`：策略的名称。 
 * - `route`：策略的归属。 
 * - `content`：执行脚本的内容。 
 * 4. **设计目的**： 
 * - 该类是一个数据传输对象（DTO），用于在系统中传递与“太空行动策略”相关的信息。 
 * 总结：这是一个简单的 DTO 类，通过注解增强了代码的可读性和可维护性，适用于 API 数据交互场景。 
**/
package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 太空行动策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceActionStrategy {
	@ApiModelProperty(value = "新增、修改，前端只用传id")
	private String resourceId;

	@ApiModelProperty(value = "名称")
	private String name;

	@ApiModelProperty(value = "归属")
	private String route;

	@ApiModelProperty(value = "执行脚本")
	private String content;
}
