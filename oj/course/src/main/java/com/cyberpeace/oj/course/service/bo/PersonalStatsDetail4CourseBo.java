package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonalStatsDetail4CourseBo implements Serializable {
    @ApiModelProperty("课程resource_id")
    private String resourceId;

    @ApiModelProperty("课程名称")
    private String name;

    @ApiModelProperty("难度")
    private String difficulty;

    @ApiModelProperty("方向名称")
    private String directionName;

    @ApiModelProperty("课时数量")
    private Integer lessonCount;

    @ApiModelProperty("已学习理论课时")
    private Integer learnedTheory;

    @ApiModelProperty("已学习实验课时")
    private Integer learnedExp;

    @ApiModelProperty("已完成课后练习")
    private Integer learnedAfterClassPractice;

    @ApiModelProperty("已提交实验报告")
    private Integer learnedExpReport;
}
