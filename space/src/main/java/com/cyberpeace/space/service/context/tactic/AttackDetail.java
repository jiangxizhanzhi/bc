package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.cr.tool.service.context.TacticTool;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 攻击详情
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AttackDetail` 的 Java 类，用于表示攻击详情的信息模型。以下是代码的主要分析和总结： 
 * ### 主要功能 
 * 该类封装了与攻击相关的详细信息，包括资源 ID、名称、战法 ID、类别信息、角色、标签、描述、技术场景、拓扑特征、技战法体系 ID、技战法体系、战法来源以及关联的动作、工具和策略。 
 * ### 关键特性 
 * 1. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了元数据说明，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - `resourceId`：资源 ID。 
 * - `name`：名称。 
 * - `tacticId`：战法 ID。 
 * - `categoryId`：类别 ID。 
 * - `categoryName`：类别名称。 
 * - `role`：角色，类型为整数。 
 * - `tag`：标签，类型为字符串列表。 
 * - `description`：描述。 
 * - `scene`：技术场景。 
 * - `feature`：拓扑特征。 
 * - `systemId`：技战法体系 ID。 
 * - `system`：技战法体系。 
 * - `source`：战法来源。 
 * - `action`：动作，类型为 `TacticAction` 列表。 
 * - `tool`：工具，类型为 `TacticTool` 列表。 
 * - `strategy`：策略，类型为 `SpaceActionStrategy` 列表。 
 * 3. **依赖类**： 
 * - `TacticAction`、`TacticTool` 和 `SpaceActionStrategy` 是外部依赖类，分别表示动作、工具和策略。 
 * ### 总结 
 * 该类是一个典型的 DTO（Data Transfer Object）类，主要用于在系统中传递和存储攻击相关的详细信息。通过注解的使用，增强了代码的可读性和可维护性，同时方便与其他框架（如 Swagger）集成以生成 API 文档。 
**/
@Getter
@Setter
public class AttackDetail {

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

	/**
	 * 战法id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "ID")
	private String tacticId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "类别")
	private String categoryId;

	/**
	 * 类别名称
	 *
	 * @see String
	 */
	private String categoryName;

	@ApiModelProperty(value = "岗位，常量type=45")
	private Integer role;

	@ApiModelProperty(value = "标签")
	private List<String> tag;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "技术场景")
	private String scene;

	@ApiModelProperty(value = "拓扑特征")
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


	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	@ApiModelProperty(value = "工具")
	private List<TacticTool> tool;

	@ApiModelProperty(value = "策略")
	private List<SpaceActionStrategy> strategy;
}

