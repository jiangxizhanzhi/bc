package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

@Data
public class CourseStudyProcessBo {

    private String courseId;

    private String courseName;

    private Integer theoryCount;

    private Integer exerciseCount;

    private String lastStudyTime;

    private Integer lessonCount;

    private Integer process;

    private Integer suggestDuration;

}
