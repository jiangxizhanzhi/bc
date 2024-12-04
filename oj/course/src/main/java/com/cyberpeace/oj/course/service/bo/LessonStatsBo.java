package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@Data
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
