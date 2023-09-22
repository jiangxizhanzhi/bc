package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel("课程学习进度信息")
public class CourseStudyProcessBo {

    @ApiModelProperty("课程编号")
    private String courseId;

    @ApiModelProperty("课程名称")
    private String courseName;

    @ApiModelProperty("课程难度")
    private Integer difficulty;

    @ApiModelProperty("理论课数量")
    private Integer theoryCount = 0;

    @ApiModelProperty("完成理论课数量")
    private Integer completeTheory = 0;

    @ApiModelProperty("实验课数量")
    private Integer exerciseCount = 0;

    @ApiModelProperty("完成实验课数量")
    private Integer completeExercise = 0;

    @ApiModelProperty("最后学习时间")
    private Date lastStudyTime;

    @ApiModelProperty("课时总数量")
    private Integer lessonCount = 0;

    @ApiModelProperty("完成学习数量")
    private Integer completeCount = 0;

    @ApiModelProperty("课程进度")
    private Double process = 0d;

    @ApiModelProperty("完成学习时长")
    private Integer completeDuration = 0;

    @ApiModelProperty("是否具有权限访问")
    private Boolean interview = Boolean.TRUE;

}
