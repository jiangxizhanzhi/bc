package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 课程学习过程 bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课程学习进度信息")
public class CourseStudyProcessBo {

    /**
     * 课程id
     */
    @ApiModelProperty("课程编号")
    private String courseId;

    /**
     * 课程名称
     */
    @ApiModelProperty("课程名称")
    private String courseName;

    /**
     * 困难
     */
    @ApiModelProperty("课程难度")
    private Integer difficulty;

    /**
     * 理论计数
     */
    @ApiModelProperty("理论课数量")
    private Integer theoryCount = 0;

    /**
     * 完成理论
     */
    @ApiModelProperty("完成理论课数量")
    private Integer completeTheory = 0;

    /**
     * 锻炼计数
     */
    @ApiModelProperty("实验课数量")
    private Integer exerciseCount = 0;

    /**
     * 完成锻炼
     */
    @ApiModelProperty("完成实验课数量")
    private Integer completeExercise = 0;

    /**
     * 上次学习时间
     */
    @ApiModelProperty("最后学习时间")
    private Date lastStudyTime;

    /**
     * 课计数
     */
    @ApiModelProperty("课时总数量")
    private Integer lessonCount = 0;

    /**
     * 完成计数
     */
    @ApiModelProperty("完成学习数量")
    private Integer completeCount = 0;

    /**
     * 过程
     */
    @ApiModelProperty("课程进度")
    private Double process = new Double(0);

    /**
     * 完整持续时间
     */
    @ApiModelProperty("完成学习时长")
    private Integer completeDuration = 0;

    /**
     * 面试
     */
    @ApiModelProperty("是否具有权限访问")
    private Boolean interview = Boolean.TRUE;

}
