package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 防御新增请求体
 *
 * @author SN
 * @date 2024/11/07
 */
/**
 * 该代码定义了一个名为 `DefenseAddReq` 的类，用于表示防御新增请求的请求体。以下是对其主要特性和功能的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于接收与防御相关的新增请求数据，包含多个字段以描述防御技战术的相关信息。 
 * 2. **字段说明**： 
 * - `name`: 定义防御对象的名称。 
 * - `categoryId`: 所属战术的类别ID。 
 * - `tool`: 用于描述防御过程中使用的工具列表。 
 * - `source`: 定义防御技战术的来源。 
 * - `systemId`: 系统级别的唯一标识符，关联到特定的防御技战术体系。 
 * - `action`: 包含一个 `TacticAction` 对象的列表，描述具体的防御行动。 
 * - `strategy`: 包含 `SpaceActionStrategy` 对象的列表，描述与防御相关的策略。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法，简化代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为 API 文档生成提供字段描述。 
 * 4. **设计特点**： 
 * - 该类的设计考虑到了防御技战术体系中的多种属性，包括工具、来源、行动和策略等，具有较强的扩展性。 
 * - 部分字段被注释掉（如 `tacticId`, `role`, `tag` 等），可能在未来的版本中会重新启用或调整。 
 * 5. **适用场景**： 
 * - 该类适用于需要处理防御技战术新增请求的场景，特别是在涉及多维度信息（如工具、行动、策略）的系统中。 
 * 总体而言，该类是一个结构清晰、功能明确的数据传输对象（DTO），便于在前后端交互中传递防御技战术的相关信息。 
**/
@Getter
@Setter
public class DefenseAddReq {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 策略id
	 */
//	@ApiModelProperty(value = "战法id")
//	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "所属战术")
	private String categoryId;

	/**
	 * 角色
	 */
//	@ApiModelProperty(value = "岗位")
//	private Integer role;

	/**
	 * 标记
	 */
//	@ApiModelProperty(value = "标签")
//	private List<String> tag;

	/**
	 * 描述
	 */
//	@ApiModelProperty(value = "描述")
//	private String description;

	/**
	 * 场景
	 */
//	@ApiModelProperty(value = "作战环境")
//	private String scene;

	/**
	 * 监控
	 */
//	@ApiModelProperty(value = "监控")
//	private String monitor;

	/**
	 * 分析
	 */
//	@ApiModelProperty(value = "分析")
//	private String analyze;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<String> tool;

	/**
	 * 源
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "来源")
	private String source;

	/**
	 * 系统id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "防御技战术体系id")
	private String systemId;

	/**
	 * 行动
	 */
	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

	/**
	 * 策略
	 *
	 * @see List<SpaceActionStrategy>
	 */
	@ApiModelProperty(value = "相关策略")
	private List<SpaceActionStrategy> strategy;
}

