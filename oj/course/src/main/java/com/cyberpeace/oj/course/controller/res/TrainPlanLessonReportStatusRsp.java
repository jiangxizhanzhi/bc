package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 实验报告状态
 */
@Data
public class TrainPlanLessonReportStatusRsp implements Serializable {


    @ApiModelProperty("课时id")
    private String lessonId;

    @ApiModelProperty("实验报告分数")
    private Integer reportScore;

    /**
     * 实验报告状态 1- 待提交， 2- 已提交 ， 3-已批改, 无状态表示没有课后练习或者未下发
     * @see com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum
     */
    @ApiModelProperty("实验报告状态 1- 待提交， 2- 已提交 ， 3-已批改")
    private Boolean reportStatus;

    @ApiModelProperty("实验报告通过状态 0- 未通过， 1-  已通过")
    private Boolean reportPassedStatus;

}
