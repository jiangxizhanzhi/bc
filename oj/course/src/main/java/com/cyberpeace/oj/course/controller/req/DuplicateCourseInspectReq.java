/**
 * 这段代码定义了一个名为 `DuplicateCourseInspectReq` 的 Java 类，用于表示重复课程检查请求体。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`package com.cyberpeace.oj.course.controller.req;` 
 * - 这个类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入的类和注解**： 
 * - `io.swagger.annotations.ApiModel`: 用于描述一个模型（例如，一个API响应或请求体）。 
 * - `io.swagger.annotations.ApiModelProperty`: 用于描述模型属性。 
 * - `lombok.Getter` 和 `lombok.Setter`: Lombok 注解，自动生成 getter 和 setter 方法。 
 * - `java.io.Serializable`: 标识这个类可以被序列化。 
 * - `javax.validation.constraints.NotEmpty`: 用于确保字符串列表不为空。 
 * ### 类定义 
 * - **类名**：`DuplicateCourseInspectReq` 
 * - **实现接口**：`Serializable` 
 * ### 类注释 
 * - **文档注释**：描述了这个类是一个重复课程检查请求体，并提供了作者、版本、日期等信息。 
 * ### 类成员变量 
 * - **fileNames**：类型为 `List<String>`，表示待检查的课程名称列表。 
 * - **注解**： 
 * - `@ApiModelProperty("待检查课程名称")`: 描述这个字段在 Swagger 文档中的说明。 
 * - `@NotEmpty`: 确保 `fileNames` 列表不为空。 
 * ### Getter 和 Setter 方法 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成，因此不需要手动编写这些方法。 
 * ### 总结 
 * - **用途**：这个类用于接收包含待检查课程名称的请求体。 
 * - **特性**：使用了 Swagger 注解来生成 API 文档，Lombok 注解简化了代码量，确保了列表不为空。 
 * 这段代码简洁明了，通过注解和 Lombok 提供了良好的可读性和功能。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 重复课程检查请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class DuplicateCourseInspectReq implements Serializable {

	/**
	 * 文件名
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("待检查课程名称")
	@NotEmpty
	private List<String> fileNames;

}
