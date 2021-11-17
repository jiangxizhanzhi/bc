package com.cyberpeace.oj.course.service.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ClassroomBo implements Serializable {

    private String resourceId;

    private Date startTime;

    private Date endTime;

    private String courseId;

    private String courseName;

    private Integer courseType;

    private String lessonId;

    private String lessonName;

    private Integer lessonType;

    private String organizationId;

    private String organizationIds;

}
