/**
 * 这段代码定义了一个名为 `LessonPracticeSceneStatusReq` 的 Java 类，用于表示课实践场景的状态请求体。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`LessonPracticeSceneStatusReq` 
 * - **实现接口**：`Serializable`，表明该类的对象可以被序列化。 
 * ### 注解 
 * - **`@Getter` 和 `@Setter`**：由 Lombok 提供，自动生成属性的 getter 和 setter 方法。 
 * - **`@ApiModel`**：来自 Swagger 注解库，用于描述这个类作为 API 模型对象。 
 * - **`@ApiModelProperty`**：同样来自 Swagger 注解库，用于为类的属性添加描述信息。 
 * ### 属性 
 * 1. **`lessonId`** 
 * - 类型：`String` 
 * - 描述：课时编号 
 * - 注解：`@ApiModelProperty("课时编号")` 
 * 2. **`practiceId`** 
 * - 类型：`String` 
 * - 描述：习题编号 
 * - 注解：`@ApiModelProperty("习题编号")` 
 * 3. **`status`** 
 * - 类型：`Integer` 
 * - 描述：场景状态（1=启动，3=暂停） 
 * - 注解：`@ApiModelProperty("场景状态 1=启动 3=暂停")` 
 * ### 总结 
 * - 这个类主要用于接收前端传来的课实践场景状态请求数据。 
 * - 它包含三个属性：课时编号、习题编号和场景状态。 
 * - 使用 Lombok 的注解减少了样板代码，提高了代码的可读性和简洁性。 
 * - Swagger 注解使得这个类在生成 API 文档时更加清晰明了。 
**/
package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课实践场景状态请求体
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
public class LessonPracticeSceneStatusReq implements Serializable {

	/**
	 * 课id
	 *
	 * @see String
	 */
	@ApiModelProperty("课时编号")
	private String lessonId;

	/**
	 * 实践id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题编号")
	private String practiceId;

	/**
	 * 状态
	 *
	 * @see Integer
	 */
	@ApiModelProperty("场景状态 1=启动 3=暂停")
	private Integer status;

}
