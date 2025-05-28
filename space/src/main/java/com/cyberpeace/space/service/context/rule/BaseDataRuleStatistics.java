/**
 * 该代码定义了一个名为 `BaseDataRuleStatistics` 的 Java 类，用于表示基本数据规则的统计信息。以下是代码的主要分析和总结： 
 * ### 主要内容： 
 * 1. **类功能**： 
 * - 该类是一个简单的数据传输对象（DTO），用于存储与安全相关的统计数据。 
 * - 它包含了四个字段，分别表示不同类型的规则或数据的数量。 
 * 2. **字段说明**： 
 * - `vuln`：表示漏洞库的数量，默认值为 0。 
 * - `fingerPrint`：表示指纹库的数量，默认值为 0。 
 * - `apt`：表示 APT 特征的数量，默认值为 0。 
 * - `ioc`：表示 IOC 威胁情报的数量，默认值为 0。 
 * 3. **注解使用**： 
 * - 使用了 Lombok 提供的 `@Getter` 和 `@Setter` 注解，自动生成字段的 getter 和 setter 方法。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解，为每个字段添加了描述性注释，便于生成 API 文档。 
 * 4. **文档说明**： 
 * - 类上添加了 Javadoc 注释，说明该类的作用是“基本数据规则统计信息”。 
 * 5. **默认值**： 
 * - 所有字段都初始化为默认值 `0`，表示在未赋值的情况下默认为零。 
 * ### 总结： 
 * 这是一个轻量级的数据结构类，主要用于存储与安全相关的统计数据。通过 Lombok 和 Swagger 注解，简化了代码的开发和文档生成工作。 
**/
package com.cyberpeace.space.service.context.rule;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 基本数据规则统计信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class BaseDataRuleStatistics {

	/**
	 * 漏洞
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "漏洞库数量")
	private Integer vuln = 0;

	/**
	 * 指纹
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "指纹库")
	private Integer fingerPrint = 0;

	/**
	 * 容易
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "apt特征")
	private Integer apt = 0;

	/**
	 * 国际 奥委会
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "ioc威胁情报")
	private Integer ioc = 0;
}
