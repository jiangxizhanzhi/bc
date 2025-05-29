package com.cyberpeace.space.service.context.tactic;

import com.cyberpeace.cr.tool.service.context.TacticTool;
import com.cyberpeace.space.service.context.frame.SpaceActionStrategy;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 防御详情
 *
 * @author SN
 * @date 2024/11/07
 */
/**
 * 这段代码定义了一个名为 `DefenseDetail` 的 Java 类，位于包 `com.cyberpeace.space.service.context.tactic` 下。以下是对其功能和结构的简要分析： 
 * ### 主要功能 
 * 该类用于表示防御相关的详细信息，包括资源 ID、名称、策略 ID、类别 ID 和名称、角色、标记、描述、场景、监控、分析、工具、行动、来源、系统 ID 和策略等字段。 
 * ### 关键点 
 * 1. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据说明，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - `resourceId`, `name`, `tacticId`, `categoryId`, `categoryName`, `role`, `description`, `scene`, `monitor`, `analyze`, `source`, `systemId`: 基本信息字段。 
 * - `tag`, `tool`, `action`, `strategy`: 分别表示标记列表、工具列表、行动列表和策略列表。 
 * 3. **依赖**： 
 * - 引用了外部类 `TacticTool` 和 `SpaceActionStrategy`，分别表示工具和行动策略。 
 * 4. **设计意图**： 
 * - 该类是一个数据传输对象（DTO），用于在不同层之间传递防御相关的详细信息。 
 * ### 总结 
 * 此代码是一个典型的 DTO 类，用于封装防御相关的信息，并通过注解简化了代码开发和文档生成过程。 
**/
@Getter
@Setter
public class DefenseDetail {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 策略id
	 */
	@ApiModelProperty(value = "战法id")
	private String tacticId;

	/**
	 * 类别id
	 */
	@ApiModelProperty(value = "所属战术id")
	private String categoryId;

	/**
	 * 类别名称
	 */
	@ApiModelProperty(value = "所属战术名称")
	private String categoryName;

	/**
	 * 角色
	 */
	@ApiModelProperty(value = "岗位")
	private Integer role;

	/**
	 * 标记
	 */
	@ApiModelProperty(value = "标签")
	private List<String> tag;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 场景
	 */
	@ApiModelProperty(value = "作战环境")
	private String scene;

	/**
	 * 监控
	 */
	@ApiModelProperty(value = "监控")
	private String monitor;

	/**
	 * 分析
	 */
	@ApiModelProperty(value = "分析")
	private String analyze;

	/**
	 * 工具
	 */
	@ApiModelProperty(value = "工具")
	private List<TacticTool> tool;

	/**
	 * 行动
	 */
	@ApiModelProperty(value = "行动")
	private List<TacticAction> action;

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
	@ApiModelProperty(value = "体系id")
	private String systemId;

	/**
	 * 策略
	 *
	 * @see List<SpaceActionStrategy>
	 */
	@ApiModelProperty(value = "策略")
	private List<SpaceActionStrategy> strategy;
}

