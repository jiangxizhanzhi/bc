/**
 * 这段代码定义了一个名为 `LessonStatsReq` 的 Java 类，用于表示课程统计请求的数据模型。以下是该类的详细分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.req` 
 * - **继承关系**：`LessonStatsReq` 继承自 `PageReqBase` 
 * ### 注解 
 * - **Lombok 注解**： 
 * - `@Getter`：为所有字段生成 getter 方法。 
 * - `@Setter`：为所有字段生成 setter 方法。 
 * - **Swagger 注解**： 
 * - `@ApiModel`：标记这个类为一个 API 模型。 
 * - `@ApiModelProperty`：为字段提供描述信息。 
 * ### 字段 
 * - **course_id** 
 * - 类型：`String` 
 * - 注解： 
 * - `@NotNull`：表示该字段不能为空。 
 * - `@ApiModelProperty`：描述字段为“课程id”。 
 * - **type** 
 * - 类型：`Integer` 
 * - 注解： 
 * - `@ApiModelProperty`：描述字段为“课时类型”。 
 * ### 总结 
 * - `LessonStatsReq` 是一个用于课程统计请求的数据模型。 
 * - 它包含两个字段：`course_id` 和 `type`。 
 * - `course_id` 必须提供且不能为 null。 
 * - 这个类使用了 Lombok 和 Swagger 注解来简化代码并提供文档注释。 
 * 通过这些分析，可以清楚地了解 `LessonStatsReq` 类的功能及其在系统中的作用。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * 课统计请求体
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
public class LessonStatsReq extends PageReqBase {
    /**
     * 课程id
     *
     * @see String
     */
    @NotNull
    @ApiModelProperty("课程id")
    private String course_id;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("课时类型")
    private Integer type;

}
