/**
 * 这段代码定义了一个名为 `ClassroomGroupArchiveBo` 的 Java 类，用于表示课堂组档案业务对象（BO）。以下是对其主要特性和功能的简要分析： 
 * ### 类定义与注解 
 * - **包声明**：类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * - **继承与实现**：该类实现了 `Serializable` 接口，这意味着它可以被序列化，便于在网络上传输或持久化存储。 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter` 注解自动生成所有字段的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel` 注解用于描述这个模型对象，通常用于 Swagger 文档生成。 
 * - `@ApiModelProperty` 注解用于为每个字段提供描述信息，这些描述可以在 Swagger UI 中显示。 
 * ### 字段定义 
 * - **resourceId**：表示资源 ID，通过 `@ApiModelProperty("模板编号")` 注解进行描述。 
 * - **name**：表示名称，通过 `@ApiModelProperty("模板名称")` 注解进行描述。 
 * - **classroomId**：表示课堂 ID，通过 `@ApiModelProperty("课堂编号")` 注解进行描述。 
 * ### 总结 
 * `ClassroomGroupArchiveBo` 类是一个简单的数据传输对象（DTO），主要用于在系统内部不同模块之间传递课堂组档案的信息。它使用了 Lombok 来简化代码，并利用 Swagger 注解来增强文档生成。该类的设计目的是为了清晰地表示课堂组的相关信息，并确保这些信息可以被方便地序列化和反序列化。 
**/
package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课堂组档案 bo
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
public class ClassroomGroupArchiveBo implements Serializable {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("模板编号")
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("模板名称")
	private String name;

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂编号")
	private String classroomId;
}
