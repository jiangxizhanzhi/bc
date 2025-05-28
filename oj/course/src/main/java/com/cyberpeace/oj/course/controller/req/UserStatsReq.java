/**
 * 这段代码定义了一个名为 `UserStatsReq` 的 Java 类，它是用于处理用户统计数据请求的。以下是对该代码的简要分析： 
 * ### 类定义 
 * - **包声明**：`com.cyberpeace.oj.course.controller.req` 
 * - **类名**：`UserStatsReq` 
 * - **继承关系**：该类继承自 `PageReqBase` 类。 
 * ### 注解 
 * - 使用了 `@Getter` 和 `@Setter` 注解，表明这个类的所有属性都有对应的 getter 和 setter 方法。 
 * - 使用了 `@ApiModelProperty` 注解，这是 Swagger 文档生成工具的一部分，用于描述 API 接口中的参数。 
 * ### 属性 
 * - **organization**：表示组织名称，用于查询特定组织的统计数据。 
 * - **startTime**：表示查询的起始时间。 
 * - **endTime**：表示查询的结束时间。 
 * - **pattern**：表示模糊查询的筛选参数，用于更灵活的查询条件。 
 * ### 总结 
 * - **用途**：`UserStatsReq` 是一个用于处理用户统计数据请求的请求体类，主要用于接收前端传递的查询条件。 
 * - **功能**：该类包含了一些基本的查询条件（如组织、时间范围和模式），这些条件可以用来过滤和统计用户数据。 
 * - **继承**：通过继承 `PageReqBase` 类，该类可能还包含了分页相关的属性和方法。 
 * 总体来看，这是一个简单的数据传输对象（DTO）类，用于封装前端传来的查询条件，以便后端进行相应的数据处理和统计。 
**/
package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户统计请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PageReqBase
 * @since 1.0.0
 */
@Getter
@Setter
public class UserStatsReq extends PageReqBase {
    /**
     * 组织
     *
     * @see String
     */
    @ApiModelProperty("组织查询")
    private String organization;

    /**
     * 开始时间
     *
     * @see String
     */
    @ApiModelProperty(name = "startTime", value = "开始日期")
    private String startTime;

    /**
     * 结束时间
     *
     * @see String
     */
    @ApiModelProperty(name = "endTime", value = "结束日期")
    private String endTime;

    /**
     * 模式
     *
     * @see String
     */
    @ApiModelProperty(value = "模糊查询筛选参数(不会转义)")
    private String pattern;
}
