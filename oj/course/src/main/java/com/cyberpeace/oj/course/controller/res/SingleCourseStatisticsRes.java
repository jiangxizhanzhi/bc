package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class SingleCourseStatisticsRes {
    @ApiModelProperty("课程resourceId")
    private String resourceId;

    @ApiModelProperty("课程参与人数，组成每个课程的实验的人数")
    private Integer studentNums;

    @ApiModelProperty("课程完成人数, 组成每个课程的实验、科目、对抗演训完成人数")
    private Integer courseFinishedNums;

    @ApiModelProperty("组成课程的实验、科目、对抗演训训练总时长")
    private Integer trainTime;


    private CourseTrainTaskRes trainTaskStatistics;

    private TrainGradesRes scoreStatistics;

    private List<SingleCourseUserInfoRes> personStatistics;
}
