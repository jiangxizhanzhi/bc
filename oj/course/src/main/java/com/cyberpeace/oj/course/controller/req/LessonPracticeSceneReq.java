package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课实践场景请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeSceneReq` 的 Java 类，该类用于表示课程实践场景的请求体。以下是对此代码的简要分析： 
 * ### 类定义 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.controller.req` 包中。 
 * - **继承**：该类实现了 `Serializable` 接口，表示它可以被序列化，通常用于在网络上传输对象或将其保存到文件中。 
 * ### 注解 
 * - **Lombok**： 
 * - `@Getter` 和 `@Setter` 注解由 Lombok 提供，自动生成类属性的 getter 和 setter 方法。 
 * - **Swagger**： 
 * - `@ApiModel` 注解用于描述该类是一个模型类。 
 * - `@ApiModelProperty` 注解用于描述类中的字段，提供文档生成的帮助信息。 
 * ### 属性 
 * - **lessonId**：表示课程的 ID，类型为 `String`。通过 `@ApiModelProperty` 注解标记，用于在 Swagger 文档中显示“课时编号”。 
 * - **practiceId**：表示实践任务的 ID，类型为 `String`。通过 `@ApiModelProperty` 注解标记，用于在 Swagger 文档中显示“习题编号”。 
 * ### 总结 
 * 这个类主要用于封装课程实践场景的请求数据，包括课程 ID 和实践任务 ID。它使用了 Lombok 和 Swagger 注解来简化代码并生成相关的文档信息。 
**/
@Getter
@Setter
@ApiModel
public class LessonPracticeSceneReq implements Serializable {

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

}

