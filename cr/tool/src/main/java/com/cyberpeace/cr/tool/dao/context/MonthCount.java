package com.cyberpeace.cr.tool.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 月计数
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `MonthCount` 的 Java 类，该类实现了 `Serializable` 接口。以下是对其主要特性和功能的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.tool.dao.context` 
 * - **导入的库**： 
 * - `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档。 
 * - `lombok.*`：Lombok 库的注解，简化了样板代码的编写。 
 * ### Lombok 注解 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * - `@NoArgsConstructor`：自动生成无参构造函数。 
 * - `@AllArgsConstructor`：自动生成包含所有字段的全参数构造函数。 
 * ### 类属性 
 * - `private String month;`：表示月份，使用 `@ApiModelProperty` 注解说明其用途为“月份”。 
 * - `private Integer count;`：表示计数，使用 `@ApiModelProperty` 注解说明其用途为“新增的数量”。 
 * ### 实现接口 
 * - `Serializable`：使对象可以序列化，便于存储或传输。 
 * ### 总结 
 * 这个类主要用于存储和表示某个月份的计数值。通过 Lombok 注解，减少了大量样板代码，使得代码更加简洁。此外，通过 Swagger 注解，有助于生成 API 文档，便于前端开发人员理解和使用。 
**/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthCount implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 月
	 */
	@ApiModelProperty("月份")
	private String month;

	/**
	 * 计数
	 */
	@ApiModelProperty("新增的数量")
	private Integer count;
}

