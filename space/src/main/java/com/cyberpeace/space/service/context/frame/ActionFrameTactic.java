package com.cyberpeace.space.service.context.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 行动框架战法
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ActionFrameTactic` 的 Java 类，位于包 `com.cyberpeace.space.service.context.frame` 中。以下是代码的简要分析： 
 * 1. **类功能**： 
 * - 该类表示“行动框架战法”，用于描述与战法相关的属性和状态。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为某些字段添加了描述性注释（如 `type` 和 `status`）。 
 * 3. **字段说明**： 
 * - `resourceId`：资源 ID，唯一标识某个资源。 
 * - `name`：战法名称。 
 * - `type`：战法类型，通过整数值表示（1 表示攻击，2 表示防御）。 
 * - `status`：战法的状态，默认值为 0（未下发），其他可能值为 1（已完成）或 2（已下发未完成）。 
 * 4. **设计目的**： 
 * - 该类主要用于封装战法的相关信息，便于在系统中传递和管理战法的状态和属性。 
 * 总结：这是一个简单的 POJO（Plain Old Java Object）类，用于描述战法的基本信息及其状态，支持序列化和文档生成。 
**/
@Getter
@Setter
public class ActionFrameTactic {

	private String resourceId;

	private String name;

    @ApiModelProperty(value = "战法类型，1攻击2防御")
	private Integer type;

	@ApiModelProperty(value = "状态，0未下发，1已完成，2已下发未完成")
	private Integer status = 0;
}

