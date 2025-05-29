package com.cyberpeace.space.service.context.tactic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 攻击列表 bo
 *
 * @author SN
 * @date 2024/11/07
 */
/**
 * 这段代码定义了一个名为 `AttackListBo` 的 Java Bean 类，用于表示攻击列表的业务对象（BO, Business Object）。以下是对其的简要分析： 
 * ### 功能概述 
 * - **类名**：`AttackListBo`，表示与攻击列表相关的业务对象。 
 * - **用途**：该类主要用于存储和传输攻击相关信息。 
 * ### 主要特性 
 * 1. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用 Swagger 的 `@ApiModelProperty` 注解为每个字段提供元数据说明，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - 包含多个字段，如 `resourceId`, `name`, `tacticId`, `categoryId`, `category`, `description`, `scene`, `feature`, `systemId`, `system`, `source`, `actionCount`, `toolCount`, `builtin`。 
 * - 每个字段都有对应的注释描述其含义。 
 * 3. **数据类型**： 
 * - 字段类型多样，包括 `String`, `Integer`, 和 `Boolean`。 
 * 4. **设计风格**： 
 * - 遵循良好的注释习惯，每个字段都有详细的描述。 
 * - 使用了现代 Java 编程技术（如 Lombok 和 Swagger 注解）来简化代码并提高可读性。 
 * ### 总结 
 * 此代码是一个典型的 Java Bean 类，适合在后端服务中作为数据传输对象（DTO, Data Transfer Object）或业务对象（BO）使用。它通过注解增强了代码的功能性和可维护性，同时提供了清晰的文档说明。 
**/
@Getter
@Setter
public class AttackListBo {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "资源id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "类别id")
	private String categoryId;

	/**
	 * 类别
	 */
	@ApiModelProperty(value = "类别")
	private String category;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 场景
	 */
	@ApiModelProperty(value = "")
	private String scene;

	/**
	 * 特征
	 */
	@ApiModelProperty(value = "")
	private String feature;

	/**
	 * 技战法体系id
	 */
	@ApiModelProperty(value = "技战法体系id")
	private String systemId;

	/**
	 * 技战法体系
	 */
	@ApiModelProperty(value = "技战法体系")
	private String system;

	/**
	 * 战法来源
	 */
	@ApiModelProperty(value = "战法来源")
	private String source;

	/**
	 * 行动计数
	 */
	@ApiModelProperty(value = "行动计数")
	private Integer actionCount;

	/**
	 * 工具计数
	 */
	@ApiModelProperty(value = "工具计数")
	private Integer toolCount;
	/**
	 * 内置
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否内置")
	private Boolean builtin;
}

