package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 个人统计详情4计数博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `PersonalStatsDetail4CountBo` 的 Java 类，该类实现了 `Serializable` 接口。这个类主要用于表示用户的个人统计数据，特别是关于用户在某个在线课程平台上的各种课程和活动的完成情况。 
 * ### 主要特点 
 * - **包声明**：该类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * - **注解**：使用了 Lombok 注解 `@Getter` 和 `@Setter` 来自动生成属性的 getter 和 setter 方法。 
 * - **Swagger 注解**：使用了 Swagger 注解 `@ApiModelProperty` 来描述每个字段的用途，便于生成 API 文档。 
 * ### 属性 
 * - `courseTotalCount`: 课程总数。 
 * - `theoryCourseTotalCount`: 理论课程的总数。 
 * - `expCourseTotalCount`: 实验课程的总数。 
 * - `afterClassPracticeTotalCount`: 课后练习的总数。 
 * - `finishCoursesCount`: 完成的课程数量。 
 * - `finishTheoryCount`: 完成的理论课程数量。 
 * - `finishExpCount`: 完成的实验课程数量。 
 * - `afterClassPractice`: 完成的课后练习数量。 
 * ### 总结 
 * - **功能**: 这个类的主要目的是存储和表示用户在某个在线课程平台上的各种统计数据。 
 * - **设计**: 使用了 Lombok 和 Swagger 注解来简化代码和文档生成。 
 * - **用途**: 可能用于显示用户的个人学习进度或统计报告。 
 * ### 适用场景 
 * - 在一个在线教育平台上，可以用来展示用户的课程完成情况。 
 * - 通过这些统计数据，可以帮助用户了解自己的学习进度，并提供个性化的学习建议。 
 * 总的来说，这是一个非常实用且易于扩展的类，适用于需要跟踪和展示用户学习进度的在线教育平台。 
**/
@Getter
@Setter
public class PersonalStatsDetail4CountBo implements Serializable {
    /**
     * 课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("课程总数")
    private Integer courseTotalCount = 0;

    /**
     * 理论课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成理论课程数量")
    private Integer theoryCourseTotalCount = 0;

    /**
     * exp （英文）课程总计数
     *
     * @see Integer
     */
    @ApiModelProperty("实验课程数量")
    private Integer expCourseTotalCount = 0;

    /**
     * 后类实践总计数
     *
     * @see Integer
     */
    @ApiModelProperty("课后练习数量")
    private Integer afterClassPracticeTotalCount = 0;

    /**
     * 完成课程计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成课程数量")
    private Integer finishCoursesCount = 0;

    /**
     * 完成理论计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成理论课程数量")
    private Integer finishTheoryCount = 0;

    /**
     * 完成exp （英文）计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成实验课程数量")
    private Integer finishExpCount = 0;

    /**
     * 课后练习
     *
     * @see Integer
     */
    @ApiModelProperty("完成课后练习数量")
    private Integer afterClassPractice = 0;

}

