/**
 * 这段代码定义了一个名为 `ToolShellCodeListReq` 的 Java 类，该类用于处理工具壳代号列表的请求。以下是详细的分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.cr.tool.controller.req` 
 * - **继承关系**：该类继承自 `PageReqBase` 类。 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：为所有字段生成 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：标记这个类是一个 API 模型。 
 * - `@ApiModelProperty`：为类中的字段添加描述信息。 
 * ### 字段 
 * - **platform**：字符串类型，表示平台。 
 * - **author**：字符串类型，表示作者。 
 * - **edbVerified**：布尔类型，表示 EDB 验证状态。 
 * ### 方法 
 * - **setEdb_verified**：设置 `edbVerified` 字段的值。 
 * ### 总结 
 * - **用途**：该类用于处理获取工具壳代号列表的请求，包括指定平台、作者以及 EDB 验证状态等条件。 
 * - **设计**：使用 Lombok 自动生成 getter 和 setter 方法，简化了代码量。同时使用 Swagger 注解来提供文档描述。 
 * - **继承**：从 `PageReqBase` 继承，可能包含分页相关的属性或方法。 
 * ### 改进建议 
 * - **命名一致性**：`setEdb_verified` 方法名与字段名 `edbVerified` 不一致，建议统一为 `setEdbVerified`。 
 * - **注释**：增加一些方法注释，以增强代码的可读性和维护性。 
 * ```java 
 * public void setEdbVerified(Boolean edbVerified) { 
 * this.edbVerified = edbVerified; 
 * } 
 * ``` 
 * 这样可以提高代码的一致性和可维护性。 
**/
package com.cyberpeace.cr.tool.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 工具壳代号列表请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
@ApiModel
public class ToolShellCodeListReq extends PageReqBase {

	/**
	 * 平台
	 */
	@ApiModelProperty(value = "平台")
	private String platform;

	/**
	 * 作者
	 */
	@ApiModelProperty(value = "作者")
	private String author;

	/**
	 * EDB 验证
	 */
	@ApiModelProperty(name = "edb_verified", value = "是否验证")
	private Boolean edbVerified;

	public void setEdb_verified(Boolean edbVerified) {
		this.edbVerified = edbVerified;
	}
}
