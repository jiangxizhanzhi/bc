package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * 课实践场景节点请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see LessonPracticeSceneReq
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonPracticeSceneNodeReq` 的 Java 类，该类是 `LessonPracticeSceneReq` 类的子类。以下是对其简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`LessonPracticeSceneNodeReq` 
 * - **继承**：`LessonPracticeSceneNodeReq` 继承自 `LessonPracticeSceneReq` 
 * ### 注解 
 * - **`@ApiModel`**：用于描述这个类是一个模型对象。 
 * - **`@Getter` 和 `@Setter`**：来自 Lombok 库，自动生成属性的 getter 和 setter 方法。 
 * ### 属性 
 * - **`nodeId`**： 
 * - **类型**：`String` 
 * - **注解**： 
 * - **`@ApiModelProperty`**：用于 Swagger 文档，表示该字段在 API 中显示为 "节点编号"。 
 * - **`@NotBlank`**：表示该字段不能为空或空白字符串。 
 * ### 总结 
 * - **用途**：`LessonPracticeSceneNodeReq` 类用于表示与课程实践场景相关的节点请求体，并包含一个非空的字符串类型的 `nodeId` 字段。 
 * - **依赖库**：使用了 Lombok 和 Swagger 注解库来简化代码和生成文档。 
 * 这个类主要用于处理与课程实践场景节点相关的请求数据，确保 `nodeId` 字段不为空或空白字符串。 
**/
@Getter
@Setter
@ApiModel
public class LessonPracticeSceneNodeReq extends LessonPracticeSceneReq {

    /**
     * 节点id
     *
     * @see String
     */
    @ApiModelProperty("节点编号")
	@NotBlank
	private String nodeId;

}

