package com.cyberpeace.gateway.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * 外部批量请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `ExternalBatchReq` 的 Java 类，该类用于表示外部批量请求的请求体。以下是对该代码的简要分析： 
 * ### 类和包声明 
 * - **包声明**：`package com.cyberpeace.gateway.controller.req;` 
 * - 这个类位于 `com.cyberpeace.gateway.controller.req` 包中。 
 * ### 导入的类 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty` 用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter` 注解来自 Lombok 库，用于自动生成 getter 和 setter 方法。 
 * - `javax.validation.constraints.NotEmpty` 用于验证字段是否为空。 
 * - `java.io.Serializable` 接口用于序列化对象。 
 * ### 类定义 
 * - **类名**：`ExternalBatchReq` 
 * - **注解**： 
 * - `@ApiModel`：用于描述这个类是一个模型（例如在 Swagger 中）。 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：表示这个类可以被序列化。 
 * ### 类成员变量 
 * - **`resourceIds`**： 
 * - 类型：`List<String>` 
 * - 注释：`资源id列表` 
 * - 注解： 
 * - `@ApiModelProperty(name = "resource_ids", value = "资源编号数组")`：用于生成 API 文档。 
 * - `@NotEmpty`：确保 `resourceIds` 列表不为空。 
 * ### 方法 
 * - **`setResource_ids`**： 
 * - 参数：`List<String> resourceIds` 
 * - 功能：设置 `resourceIds` 列表。 
 * - 注意：这个方法是多余的，因为 Lombok 的 `@Setter` 注解已经自动生成了 `setResourceIds` 方法。 
 * ### 总结 
 * - 这个类主要用于接收包含资源 ID 列表的外部批量请求。 
 * - 使用了 Lombok 来简化代码，减少了样板代码。 
 * - 验证注解 `@NotEmpty` 确保 `resourceIds` 不为空。 
 * - 通过 Swagger 注解来生成 API 文档，方便接口文档的维护。 
 * ### 改进建议 
 * - 可以删除 `setResource_ids` 方法，因为它与 Lombok 自动生成的方法重复。 
 * - 如果需要更严格的验证逻辑，可以在 `setResourceIds` 方法中添加额外的逻辑。 
**/
@Getter
@Setter
@ApiModel
public class ExternalBatchReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id列表
	 */
	@ApiModelProperty(name = "resource_ids", value = "资源编号数组")
	@NotEmpty
	private List<String> resourceIds;

	/**
	 * 设置资源id列表
	 *
	 * @param resourceIds 资源id列表
	 */
	public void setResource_ids(List<String> resourceIds) {
		this.resourceIds = resourceIds;
	}
}

