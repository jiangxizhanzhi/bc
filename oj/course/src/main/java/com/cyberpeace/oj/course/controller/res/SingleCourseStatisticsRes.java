package com.cyberpeace.oj.course.controller.res;

import lombok.Data;

import java.util.List;

@Data
public class SingleCourseStatisticsRes {
    private String resourceId;

    private String studentNums;

    private String courseFinishedNums;

    private String trainTime;

    private CourseTrainTaskRes trainTaskStatistics;

    private TrainGradesRes scoreStatistics;

    private List<SingleCourseUserInfoRes> personStatistics;

}
