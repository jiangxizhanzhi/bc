package com.cyberpeace.oj.course.controller.req;


import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课程统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseStatsReq` 的 Java 类，用于表示课程统计请求的参数。以下是该类的简要分析： 
 * ### 类定义 
 * - **包名**: `com.cyberpeace.oj.course.controller.req` 
 * - **类名**: `CourseStatsReq` 
 * - **继承关系**: 继承自 `PageReqBase` 
 * ### 类注释 
 * - 该类用于表示课程统计请求体。 
 * - 作者为 SN，版本为 1.0.0，日期为 2024年11月8日。 
 * ### 注解 
 * - `@Getter`: 自动生成所有字段的 getter 方法。 
 * - `@Setter`: 自动生成所有字段的 setter 方法。 
 * - `@ApiModel`: 用于 Swagger 文档，描述这个类是一个模型。 
 * - `@ApiModelProperty`: 用于 Swagger 文档，描述字段的含义。 
 * ### 字段 
 * 1. **direction** 
 * - 类型: `String` 
 * - 注释: 表示课程的方向。 
 * - Swagger 注解: 描述字段为“方向”。 
 * 2. **difficult** 
 * - 类型: `Integer` 
 * - 注释: 表示课程的难度。 
 * - Swagger 注解: 描述字段为“难度”。 
 * ### 总结 
 * - `CourseStatsReq` 类主要用于封装课程统计请求的参数，包括方向和难度。 
 * - 通过继承 `PageReqBase` 类，可能还包含分页相关的参数。 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解简化了代码，避免手动编写 getter 和 setter 方法。 
 * - 通过 Swagger 注解，方便生成 API 文档。 
**/
@Getter
@Setter
@ApiModel
public class CourseStatsReq extends PageReqBase {
    /**
     * 方向
     *
     * @see String
     */
    @ApiModelProperty("方向")
    private String direction;

    /**
     * 难
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficult;

}

