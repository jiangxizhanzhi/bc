package com.cyberpeace.oj.course.dao.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see BaseTable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `Classroom` 的 Java 类，该类继承自 `BaseTable`。这个类表示一个课堂实体，并且使用了 MyBatis-Plus 和 Lombok 库来简化开发。以下是对代码的简要分析： 
 * ### 主要内容 
 * 1. **包声明和导入**： 
 * - 这个类位于 `com.cyberpeace.oj.course.dao.entity` 包中。 
 * - 导入了必要的库，如 `Date`、MyBatis-Plus 的注解（如 `@TableName` 和 `@TableId`），以及 Lombok 的注解（如 `@Getter` 和 `@Setter`）。 
 * 2. **类声明**： 
 * - 类名为 `Classroom`，并使用了 `@TableName` 注解指定了对应的数据库表名为 `oj_classroom`。 
 * - 类使用了 `@Getter` 和 `@Setter` 注解，使得所有属性都有自动生成的 getter 和 setter 方法。 
 * 3. **属性**： 
 * - `resourceId`：资源ID，使用 `@TableId` 注解指定为主键类型为 UUID。 
 * - `startTime` 和 `endTime`：课堂的开始时间和结束时间，类型为 `Date`。 
 * - `courseId`：课程ID。 
 * - `lessonId`：课时ID。 
 * - `organizationId` 和 `organizationIds`：组织ID和组织ID列表。 
 * - `createUserId`：创建者的用户ID。 
 * - `trainPlanId`：训练计划ID。 
 * 4. **文档注释**： 
 * - 每个属性都有详细的 Javadoc 注释，描述了其用途和类型。 
 * - 类也有 Javadoc 注释，说明了类的作用和作者信息。 
 * ### 总结 
 * - **功能**：`Classroom` 类表示一个课堂实体，并且包含了与课堂相关的各种属性。 
 * - **技术栈**：使用了 MyBatis-Plus 来简化数据库操作，Lombok 来减少样板代码。 
 * - **数据模型**：该类对应于数据库中的 `oj_classroom` 表，并且定义了表中各个字段的映射关系。 
 * 这段代码是一个典型的用于数据持久化的 Java 实体类，适用于 Spring Boot 项目中。 
**/
@Getter
@Setter
@TableName("oj_classroom")
public class Classroom extends BaseTable {

    /**
     * 资源id
     *
     * @see String
     */
    @TableId(type = IdType.UUID, value = "resource_id")
    @ApiModelProperty("课程表编号")
    private String resourceId;

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
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
    private String courseId;

    /**
     * 课id
     *
     * @see String
     */
    @ApiModelProperty("课时编号")
    private String lessonId;

    /**
     * 组织id
     *
     * @see String
     */
    @ApiModelProperty("组织编号")
    private String organizationId;

    /**
     * 组织id列表
     *
     * @see String
     */
    @ApiModelProperty("子组织编号")
    private String organizationIds;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建者编号")
    private String createUserId;

    /**
     * 训练计划id
     *
     * @see String
     */
    @ApiModelProperty("训练计划编号")
    private String trainPlanId;

}

