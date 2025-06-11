package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 课后练习状态
 */
@Data
public class TrainPlanLessonExerciseStatusRsp implements Serializable {


    @ApiModelProperty("课时id")
    private String lessonId;

    /**
     * 课后练习等级， 1- 优、2 -良、 3-中、4-差
     * @see com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate.LevelType
     */
    @ApiModelProperty("课后练习等级， 1- 优、2 -良、 3-中、4-差")
    private Integer level;

    /**
     * 课后练习状态 1- 待提交， 2- 已提交 ， 3-已批改, 无状态表示没有课后练习或者未下发
     * @see com.cyberpeace.oj.ojbase.constant.ExperimentReportEnum
     */
    @ApiModelProperty("课后练习状态 1- 待提交， 2- 已提交 ， 3-已批改")
    private Boolean levelStatus;


}
