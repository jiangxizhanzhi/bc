package com.cyberpeace.oj.course.controller.res;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class UserCourseProcessRes {
    private String courseName;
    private String courseNo;
    private Date startTime;
    private Date finishedTime;
    private Integer progress;
    private Integer learnedLessons;
    private Integer learnTime;
}
