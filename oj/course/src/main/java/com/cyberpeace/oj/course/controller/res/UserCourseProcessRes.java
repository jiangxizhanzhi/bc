package com.cyberpeace.oj.course.controller.res;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserCourseProcessRes {
    private String courseName;
    private String courseNo;
    private LocalDateTime startTime;
    private LocalDateTime finishedTime;
    private Integer progress;
    private Integer learnedLessons;
    private String learnTime;
}
