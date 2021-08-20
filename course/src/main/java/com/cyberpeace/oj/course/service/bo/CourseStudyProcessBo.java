package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.util.Date;

@Data
public class CourseStudyProcessBo {

    private String courseId;

    private String courseName;

    private Integer theoryCount;

    private Integer completeTheory;

    private Integer exerciseCount;

    private Integer completeExercise;

    private Date lastStudyTime;

    private Integer lessonCount;

    private Integer completeCount;

    private Integer process;

    private Integer suggestDuration;

    private Integer completeDuration;

}
