/**
 * 这段代码定义了一个名为 `AdminListNoteReq` 的 Java 类，用于处理管理列表中关于笔记的请求。该类继承自 `PageReqBase` 类，并使用了 Lombok 库来简化 getter 和 setter 方法的生成。以下是代码的详细分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.ojbase.controller.req` 
 * - **类名**：`AdminListNoteReq` 
 * - **继承**：`PageReqBase` 
 * ### 注解 
 * - **Lombok 注解** 
 * - `@Getter`：为所有字段生成 getter 方法。 
 * - `@Setter`：为所有字段生成 setter 方法。 
 * - **Swagger 注解** 
 * - `@ApiModel`：标记这个类是一个 Swagger 模型，用于文档生成。 
 * - `@ApiModelProperty`：为每个字段提供描述信息，用于 Swagger 文档生成。 
 * ### 字段 
 * 1. **userName** 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty(name = "user_name", value = "姓名查询条件")` 
 * - 作用：用户名称，用于查询特定用户的笔记。 
 * 2. **noteType** 
 * - 类型：`Integer` 
 * - 注解：`@ApiModelProperty(name = "note_type", value = "笔记类型 笔记=1 实验报告=2")` 和 `@NotNull` 
 * - 作用：笔记类型，必须是非空值，区分笔记和实验报告。 
 * 3. **businessType** 
 * - 类型：`Integer` 
 * - 注解：`@ApiModelProperty(name = "business_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")` 
 * - 作用：业务类型，表示笔记关联的资源类型。 
 * 4. **businessId** 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty(name = "business_id", value = "资源编号 课时")` 
 * - 作用：业务 ID，表示笔记关联的具体资源编号。 
 * ### Getter 和 Setter 方法 
 * - 该类通过 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了所有字段的 getter 和 setter 方法，因此没有显式地编写这些方法。 
 * ### 总结 
 * - **功能**：`AdminListNoteReq` 类用于封装管理列表中关于笔记的查询条件，包括用户名称、笔记类型、业务类型和业务 ID。 
 * - **继承**：该类继承自 `PageReqBase` 类，可能包含了分页相关的属性和方法。 
 * - **注解**：使用了 Lombok 和 Swagger 注解，方便自动化生成文档和简化代码。 
 * - **设计**：通过注解和继承的设计，使得类简洁且易于维护。 
**/
package com.cyberpeace.oj.ojbase.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 管理列表注意请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class AdminListNoteReq extends PageReqBase {

	/**
	 * 用户名称
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "user_name", value = "姓名查询条件")
	private String userName;

	/**
	 * 注释类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "note_type", value = "笔记类型 笔记=1 实验报告=2")
	@NotNull
	private Integer noteType;

	/**
	 * 业务类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(name = "business_type", value = "资源类型 1=课程 2=课时 3=练习 4=课后练习")
	private Integer businessType;

	/**
	 * 业务id
	 *
	 * @see String
	 */
	@ApiModelProperty(name = "business_id", value = "资源编号 课时")
	private String businessId;

	public void setUser_name(String userName) {
		this.userName = userName;
	}

	public void setNote_type(Integer noteType) {
		this.noteType = noteType;
	}

	public void setBusiness_type(Integer businessType) {
		this.businessType = businessType;
	}

	public void setBusiness_id(String businessId) {
		this.businessId = businessId;
	}
}
