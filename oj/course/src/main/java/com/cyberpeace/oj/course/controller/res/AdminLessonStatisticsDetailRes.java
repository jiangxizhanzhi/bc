package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 管理课统计学详情结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonStatisticsDetailRes` 的 Java 类，用于表示管理员在查看课程统计详情时所需的数据模型。这个类主要包含了以下几个方面的信息： 
 * 1. **包声明与导入**： 
 * - 包声明为 `com.cyberpeace.oj.course.controller.res`。 
 * - 导入了 `Serializable` 接口、`Date` 类以及 Lombok 的 `Getter` 和 `Setter` 注解，还有 Swagger 的注解 `ApiModelProperty`。 
 * 2. **类与字段**： 
 * - 类 `AdminLessonStatisticsDetailRes` 实现了 `Serializable` 接口，这意味着该类的对象可以序列化以便在网络上传输或持久化到存储介质中。 
 * - 类中的字段包括 `resourceId`（课程资源编号）、`id`（唯一标识符）、`score`（得分）、`reportUpdateTime`（报告更新时间）、`markingTime`（评分时间）、`practiceCount`（实践计数）、`organizationName`（组织名称）、`isReport`（是否提交报告）、`createUser`（创建人）、`record`（答题记录）以及 `userName` 和 `name`（用户相关的信息）。 
 * 3. **注解**： 
 * - 使用了 Lombok 的 `@Getter` 和 `@Setter` 注解来自动生成字段的 getter 和 setter 方法，减少了样板代码。 
 * - 使用了 Swagger 的 `@ApiModelProperty` 注解来描述每个字段的用途，这有助于生成 API 文档，并且便于客户端理解这些数据的意义。 
 * 4. **文档**： 
 * - 类和字段都有 Javadoc 文档注释，描述了它们的功能和用途。 
 * 总体来说，这是一个简洁而功能明确的数据传输对象（DTO），用于封装课程统计相关的详细信息，方便在系统内部或外部进行数据交换。 
**/
@Getter
@Setter
public class AdminLessonStatisticsDetailRes implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课程资源编号")
    private String resourceId;

    /**
     * id
     *
     * @see String
     */
    @ApiModelProperty("id")
    private String id;

    /**
     * 得分
     *
     * @see Integer
     */
    @ApiModelProperty("实验评分")
    private Integer score;

    /**
     * 报告更新时间
     *
     * @see Date
     */
    @ApiModelProperty("报告更新时间")
    private Date reportUpdateTime;

    /**
     * 标记时间
     *
     * @see Date
     */
    @ApiModelProperty("评分时间")
    private Date markingTime;

    /**
     * 实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成练习")
    private Integer practiceCount;

    /**
     * 组织名称
     *
     * @see String
     */
    @ApiModelProperty("组织")
    private String organizationName;

    /**
     * 是举报
     *
     * @see Boolean
     */
    @ApiModelProperty("是否提交报告")
    private Boolean isReport;

    /**
     * 创建用户
     *
     * @see String
     */
    @ApiModelProperty("创建人")
    private String createUser;

    /**
     * 记录
     *
     * @see String
     */
    @ApiModelProperty("答题记录")
    private String record;

    /**
     * 用户名称
     *
     * @see String
     */
    @ApiModelProperty("用户名")
    private String userName;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("姓名")
    private String name;

}

