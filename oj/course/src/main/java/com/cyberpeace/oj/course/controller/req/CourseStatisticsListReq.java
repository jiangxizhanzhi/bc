/**
 * 这段代码定义了一个名为 `CourseStatisticsListReq` 的 Java 类，它位于包 `com.cyberpeace.oj.course.controller.req` 中。这个类用于处理课程统计学列表请求的数据。以下是详细的分析： 
 * ### 包声明和导入 
 * - **包声明**：`package com.cyberpeace.oj.course.controller.req;` 
 * - 这个类属于 `com.cyberpeace.oj.course.controller.req` 包。 
 * - **导入语句**： 
 * - `com.cyberpeace.common.model.PageReqBase;` 
 * - 引入了 `PageReqBase` 类，表示这个类继承或扩展了 `PageReqBase`。 
 * - `io.swagger.annotations.ApiModel;` 
 * - 引入了 Swagger 注解 `@ApiModel`，用于描述 API 模型。 
 * - `io.swagger.annotations.ApiModelProperty;` 
 * - 引入了 Swagger 注解 `@ApiModelProperty`，用于描述模型属性。 
 * - `lombok.Getter;` 
 * - 引入了 Lombok 注解 `@Getter`，用于自动生成 getter 方法。 
 * - `lombok.Setter;` 
 * - 引入了 Lombok 注解 `@Setter`，用于自动生成 setter 方法。 
 * ### 类声明 
 * - **类声明**：`@ApiModel public class CourseStatisticsListReq extends PageReqBase` 
 * - 使用了 Swagger 注解 `@ApiModel` 来标记这个类是一个 API 模型。 
 * - 这个类继承了 `PageReqBase` 类，可能包含了分页相关的属性和方法。 
 * ### 属性 
 * - **方向（direction）** 
 * - 类型：`String` 
 * - 注释：`@ApiModelProperty("方向")` 
 * - 描述：用户选择的方向。 
 * - **难度（difficulty）** 
 * - 类型：`Integer` 
 * - 注释：`@ApiModelProperty("课时类型")` 
 * - 描述：用户选择的课程难度。 
 * ### Getter 和 Setter 方法 
 * - **Lombok 注解** 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 自动生成 getter 和 setter 方法，减少了样板代码。 
 * ### 总结 
 * - `CourseStatisticsListReq` 是一个用于处理课程统计学列表请求的请求体类。 
 * - 它继承了 `PageReqBase` 类，可能包含分页相关的功能。 
 * - 类中定义了两个属性：`direction` 和 `difficulty`，分别表示方向和难度。 
 * - 使用了 Swagger 注解来描述 API 模型和属性。 
 * - 使用了 Lombok 注解来自动生成 getter 和 setter 方法。 
 * 这个类主要用于前端与后端交互时传递课程统计学列表请求的数据。 
**/
package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程统计学列表请求体
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
public class CourseStatisticsListReq extends PageReqBase {


	/**
	 * 方向
	 *
	 * @see String
	 */
	@ApiModelProperty("方向")
	private String direction;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("课时类型")
	private Integer difficulty;

}
