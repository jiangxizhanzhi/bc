/**
 * 这段代码定义了一个名为 `LessonStatisticsDetailReq` 的 Java 类，用于表示课程统计详情请求的数据模型。以下是对其主要特性的简要分析： 
 * 1. **包声明**：该类位于 `com.cyberpeace.oj.course.controller.req` 包下。 
 * 2. **导入语句**： 
 * - 导入了 `PageReqBase` 类，表明这个类可能继承或扩展了某种分页请求基类。 
 * - 导入了 Swagger 相关注解 (`@ApiModel`, `@ApiModelProperty`) 用于生成 API 文档。 
 * - 使用 Lombok 注解 (`@Getter`, `@Setter`) 来自动生成 getter 和 setter 方法，减少样板代码。 
 * 3. **类声明**：`LessonStatisticsDetailReq` 类继承自 `PageReqBase`，并使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来为所有字段提供 getter 和 setter 方法。 
 * 4. **文档注释**：类上使用了 Javadoc 注释，说明了这是一个课程统计详情请求体，并提供了作者、版本、日期等信息。同时指出了该类与 `PageReqBase` 的关系。 
 * 5. **字段及注解**： 
 * - `lessonId`: 表示课程 ID，使用 `@ApiModelProperty` 注解描述其用途为“课时ID”。 
 * - `search`: 用于搜索用户名或姓名，使用 `@ApiModelProperty` 注解描述其用途为“用户名、姓名查询”。 
 * - `orgId`: 表示组织 ID，使用 `@ApiModelProperty` 注解描述其用途为“组织”。 
 * 6. **功能**：这个类的主要目的是封装课程统计详情请求所需的信息，包括分页相关的属性（从 `PageReqBase` 继承）以及特定于课程统计详情的参数。 
 * 综上所述，`LessonStatisticsDetailReq` 是一个用于课程统计详情请求的数据模型类，通过继承和注解简化了代码结构和 API 文档生成。 
**/
package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课统计学详情请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@ApiModel
@Getter
@Setter
public class LessonStatisticsDetailReq extends PageReqBase {

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时ID")
	private String lessonId;

	/**
	 * 搜索
	 *
	 * @see String
	 */
	@ApiModelProperty("用户名、姓名查询")
	private String search;

	/**
	 * 组织id
	 *
	 * @see String
	 */
	@ApiModelProperty("组织")
	private String orgId;

}
