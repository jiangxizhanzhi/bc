/**
 * 这段代码定义了一个名为 `BatchAuthLessonPracticeReq` 的 Java 类，用于处理批量认证课实践请求。以下是详细的分析和总结： 
 * ### 类名与包名 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `BatchAuthLessonPracticeReq` 
 * ### 类的注解 
 * - `@Getter` 和 `@Setter`: 这两个注解来自 Lombok 库，它们自动生成 getter 和 setter 方法。 
 * - `@ApiModel`: 来自 Swagger 注解库，用于描述这个类在 API 文档中的模型。 
 * ### 类的描述 
 * - 类的描述信息通过 Javadoc 提供，说明了该类是用于批量认证课实践请求体。 
 * ### 类的属性 
 * - `lessonId`: 课时编号，类型为 `String`，并且必须提供（使用 `@NotBlank` 注解）。 
 * - `organizationId`: 组织 ID，类型为 `String`，并且必须提供（使用 `@NotBlank` 注解）。 
 * - `isPublic`: 是否公开，类型为 `Boolean`，可选参数。 
 * - `classroomId`: 课堂 ID，类型为 `String`，并且必须提供（使用 `@NotBlank` 注解）。 
 * ### 属性的注解 
 * - `@ApiModelProperty`: 用于描述每个属性在 API 文档中的含义。 
 * - `lessonId`: 描述为“课时编号”。 
 * - `organizationId`: 描述为“组织id”。 
 * - `isPublic`: 描述为“是否下发”。 
 * - `classroomId`: 描述为“课堂id”。 
 * ### 实现的接口 
 * - `Serializable`: 该类实现了 `Serializable` 接口，表示它可以被序列化。 
 * ### 总结 
 * - `BatchAuthLessonPracticeReq` 类是一个用于处理批量认证课实践请求的 DTO（数据传输对象），主要用于 API 请求中传递数据。 
 * - 类中的属性都带有相应的注解，以确保在 API 文档中正确显示，并且部分属性需要非空验证。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，减少了样板代码。 
**/
package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


/**
 * 批量认证课实践请求体
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
public class BatchAuthLessonPracticeReq implements Serializable {

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("组织id")
    @NotBlank
    private String organizationId;

    /**
     * 是否公开
     *
     * @see Boolean
     */
    @ApiModelProperty("是否下发")
    private Boolean isPublic;

    /**
     * 课堂id
     *
     * @see String
     */
    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}
