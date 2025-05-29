package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 课程学习过程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `CourseStudyProcessBo` 的 Java 类，用于表示课程学习过程中的相关信息。以下是该类的详细分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.oj.course.service.bo; 
 * ``` 
 * 这个包声明表明该类位于 `com.cyberpeace.oj.course.service.bo` 包中。 
 * ### 导入的类 
 * ```java 
 * import io.swagger.annotations.ApiModel; 
 * import io.swagger.annotations.ApiModelProperty; 
 * import lombok.Getter; 
 * import lombok.Setter; 
 * ``` 
 * - `io.swagger.annotations.ApiModel` 和 `io.swagger.annotations.ApiModelProperty`：这两个注解用于生成 Swagger 文档，帮助 API 文档的自动生成。 
 * - `lombok.Getter` 和 `lombok.Setter`：Lombok 注解，用于自动生成 getter 和 setter 方法。 
 * ### 类声明 
 * ```java 
 * @Getter 
 * @Setter 
 * @ApiModel("课程学习进度信息") 
 * public class CourseStudyProcessBo { 
 * ``` 
 * - `@Getter` 和 `@Setter`：Lombok 注解，为所有字段自动生成 getter 和 setter 方法。 
 * - `@ApiModel("课程学习进度信息")`：Swagger 注解，用于描述该对象的整体模型。 
 * ### 字段 
 * 类中定义了多个字段，每个字段都有相应的注释和 Swagger 注解，以便在 Swagger 文档中显示这些字段的信息。 
 * #### 主要字段： 
 * - `courseId`：课程编号。 
 * - `courseName`：课程名称。 
 * - `difficulty`：课程难度。 
 * - `theoryCount`：理论课数量（默认值为 0）。 
 * - `completeTheory`：完成理论课数量（默认值为 0）。 
 * - `exerciseCount`：实验课数量（默认值为 0）。 
 * - `completeExercise`：完成实验课数量（默认值为 0）。 
 * - `lastStudyTime`：上次学习时间。 
 * - `lessonCount`：课时总数量（默认值为 0）。 
 * - `completeCount`：完成学习数量（默认值为 0）。 
 * - `process`：课程进度（默认值为 0）。 
 * - `completeDuration`：完成学习时长（默认值为 0）。 
 * - `interview`：是否具有权限访问（默认值为 `true`）。 
 * ### 总结 
 * - `CourseStudyProcessBo` 类主要用于表示课程学习过程中的一些关键信息。 
 * - 通过 Lombok 注解，简化了 getter 和 setter 方法的编写。 
 * - 使用 Swagger 注解，便于生成 API 文档。 
 * - 各个字段描述了课程学习过程中的各种状态和属性，包括课程的基本信息、学习进度等。 
 * 这个类可以作为 API 返回的数据传输对象（DTO），用于向客户端提供关于课程学习过程的相关信息。 
**/
@Getter
@Setter
@ApiModel("课程学习进度信息")
public class CourseStudyProcessBo {

    /**
     * 课程id
     *
     * @see String
     */
    @ApiModelProperty("课程编号")
    private String courseId;

    /**
     * 课程名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课程难度")
    private Integer difficulty;

    /**
     * 理论计数
     *
     * @see Integer
     */
    @ApiModelProperty("理论课数量")
    private Integer theoryCount = 0;

    /**
     * 完成理论
     *
     * @see Integer
     */
    @ApiModelProperty("完成理论课数量")
    private Integer completeTheory = 0;

    /**
     * 锻炼计数
     *
     * @see Integer
     */
    @ApiModelProperty("实验课数量")
    private Integer exerciseCount = 0;

    /**
     * 完成锻炼
     *
     * @see Integer
     */
    @ApiModelProperty("完成实验课数量")
    private Integer completeExercise = 0;

    /**
     * 上次学习时间
     *
     * @see Date
     */
    @ApiModelProperty("最后学习时间")
    private Date lastStudyTime;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课时总数量")
    private Integer lessonCount = 0;

    /**
     * 完成计数
     *
     * @see Integer
     */
    @ApiModelProperty("完成学习数量")
    private Integer completeCount = 0;

    /**
     * 过程
     *
     * @see Double
     */
    @ApiModelProperty("课程进度")
    private Double process = new Double(0);

    /**
     * 完整持续时间
     *
     * @see Integer
     */
    @ApiModelProperty("完成学习时长")
    private Integer completeDuration = 0;

    /**
     * 面试
     *
     * @see Boolean
     */
    @ApiModelProperty("是否具有权限访问")
    private Boolean interview = Boolean.TRUE;

}

