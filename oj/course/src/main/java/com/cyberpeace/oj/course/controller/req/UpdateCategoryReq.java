package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.baseinterface.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 更新类别请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see SaveCourseCategoryReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `UpdateCategoryReq` 的 Java 类，该类用于处理更新课程类别的请求。以下是对其主要特性和功能的简要分析： 
 * 1. **包声明**：代码位于 `com.cyberpeace.oj.course.controller.req` 包中，表明这是一个请求对象，用于课程管理模块中的控制器层。 
 * 2. **导入语句**： 
 * - 导入了自定义接口 `Update` 和一些来自 `com.cyberpeace.common.baseinterface` 包中的元素。 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来简化 getter 和 setter 方法的生成。 
 * - 使用了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty` 来描述模型，并为 API 文档提供信息。 
 * - 引入了 `javax.validation.constraints.NotBlank` 用于验证字段是否为空。 
 * 3. **类定义**： 
 * - `UpdateCategoryReq` 类继承自 `SaveCourseCategoryReq` 类，这表明它可能共享了一些保存课程类别时所需的属性。 
 * - 类上使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，表示该类的属性可以通过 Lombok 自动生成的 getter 和 setter 方法访问。 
 * 4. **字段定义**： 
 * - `resourceId` 字段是一个 `String` 类型的字符串，用于标识资源的编号。这个字段被标记为必须非空（通过 `@NotBlank` 注解），并且仅在 `Update` 验证组中进行检查。 
 * 5. **文档注释**： 
 * - 类上有详细的 Javadoc 注释，描述了该类的作用、作者、版本信息以及相关的其他类。 
 * - 字段 `resourceId` 上也有相应的 Javadoc 注释，说明了该字段的用途。 
 * 6. **Swagger 注解**： 
 * - `@ApiModelProperty` 注解提供了关于 `resourceId` 字段的元数据，有助于 API 文档的生成。 
 * 总结来说，`UpdateCategoryReq` 是一个用于更新课程类别的请求对象，继承了保存课程类别所需的一些属性，并且包含了必要的验证逻辑以确保更新操作的有效性。 
**/
@Getter
@Setter
@ApiModel
public class UpdateCategoryReq extends SaveCourseCategoryReq {

	/**
	 * 资源id
	 *
	 * @see String
	 */
	@ApiModelProperty("资源编号")
	@NotBlank(groups = { Update.class })
	private String resourceId;

}

