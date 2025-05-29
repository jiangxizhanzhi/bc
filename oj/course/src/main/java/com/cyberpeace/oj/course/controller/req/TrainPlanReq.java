package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 训练计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `TrainPlanReq` 的 Java 类，该类用于表示训练计划请求的数据结构。以下是对该代码的简要分析： 
 * ### 包声明和导入 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - 这个类位于 `com.cyberpeace.oj.course.controller.req` 包下。 
 * - **导入的类和注解** 
 * - `com.cyberpeace.common.model.PageReqBase`：这是一个基类，`TrainPlanReq` 类继承自它。 
 * - `io.swagger.annotations.ApiModelProperty`：这个注解用于描述类中的属性，通常用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：这两个注解由 Lombok 库提供，可以自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * - **类名**：`TrainPlanReq` 
 * - 这是一个公共类（`public class`），继承自 `PageReqBase` 类。 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * ### 类属性 
 * - **organizationId** 
 * - 类型为 `String`，表示组织 ID。 
 * - 使用了 `@ApiModelProperty` 注解来描述这个属性。 
 * - 这个属性还定义了一个显式的 setter 方法 `setOrganization_id`，但这个方法是多余的，因为 Lombok 的 `@Setter` 注解已经提供了相同功能的方法。 
 * ### 类文档 
 * - 类的 Javadoc 文档说明了这个类的用途，即表示训练计划请求体，并且指出了这个类继承自 `PageReqBase` 类。 
 * ### 总结 
 * - `TrainPlanReq` 类继承自 `PageReqBase` 类，并扩展了其功能以包含一个组织 ID 属性。 
 * - 使用了 Lombok 来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 通过 Swagger 的注解提供了属性的文档说明，有助于 API 文档的生成。 
 * - 显式的 `setOrganization_id` 方法是多余的，可以移除以保持代码简洁。 
**/
@Getter
@Setter
public class TrainPlanReq extends PageReqBase {

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "organization_id", value = "组织id")
	private String organizationId;

	public void setOrganization_id(String organizationId) {
		this.organizationId = organizationId;
	}
}

