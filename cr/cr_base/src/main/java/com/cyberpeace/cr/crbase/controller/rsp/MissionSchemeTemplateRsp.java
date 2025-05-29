package com.cyberpeace.cr.crbase.controller.rsp;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.mission.MissionSchemeStage;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 任务方案模板响应
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MissionSchemeTemplateRsp` 的 Java 类，用于作为任务方案模板的响应对象。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装任务方案模板的相关信息，供外部接口返回使用。 
 * 2. **注解与框架**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，简化代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据说明，便于生成 API 文档。 
 * 3. **字段说明**： 
 * - `color`：表示色调，类型为整数。 
 * - `template`：表示模板类型，类型为整数。 
 * - `combatConfig`：表示战斗配置，是一个包含 `MissionSchemeStage` 对象的列表。 
 * - `config`：表示方案配置的 JSON 字符串。 
 * - `createUser`：表示创建者的用户 ID，类型为字符串。 
 * - `description`：表示描述信息，类型为字符串。 
 * - `repName`：表示创建者的姓名，类型为字符串。 
 * - `resourceId`：表示资源 ID，类型为字符串。 
 * - `title`：表示标题或名称，类型为字符串。 
 * 4. **设计特点**： 
 * - 类的设计符合 RESTful API 的响应结构，字段命名清晰，注释详细。 
 * - 使用了 Lombok 和 Swagger 注解，提高了开发效率并增强了代码的可读性。 
 * 总结：这是一个典型的 Java Bean 类，用于封装任务方案模板的响应数据，支持自动化注解和良好的文档生成能力。 
**/
@Getter
@Setter
public class MissionSchemeTemplateRsp {

	/**
	 * 颜色
	 */
	@ApiModelProperty(value = "色调")
	private Integer color;

	/**
	 * 模板
	 */
	@ApiModelProperty(value = "模板类型")
	private Integer template;

	/**
	 * 战斗配置
	 */
	@ApiModelProperty(value = "方案配置")
	private List<MissionSchemeStage> combatConfig;

	/**
	 * 配置
	 */
	@ApiModelProperty(value = "方案配置json")
	private String config;

	/**
	 * 创建用户
	 */
	@ApiModelProperty(value = "创建者id")
	private String createUser;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	/**
	 * 代表名称
	 */
	@ApiModelProperty(value = "创建者姓名")
	private String repName;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "编号")
	private String resourceId;

	/**
	 * 标题
	 */
	@ApiModelProperty(value = "名称")
	private String title;
}

