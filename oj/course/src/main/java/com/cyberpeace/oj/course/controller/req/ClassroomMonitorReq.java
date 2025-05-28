/**
 * 这段代码定义了一个名为 `ClassroomMonitorReq` 的 Java 类，该类继承自 `PageReqBase`。这个类主要用于处理与课堂监控相关的请求数据。以下是详细的分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`ClassroomMonitorReq` 
 * - **继承**：`PageReqBase` 
 * ### 注解 
 * - **`@Getter` 和 `@Setter`**：这两个注解来自 Lombok 库，用于自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**：这是一个 Swagger 注解，用于描述 API 模型。它通常用来提供模型的详细描述。 
 * ### 属性 
 * - **`classroomId`**： 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty("课堂表编号")` 
 * - 描述：表示课堂的唯一标识符。 
 * - **`pattern`**： 
 * - 类型：`String` 
 * - 注解：`@ApiModelProperty(value = "模糊查询筛选参数(不会转义)")` 
 * - 描述：用于模糊查询的筛选参数，不会进行转义。 
 * ### 方法 
 * - **`setClassroom_id(String classroomId)`**： 
 * - 这个方法是手动定义的 setter 方法，用于设置 `classroomId` 属性。虽然使用了 Lombok 的 `@Setter` 注解，但这里仍然定义了一个手动的 setter 方法，这可能会导致混淆。通常情况下，Lombok 的 `@Setter` 注解已经足够。 
 * ### 总结 
 * - **功能**：`ClassroomMonitorReq` 类主要用于封装课堂监控请求的数据，包括课堂 ID 和模糊查询的筛选参数。 
 * - **设计**：类的设计较为简单，主要包含一些基本的属性和方法。需要注意的是，虽然使用了 Lombok 的 `@Setter` 注解，但手动定义了一个同名的 setter 方法，这可能会导致不必要的重复代码。 
 * - **可改进点**：可以移除手动定义的 `setClassroom_id` 方法，以保持代码的一致性和简洁性。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂监控请求体
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
public class ClassroomMonitorReq extends PageReqBase {

	/**
	 * 课堂id
	 *
	 * @see String
	 */
	@ApiModelProperty("课堂表编号")
	private String classroomId;

	/**
	 * 模式
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
	private String pattern;

	public void setClassroom_id(String classroomId) {
		this.classroomId = classroomId;
	}

}
