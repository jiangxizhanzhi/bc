package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 课程更新请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see CourseAddReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseUpdateReq` 的 Java 类，它是用于课程更新请求的模型类。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **继承关系**：`CourseUpdateReq` 继承自 `CourseAddReq` 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter` 和 `@Setter`：自动生成所有字段的 getter 和 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：标记这个类为 Swagger 模型对象。 
 * - `@ApiModelProperty`：描述类中的属性。 
 * - **JSR 380 (Bean Validation) 注解**： 
 * - `@NotBlank`：要求字段不能为空或空白字符串。该注解还指定了分组 `Update`。 
 * ### 字段 
 * - **resourceId**（类型：`String`）： 
 * - 描述：课程资源编号。 
 * - 注解： 
 * - `@ApiModelProperty("课程资源编号")`：描述字段用途。 
 * - `@NotBlank(groups = { Update.class })`：在 `Update` 分组中，该字段不能为空或空白字符串。 
 * ### 总结 
 * - **用途**：`CourseUpdateReq` 类用于处理课程更新请求的数据传输对象（DTO），它继承自 `CourseAddReq` 类。 
 * - **验证**：`resourceId` 字段在 `Update` 分组中必须提供非空值。 
 * - **文档生成**：通过 Swagger 注解，可以自动生成 API 文档。 
 * 这个类的设计目的是确保在更新课程时能够正确地传递和验证必要的信息。 
**/
@Getter
@Setter
@ApiModel
public class CourseUpdateReq extends CourseAddReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("课程资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}

