/**
 * 这段代码定义了一个名为 `AptReportHistory` 的 Java 类，用于表示 APT 报告的历史记录。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类主要用于存储 APT 报告的历史信息，包括发现时间、报告名称和发布厂商。 
 * 2. **注解说明**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为字段提供了描述性注释，方便文档生成和接口理解。 
 * 3. **属性说明**： 
 * - `time`：表示报告的发现时间，类型为 `LocalDate`，符合日期格式。 
 * - `name`：表示报告的名称，类型为 `String`。 
 * - `firm`：表示发布该报告的厂商，类型为 `String`。 
 * 4. **设计特点**： 
 * - 类的设计简单明了，专注于存储特定的业务数据。 
 * - 使用注解减少了冗长的代码，提升了开发效率。 
 * 总结：这是一个典型的 DTO（Data Transfer Object）类，用于在系统中传递 APT 报告的历史数据。 
**/
package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * APT 报告历史
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class AptReportHistory {

	/**
	 * 时间
	 *
	 * @see LocalDate
	 */
	@ApiModelProperty(value = "发现时间")
	private LocalDate time;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "报告名称")
	private String name;

	/**
	 * 公司
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "发布厂商")
	private String firm;
}
