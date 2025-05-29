package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 课程类别列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseCategoryListReq` 的 Java 类，用于处理课程类别列表的请求。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `CourseCategoryListReq` 
 * - **继承关系**: 继承自 `PageReqBase` 
 * ### 主要特性 
 * 1. **注解使用** 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModel` 和 `@ApiModelProperty` 来描述模型和属性，方便生成 API 文档。 
 * - 使用了 `@NotNull` 注解来确保 `children` 属性不为 null。 
 * 2. **属性** 
 * - `children`: 布尔型变量，表示是否查询子类数据。默认情况下，该属性是必需的（通过 `@NotNull` 注解）。 
 * - **描述**: 是否查询子类数据。 
 * - **值**: `true` 表示查询子类数据，`false` 表示不查询子类数据。 
 * 3. **文档注释** 
 * - 类上有详细的 Javadoc 注释，说明了该类的作用、作者、版本和相关引用。 
 * - 属性 `children` 上也有详细的 Javadoc 注释，说明了其用途。 
 * ### 总结 
 * - `CourseCategoryListReq` 类主要用于接收课程类别列表请求的数据。 
 * - 它继承了 `PageReqBase` 类，可能包含分页相关的参数。 
 * - `children` 参数用于控制是否查询子类数据。 
 * - 该类使用了 Lombok 和 Swagger 注解，简化了代码并增强了可读性和可维护性。 
 * 这个类的设计简洁明了，易于理解，并且符合现代 Java 开发的最佳实践。 
**/
@Getter
@Setter
@ApiModel
public class CourseCategoryListReq extends PageReqBase {

	/**
	 * 子级
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否查询子类数据 true查询子类 false不查询")
	@NotNull
	private Boolean children;

}

