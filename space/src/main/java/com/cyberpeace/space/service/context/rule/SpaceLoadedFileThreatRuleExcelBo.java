/**
 * 该代码定义了一个名为 `SpaceLoadedFileThreatRuleExcelBo` 的 Java 类，用于表示与空间加载文件威胁相关的业务对象。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于封装与空间加载文件威胁相关的信息，可能是用于导出或展示威胁规则的数据结构。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述性注释，方便 API 文档生成。 
 * 3. **字段说明**： 
 * - `type`：表示检测类型，可能对应不同的威胁分类。 
 * - `description`：提供威胁的简要描述。 
 * - `content`：详细描述威胁的内容。 
 * - `tool`：列出用于检测或处理威胁的工具。 
 * - `executeType`：指定执行方式，值为 `"1"` 表示手动，值为 `"2"` 表示自动。 
 * 4. **设计目的**： 
 * - 这是一个典型的 BO（Business Object）类，用于数据传输或展示。 
 * - 字段的设计较为简洁，覆盖了威胁规则的基本信息需求。 
 * 总结：该类是一个简单的数据传输对象（DTO），用于存储和传递空间加载文件威胁的相关信息，并通过注解增强了可读性和文档化能力。 
**/
package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 空间加载文件威胁统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceLoadedFileThreatRuleExcelBo {

	/**
	 * 类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "检测类型")
	private String type;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容简介")
	private String description;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "内容详情")
	private String content;

	/**
	 * 工具
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "应用工具")
	private String tool;

	/**
	 * 执行类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "执行方式，自动2，手动1")
	private String executeType;
}
