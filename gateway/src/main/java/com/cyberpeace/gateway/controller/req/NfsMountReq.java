/**
 * 这段代码定义了一个名为 `NfsMountReq` 的 Java 类，该类用于表示 NFS（Network File System）安装请求的数据结构。以下是对其主要特性和功能的简要分析： 
 * ### 包声明与导入 
 * - **包声明**：该类位于 `com.cyberpeace.gateway.controller.req` 包中。 
 * - **导入的类**： 
 * - `Serializable`：表示该类实现了序列化接口，可以在网络上传输或持久化保存。 
 * - `NotBlank`：用于验证字段不能为空或空白字符串。 
 * - `ApiModelProperty`：用于 Swagger 文档生成，描述 API 参数。 
 * - `Getter` 和 `Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类定义 
 * - **类名**：`NfsMountReq` 
 * - **实现的接口**：`Serializable` 
 * - **注解**： 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `@ApiModelProperty`：用于 Swagger 文档生成，提供参数说明。 
 * ### 类属性 
 * - **host**： 
 * - 类型：`String` 
 * - 注解： 
 * - `@NotBlank`：确保该字段不为空或空白字符串。 
 * - `@ApiModelProperty`：描述为“主机地址”，且必填。 
 * - **path**： 
 * - 类型：`String` 
 * - 注解： 
 * - `@NotBlank`：确保该字段不为空或空白字符串。 
 * - `@ApiModelProperty`：描述为“挂载路径”，且必填。 
 * ### 总结 
 * - **用途**：`NfsMountReq` 类用于接收和封装 NFS 安装请求的数据，包括主机地址和挂载路径。 
 * - **数据验证**：使用了 `@NotBlank` 注解来确保这两个字段在请求时必须提供有效值。 
 * - **文档生成**：通过 `@ApiModelProperty` 注解，该类可以方便地集成到 Swagger 等 API 文档工具中，生成相关的 API 文档。 
 * - **可序列化**：实现了 `Serializable` 接口，支持对象在网络上传输或持久化存储。 
 * 该类是一个典型的 DTO（Data Transfer Object），主要用于传输数据，并通过注解增强了数据验证和文档生成的功能。 
**/
package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * NFS安装请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class NfsMountReq implements Serializable {

	/**
	 * 主机
	 */
	@NotBlank
	@ApiModelProperty(value = "主机地址", required = true)
	private String host;

	/**
	 * 路径
	 */
	@NotBlank
	@ApiModelProperty(value = "挂载路径", required = true)
	private String path;

}
