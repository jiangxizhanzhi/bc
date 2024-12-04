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
public class CourseStatsBo implements Serializable {

    /**
     * 资源id
     *
     * @see String
     */
    @ApiModelProperty("课程resource_id")
    private String resourceId;

    /**
     * 名称
     *
     * @see String
     */
    @ApiModelProperty("课程名称")
    private String name;

    /**
     * 方向名称
     *
     * @see String
     */
    @ApiModelProperty("方向名称")
    private String directionName;

    /**
     * 难度
     *
     * @see Integer
     */
    @ApiModelProperty("课程难度")
    private Integer difficulty;

    /**
     * 报告计数
     *
     * @see Integer
     */
    @ApiModelProperty("报告数量")
    private Integer reportCount;

    /**
     * 课计数
     *
     * @see Integer
     */
    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    /**
     * 学习计数
     *
     * @see Integer
     */
    @ApiModelProperty("学习人数")
    private Integer learningCount;

    /**
     * 创造用户id
     *
     * @see String
     */
    @ApiModelProperty("创建用户resource_id")
    private String createUserId;

    /**
     * 创建用户名
     *
     * @see String
     */
    @ApiModelProperty("创建用户姓名")
    private String createUsername;
}
