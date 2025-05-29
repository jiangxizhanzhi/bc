package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 空间容易威胁检测统治胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `SpaceAptThreatDetectRuleExcelBo` 的 Java 类，属于 `com.cyberpeace.space.service.context.rule` 包。以下是代码的简要分析： 
 * ### 功能描述： 
 * 该类是一个数据传输对象（DTO），用于表示空间中APT（高级持续性威胁）检测的相关信息。它主要用于存储和传递APT威胁检测规则的数据。 
 * ### 主要特点： 
 * 1. **注解使用**： 
 * - 使用了 Lombok 的 `@Data` 注解，自动生成 getter、setter、toString、equals 和 hashCode 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供了描述性注释，便于生成 API 文档。 
 * 2. **字段说明**： 
 * - `aptCode`：APT 威胁的唯一标识符。 
 * - `type`：检测的类型，例如某种具体的检测方式。 
 * - `description`：威胁检测的简介。 
 * - `content`：威胁检测的具体内容或详细信息。 
 * - `tool`：用于检测威胁的工具名称。 
 * 3. **文档注释**： 
 * - 类和字段都有详细的注释，说明其用途和含义，有助于代码的可读性和维护性。 
 * ### 总结： 
 * 这是一个简单的 DTO 类，主要用于封装与APT威胁检测相关的数据，并通过注解增强了代码的功能性和可读性。 
**/
@Data
public class SpaceAptThreatDetectRuleExcelBo {

	/**
	 * 容易代号
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "APT代号")
	private String aptCode;

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
}

