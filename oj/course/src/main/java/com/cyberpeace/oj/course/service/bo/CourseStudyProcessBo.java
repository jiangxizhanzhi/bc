package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 课程学习过程 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Data
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
