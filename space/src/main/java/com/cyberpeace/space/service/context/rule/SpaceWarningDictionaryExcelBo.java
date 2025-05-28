/**
 * 这段代码定义了一个名为 `SpaceWarningDictionaryExcelBo` 的 Java 类，用于表示空间警告字典的业务对象（BO）。以下是代码的简要分析： 
 * ### 主要功能 
 * - **类作用**：该类主要用于存储与空间警告相关的数据信息，适合作为 Excel 导入导出的数据传输对象（DTO）。 
 * - **注解说明**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法，减少冗余代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段提供元数据说明，便于生成 API 文档。 
 * ### 字段说明 
 * - `name`：告警名称，字符串类型。 
 * - `toolType`：工具类型，字符串类型。 
 * - `toolLevel`：工具告警等级，字符串类型。 
 * - `level`：告警等级（整数类型），具体映射为低(1)、中(2)、高(3)。 
 * - `description`：等级描述，字符串类型。 
 * - `updateTime`：更新时间，`Date` 类型。 
 * ### 总结 
 * 这是一个简单的数据传输类，主要目的是封装与空间警告相关的信息，并通过注解增强了代码的可读性和可维护性。适合在需要处理 Excel 数据或生成 API 文档的场景中使用。 
**/
package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 空间警告字典胜过Bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class SpaceWarningDictionaryExcelBo {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "告警名称")
	private String name;

	/**
	 * 工具类型
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具类型")
	private String toolType;

	/**
	 * 工具层级
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "工具告警等级")
	private String toolLevel;

	/**
	 * 层级
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "告警等级,低1,中2,高3")
	private Integer level;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "等级描述")
	private String description;

	/**
	 * 更新时间
	 *
	 * @see Date
	 */
	@ApiModelProperty("更新时间")
	private Date updateTime;
}
