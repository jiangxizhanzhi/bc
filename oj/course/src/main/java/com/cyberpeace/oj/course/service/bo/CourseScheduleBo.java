package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CourseScheduleBo implements Serializable {

    private String resourceId;

    private Date start;

    private Date end;

    private String course;

    private String courseName;

    private Integer courseType;

    private String lesson;

    private String lessonName;

    private String organization;

    private String organizationInfo;

}
