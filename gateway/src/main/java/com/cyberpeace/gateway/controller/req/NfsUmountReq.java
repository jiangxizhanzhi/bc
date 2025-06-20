package com.cyberpeace.gateway.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * NFSumount请求体
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `NfsUmountReq` 的 Java 类，用于表示 NFS 卸载操作的请求体。以下是对该代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.gateway.controller.req; 
 * ``` 
 * - 这行代码声明了该类所在的包为 `com.cyberpeace.gateway.controller.req`。 
 * ### 导入语句 
 * ```java 
 * import java.io.Serializable; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * import javax.validation.constraints.NotBlank; 
 * ``` 
 * - `Serializable` 接口：用于序列化对象。 
 * - `ApiModelProperty` 注解：用于 Swagger 文档生成，描述 API 参数。 
 * - `Getter` 和 `Setter` 注解：来自 Lombok 库，自动生成 getter 和 setter 方法。 
 * - `@NotBlank` 注解：用于验证字段不能为空或空白字符串。 
 * ### 类定义 
 * ```java 
 * @Getter 
 * @Setter 
 * public class NfsUmountReq implements Serializable { 
 * ``` 
 * - `@Getter` 和 `@Setter`：由 Lombok 自动生成 getter 和 setter 方法。 
 * - `implements Serializable`：使该类可以被序列化。 
 * - `NfsUmountReq` 类名：表示 NFS 卸载请求体。 
 * ### 字段定义 
 * ```java 
 * @ApiModelProperty(value = "卸载路径", required = true) 
 * @NotBlank 
 * private String path; 
 * ``` 
 * - `path` 字段：表示需要卸载的路径。 
 * - `@ApiModelProperty` 注解：描述该字段在 Swagger 文档中的名称和是否必填。 
 * - `@NotBlank` 注解：确保 `path` 字段不为空或空白字符串。 
 * ### 总结 
 * - **功能**：`NfsUmountReq` 类用于封装 NFS 卸载操作所需的路径信息。 
 * - **特性**： 
 * - 使用 Lombok 简化了 getter 和 setter 方法的编写。 
 * - 通过 `@ApiModelProperty` 和 `@NotBlank` 注解提供了文档生成和字段验证的功能。 
 * - 实现了 `Serializable` 接口，支持对象的序列化。 
 * 这个类主要用于接收和验证 NFS 卸载操作的路径参数，并且可以通过 Swagger 文档生成工具自动生成相关的 API 文档。 
**/
@Getter
@Setter
public class NfsUmountReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 路径
	 */
	@NotBlank
	@ApiModelProperty(value = "卸载路径", required = true)
	private String path;

}

