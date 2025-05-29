package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 报告更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see ReportAddReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `ReportUpdateReq` 的类，该类继承自 `ReportAddReq`。以下是对其主要特性的简要分析： 
 * 1. **包声明**： 
 * - 类位于 `com.cyberpeace.oj.ojbase.controller.req` 包中。 
 * 2. **导入的库**： 
 * - 使用了 Lombok 库中的 `@Getter` 和 `@Setter` 注解，用于简化 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 注解 `@ApiModelProperty`，用于生成 API 文档。 
 * - 引入了 `javax.validation.constraints.NotBlank` 注解，用于确保字段不为空或空白。 
 * 3. **类注释**： 
 * - 类名为 `ReportUpdateReq`，表示这是一个用于报告更新的请求体。 
 * - 提供了作者、版本、日期以及相关类的引用信息。 
 * 4. **字段**： 
 * - `resourceId`：一个字符串类型的字段，表示实验报告的资源编号。 
 * - 字段上使用了 `@ApiModelProperty` 注解来描述字段用途。 
 * - 字段上使用了 `@NotBlank` 注解来确保该字段不能为空或空白。 
 * 5. **继承关系**： 
 * - `ReportUpdateReq` 继承自 `ReportAddReq`，表明它可能有一些与报告添加相关的共同属性或方法。 
 * 6. **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解用于自动生成所有字段的 getter 和 setter 方法，从而减少了样板代码。 
 * 总结： 
 * - 这是一个用于处理实验报告更新请求的 Java 类。 
 * - 它包含一个必需的字符串字段 `resourceId`，用于标识实验报告的资源编号。 
 * - 类的设计考虑到了 API 文档生成和数据验证的需求。 
 * - 通过继承 `ReportAddReq` 类，它可能还包含了其他与报告相关的通用属性或方法。 
**/
@Getter
@Setter
public class ReportUpdateReq extends ReportAddReq{

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("实验报告资源编号")
	@NotBlank
	private String resourceId;

}

