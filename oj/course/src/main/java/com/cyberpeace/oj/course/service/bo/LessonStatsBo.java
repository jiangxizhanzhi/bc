package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 课程统计 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `LessonStatsBo` 的 Java 类，用于表示课程统计信息。以下是该类的简要分析： 
 * ### 类定义 
 * - **包名**：`com.cyberpeace.oj.course.service.bo` 
 * - **实现接口**：`Serializable`，这意味着这个类的对象可以序列化，便于在网络上传输或持久化存储。 
 * ### 类注释 
 * - 文档说明了这是一个课程统计的业务对象（BO, Business Object），由作者 SN 创建，版本为 1.0.0，创建日期为 2024 年 11 月 8 日。 
 * ### 属性 
 * - **资源 ID** (`resourceId`)：表示课时的资源 ID。 
 * - **课程 ID** (`courseId`)：表示课程的资源 ID。 
 * - **名称** (`name`)：表示课时的名称。 
 * - **类型** (`type`)：表示课时的类型。 
 * - **难度** (`difficulty`)：表示课时的难度。 
 * - **学习计数** (`learningCount`)：表示学习这门课时的人数。 
 * - **报告计数** (`reportCount`)：表示提交报告的数量。 
 * - **完成实践计数** (`finishPracticeCount`)：表示完成练习的数量。 
 * ### 注解 
 * - 使用了 Lombok 库的 `@Getter` 和 `@Setter` 注解来自动生成属性的 getter 和 setter 方法。 
 * - 使用了 Swagger 注解 `@ApiModelProperty` 来描述每个属性的作用，方便 API 文档生成工具解析。 
 * ### 总结 
 * `LessonStatsBo` 类是一个数据传输对象（DTO），用于封装课程相关的统计信息。通过使用 Lombok 和 Swagger 注解，简化了代码的编写并提高了可读性和文档生成的便利性。这个类主要用于在系统内部或与外部系统通信时传递课程统计信息。 
**/
@Getter
@Setter
public class LessonStatsBo implements Serializable {
    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课时resource_id")
    private String resourceId;

    /**
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程resource_id")
    private String courseId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课时名称")
    private String name;

    /**
     * 类型
     *
     * @see Integer
     */
    @ApiModelProperty("课时类型")
    private Integer type;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课时难度")
    private Integer difficulty;

    /**
     * 学习计数
     *
     * @see Integer
     */
    @ApiModelProperty("学习人数")
    private Integer learningCount;

    /**
     * 报告计数
     *
     * @see Integer
     */
    @ApiModelProperty("提交报告数量")
    private Integer reportCount;

    /**
     * 完成实践计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成练习数")
    private Integer finishPracticeCount;

}

