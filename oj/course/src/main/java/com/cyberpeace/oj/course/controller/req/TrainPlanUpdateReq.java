/**
 * 这段代码定义了一个名为 `TrainPlanUpdateReq` 的 Java 类，用于表示训练计划更新请求体。以下是该类的详细分析： 
 * ### 类声明 
 * - **包声明**：`package com.cyberpeace.oj.course.controller.req;` 
 * - 这个类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **导入语句**： 
 * - `import java.io.Serializable;` 
 * - 导入 `Serializable` 接口，使得这个类可以序列化。 
 * - `import io.swagger.annotations.ApiModelProperty;` 
 * - 导入 `ApiModelProperty` 注解，用于在 Swagger 文档中描述属性。 
 * - `import lombok.Getter;` 
 * - `import lombok.Setter;` 
 * - 使用 Lombok 库来简化 getter 和 setter 方法的生成。 
 * ### 类注释 
 * - 类的注释说明了该类是用于训练计划更新请求体，并提供了作者、版本和日期信息。 
 * ### 类定义 
 * - **类名**：`TrainPlanUpdateReq` 
 * - 实现了 `Serializable` 接口，因此该类的对象可以被序列化。 
 * - **Lombok 注解** 
 * - `@Getter`：为所有字段自动生成 getter 方法。 
 * - `@Setter`：为所有字段自动生成 setter 方法。 
 * ### 字段 
 * - **resourceId** 
 * - 类型：`String` 
 * - 注释说明这是一个资源 ID。 
 * - 使用 `@ApiModelProperty` 注解，标明该字段在 Swagger 文档中的名称为 "资源Id"。 
 * - **name** 
 * - 类型：`String` 
 * - 注释说明这是一个名称。 
 * - 使用 `@ApiModelProperty` 注解，标明该字段在 Swagger 文档中的名称为 "资源名称"。 
 * ### 总结 
 * - **用途**：`TrainPlanUpdateReq` 类用于接收并处理训练计划更新请求的数据。 
 * - **特性**：使用 Lombok 简化了 getter 和 setter 方法的编写，并通过 Swagger 注解增强了文档的可读性。 
 * - **结构**：该类包含两个字段 `resourceId` 和 `name`，分别用于存储资源 ID 和名称。 
 * 这段代码简洁明了，符合良好的编码实践。 
**/
package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练计划更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanUpdateReq implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源Id")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("资源名称")
	private String name;
}
