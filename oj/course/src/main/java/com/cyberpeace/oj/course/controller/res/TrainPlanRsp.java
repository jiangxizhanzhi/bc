/**
 * 这段代码定义了一个名为 `TrainPlanRsp` 的 Java 类，用于表示训练计划的响应数据。以下是对该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.controller.res` 
 * - **类名**：`TrainPlanRsp` 
 * - **实现接口**：`Serializable`（表示该类的对象可以被序列化） 
 * ### 类注释 
 * - 注释说明了该类的作用是表示训练计划的响应数据。 
 * - 提供了作者和版本信息。 
 * ### 类属性 
 * - `resourceId`：训练计划编号，类型为 `String`。 
 * - `name`：训练计划名称，类型为 `String`。 
 * - `startTime`：训练计划开始时间，类型为 `Date`。 
 * - `endTime`：训练计划结束时间，类型为 `Date`。 
 * - `updateTime`：训练计划更新时间，类型为 `Date`。 
 * - `count`：数量，类型为 `Integer`。 
 * - `createUserId`：创建人ID，类型为 `String`。 
 * - `createUser`：创建人名称，类型为 `String`。 
 * ### 属性注释 
 * 每个属性都有详细的注释，描述了其用途和相关类型。 
 * ### Getter 和 Setter 方法 
 * - 使用 Lombok 的 `@Getter` 和 `@Setter` 注解自动生成了所有属性的 getter 和 setter 方法。 
 * ### Swagger 注解 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个属性在 API 文档中的显示方式。 
 * ### 总结 
 * - 该类主要用于封装训练计划的相关信息，并且通过 Swagger 注解使其易于在 API 文档中展示。 
 * - 类的设计简洁明了，提供了必要的属性和方法来表示训练计划的响应数据。 
**/
package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 训练计划响应
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class TrainPlanRsp implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("训练计划编号")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("训练计划名称")
    private String name;

    /**
     * 开始时间
     *
     * @see Date
     */
    @ApiModelProperty("开始时间")
    private Date startTime;

    /**
     * 结束时间
     *
     * @see Date
     */
    @ApiModelProperty("结束时间")
    private Date endTime;

    /**
     * 更新时间
     *
     * @see Date
     */
    @ApiModelProperty("更新时间")
    private Date updateTime;

    /**
     * 计数
     *
     * @see Integer
     */
    @ApiModelProperty("数量")
    private Integer count;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建人id")
    private String createUserId;

    /**
     * 创建用户
     *
     * @see String
     */
    @ApiModelProperty("创建人名称")
    private String createUser;
}
