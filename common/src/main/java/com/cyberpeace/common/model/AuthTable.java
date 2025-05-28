/**
 * ### 代码分析 
 * #### 类概述 
 * - **类名**：`AuthTable` 
 * - **继承**：`AuthTable` 继承自 `BaseTable`。 
 * - **功能**：该类表示一个数据库表（`auth` 表），用于管理授权相关的数据。 
 * #### 主要字段 
 * 1. **`operationAuthType`** 
 * - **描述**：实操认证类型，表示授权的操作类型。 
 * - **枚举值**： 
 * - `1`：共享 
 * - `2`：自定义 
 * - **注解**：`@ApiModelProperty` 用于生成 API 文档。 
 * 2. **`operationAuthIds`** 
 * - **描述**：实操认证 ID 列表，存储授权操作用户的编号，使用分隔符分隔。 
 * - **注解**：`@ApiModelProperty` 用于生成 API 文档。 
 * 3. **`readAuthType`** 
 * - **描述**：读取身份验证类型，表示授权访问的类型。 
 * - **枚举值**： 
 * - `1`：共享 
 * - `2`：自定义 
 * - **注解**：`@ApiModelProperty` 用于生成 API 文档。 
 * 4. **`readAuthIds`** 
 * - **描述**：读认证 ID 列表，存储授权访问组织的编号，使用分隔符分隔。 
 * - **注解**：`@ApiModelProperty` 用于生成 API 文档。 
 * #### 枚举类 `AuthType` 
 * - **实现接口**：`GenericEnum`，通常用于通用枚举类型。 
 * - **字段**： 
 * - `code`：枚举值的编号。 
 * - `desc`：枚举值的描述。 
 * - **构造方法**：通过 `code` 和 `desc` 初始化枚举实例。 
 * - **枚举值**： 
 * - `ALL_SHARE_MODE`：授权所有（共享模式）。 
 * - `CUSTOM_SHARE_MODE`：自定义授权。 
 * #### 特性 
 * - **Lombok 注解**：`@Getter` 和 `@Setter` 自动生成 getter 和 setter 方法。 
 * - **Swagger 注解**：`@ApiModelProperty` 用于生成 API 文档，方便前端和后端开发人员理解字段用途。 
 * #### 总结 
 * 该类主要用于管理授权相关的数据，包括操作认证和读取认证的类型及对应的 ID 列表。通过枚举类 `AuthType` 定义了授权类型的通用规范，并结合 Swagger 注解提升了代码的可读性和文档化程度。 
**/
package com.cyberpeace.common.model;

import com.cyberpeace.common.baseinterface.GenericEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * auth 表
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class AuthTable extends BaseTable {

	/**
	 * 实操认证类型
	 */
	@ApiModelProperty("授权操作类型 1=共享 2=自定义")
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 */
	@ApiModelProperty("授权操作用户编号 ,为编号分割符")
	private String operationAuthIds;

	/**
	 * 读取身份验证类型
	 */
	@ApiModelProperty("授权访问类型 1=共享 2=自定义")
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 */
	@ApiModelProperty("授权访问组织编号 ,为分割符")
	private String readAuthIds;

	public enum AuthType implements GenericEnum {

		ALL_SHARE_MODE(1, "授权所有"), CUSTOM_SHARE_MODE(2, "自定义");

		@Getter
		private Integer code;

		@Getter
		private String desc;

		AuthType(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
