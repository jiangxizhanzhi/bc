package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


/**
 * 标记课实践请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MarkLessonPracticeReq` 的 Java 类，该类用于表示标记课程实践请求的数据模型。以下是对该类的简要分析： 
 * ### 类名和包声明 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`MarkLessonPracticeReq` 
 * ### 类的注解 
 * - **`@ApiModel`**：此注解来自 Swagger，用于描述 API 模型。 
 * - **`@Getter` 和 `@Setter`**：这两个注解来自 Lombok 库，自动生成属性的 getter 和 setter 方法。 
 * ### 实现的接口 
 * - **`Serializable`**：表示该类实现了序列化接口，可以被序列化为字节流。 
 * ### 属性 
 * - **`lessonId`**：表示课时编号，类型为 `String`，使用 `@NotBlank` 注解确保其不为空。 
 * - **`userId`**：表示用户 ID，类型为 `String`，同样使用 `@NotBlank` 注解确保其不为空。 
 * - **`level`**：表示水平或等级，类型为 `Integer`，默认值未指定，范围从 1 到 4。 
 * - **`classroomId`**：表示课堂 ID，类型为 `String`，使用 `@NotBlank` 注解确保其不为空。 
 * ### 字段注释 
 * 每个字段都有详细的 Javadoc 注释，描述了字段的用途及其可能的值。 
 * ### 总结 
 * 这个类主要用于处理标记课程实践请求的数据，包含了课程编号、用户 ID、等级和课堂 ID 等信息。通过 Lombok 的注解减少了样板代码，同时使用 Swagger 的注解方便 API 文档生成。 
**/
@Getter
@Setter
@ApiModel
public class MarkLessonPracticeReq implements Serializable {

	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    @NotBlank
    private String lessonId;

    /**
     * 用户id
     *
     * @see String
     */
    @ApiModelProperty("学员id")
    @NotBlank
    private String userId;

    /**
     * 水平
     *
     * @see Integer
     */
    @ApiModelProperty("等级 1=优 2=良 3=中 4=差")
    private Integer level;

    /**
     * 课堂id
     *
     * @see String
     */
    @ApiModelProperty("课堂id")
    @NotBlank
    private String classroomId;

}

