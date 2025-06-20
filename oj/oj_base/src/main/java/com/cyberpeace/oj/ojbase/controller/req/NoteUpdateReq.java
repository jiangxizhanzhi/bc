package com.cyberpeace.oj.ojbase.controller.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 注意更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `NoteUpdateReq` 的 Java 类，用于表示更新笔记请求的请求体。以下是该类的主要特点和功能的简要分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **导入**： 
 * - `javax.validation.constraints.NotBlank`：用于确保某些字段不为空或空白。 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于 Swagger 文档生成。 
 * - `lombok.Getter` 和 `lombok.Setter`：简化 getter 和 setter 方法的生成。 
 * - `java.io.Serializable`：使对象可以序列化。 
 * ### 类定义 
 * - **类名**：`NoteUpdateReq` 
 * - **继承**：实现了 `Serializable` 接口。 
 * - **注解**： 
 * - `@ApiModel`：表示这个类是一个模型（例如，用于 Swagger 文档）。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，自动生成所有字段的 getter 和 setter 方法。 
 * ### 字段 
 * - **resourceId**：笔记资源编号，必须是非空字符串 (`@NotBlank`)。 
 * - **content**：笔记内容，允许为空。 
 * - **operationAuthType**：授权操作类型（整数），默认值为 1 或 2，但隐藏在 Swagger 文档中。 
 * - **operationAuthIds**：授权操作人编号，允许为空且隐藏在 Swagger 文档中。 
 * - **readAuthType**：授权访问权限类型（整数），默认值为 1 或 2，但隐藏在 Swagger 文档中。 
 * - **readAuthIds**：授权组织编号，允许为空且隐藏在 Swagger 文档中。 
 * ### 总结 
 * - 这个类主要用于处理更新笔记时的请求数据。 
 * - 使用了 Lombok 来简化代码，避免手动编写 getter 和 setter 方法。 
 * - 字段通过 Swagger 注解进行文档化，便于 API 文档生成。 
 * - 部分字段被标记为隐藏，可能是因为它们在 API 文档中不需要展示。 
 * 整体而言，这是一个简单的数据传输对象（DTO），用于从客户端接收更新笔记所需的数据。 
**/
@Getter
@Setter
@ApiModel
public class NoteUpdateReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记资源编号")
	@NotBlank
	private String resourceId;

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 实操认证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权操作类型 1=共享 2=自定义", hidden = true)
	private Integer operationAuthType;

	/**
	 * 实操认证id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "授权操作人编号", hidden = true)
	private String operationAuthIds;

	/**
	 * 读取身份验证类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "授权访问权限 1=共享 2=自定义", hidden = true)
	private Integer readAuthType;

	/**
	 * 读认证id列表
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "授权组织编号", hidden = true)
	private String readAuthIds;

}

