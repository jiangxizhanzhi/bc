/**
 * 这段代码定义了一个名为 `NoteAddReq` 的 Java 类，用于处理添加笔记的请求。以下是该类的详细分析： 
 * ### 类名和包声明 
 * - **包声明**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**：`NoteAddReq` 
 * ### 导入的包 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：用于生成 API 文档。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * - `javax.validation.constraints.NotBlank` 和 `javax.validation.constraints.NotNull`：用于验证字段是否为空或为 null。 
 * - `java.io.Serializable`：表示该类可以序列化。 
 * ### 类注释 
 * - `@ApiModel`：用于标识这是一个 API 模型类。 
 * - `@Getter` 和 `@Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类属性 
 * - `content`：笔记的内容，使用 `@ApiModelProperty` 注释描述其用途。 
 * - `noteType`：笔记的类型（1=笔记，2=实验报告），使用 `@NotNull` 注释确保其不为空。 
 * - `businessId`：业务 ID，使用 `@ApiModelProperty` 注释描述其用途，并使用 `@NotBlank` 注释确保其不为空或空白字符串。 
 * - `businessType`：业务类型（1=课程，2=课时，3=实操题，4=课后练习，5=理论题），使用 `@NotNull` 注释确保其不为空。 
 * ### 方法 
 * - `getContent()`：返回笔记内容，如果内容为 `null` 则返回空字符串。 
 * ### 总结 
 * - `NoteAddReq` 类主要用于接收添加笔记的请求数据。 
 * - 使用 Lombok 注解减少了样板代码。 
 * - 使用 Swagger 注解生成 API 文档。 
 * - 字段使用了约束注释来确保数据的有效性。 
 * - 提供了默认值处理逻辑，如果内容为 `null` 则返回空字符串。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 注意新增请求体
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
public class NoteAddReq implements Serializable {

	/**
	 * 内容
	 *
	 * @see String
	 */
	@ApiModelProperty("笔记内容")
	private String content;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("笔记类型 1=笔记 2=实验报告")
	@NotNull
	private Integer noteType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号 课程 课时 习题")
	@NotBlank
	private String businessId;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("资源类型 1=课程 2=课时 3=实操题 4=课后练习 5=理论题")
	@NotNull
	private Integer businessType;

	public String getContent() {
		return null == content ? "" : content;
	}
}
