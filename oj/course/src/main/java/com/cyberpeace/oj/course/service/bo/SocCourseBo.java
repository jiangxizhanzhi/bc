package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

@Data
public class SocCourseBo {
    private String caller;
    private String courseName;
    private String courseType;
    private String messageSn;
    private String pathName;
    private String studyType;
    private Integer totalLearningTime;
    private String userId;
}
