package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 管理员课程练习 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `AdminLessonPracticeBo` 的 Java 类，用于表示管理员课程练习的相关信息。该类实现了 `Serializable` 接口，表明它可以被序列化以便在网络上传输或持久化存储。 
 * ### 主要特点： 
 * 1. **Lombok 注解**：使用了 `@Getter` 和 `@Setter` 注解，自动生成属性的 getter 和 setter 方法。 
 * 2. **Swagger 注解**：使用了 `@ApiModelProperty` 注解来描述每个属性的用途，便于生成 API 文档。 
 * ### 属性说明： 
 * - `answer`：答案字符串。 
 * - `title`：题目标题。 
 * - `content`：题目内容。 
 * - `option`：选择项。 
 * - `directionName`：方向名称。 
 * - `practiceType`：习题类型（整数）。 
 * - `questionType`：题型（整数）。 
 * - `difficulty`：难度（整数）。 
 * - `resourceId`：资源编号。 
 * - `refId`：快照编号。 
 * - `score`：分值（双精度浮点数）。 
 * ### 总结： 
 * `AdminLessonPracticeBo` 类是一个数据传输对象（DTO），用于在系统中传递与管理员课程练习相关的数据。该类通过 Lombok 和 Swagger 注解简化了代码，并且易于理解和维护。 
**/
@Getter
@Setter
public class AdminLessonPracticeBo implements Serializable {

    /**
     * 答案
     *
     * @see String
     */
    @ApiModelProperty("答案")
    private String answer;

    /**
     * 标题
     *
     * @see String
     */
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     *
     * @see String
     */
    @ApiModelProperty("内容")
    private String content;

    /**
     * 选择
     *
     * @see String
     */
    @ApiModelProperty("选项")
    private String option;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 执业类型
     *
     * @see Integer
     */
    @ApiModelProperty("习题类型")
    private Integer practiceType;

    /**
     * 问题类型
     *
     * @see Integer
     */
    @ApiModelProperty("题型")
    private Integer questionType;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("难度")
    private Integer difficulty;

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("资源编号")
    private String resourceId;

    /**
     * 裁判id
     *
     * @see String
     */
    @ApiModelProperty("快照编号")
    private String refId;

    /**
     * 得分
     *
     * @see Double
     */
    @ApiModelProperty("分值")
    private Double score;

}

