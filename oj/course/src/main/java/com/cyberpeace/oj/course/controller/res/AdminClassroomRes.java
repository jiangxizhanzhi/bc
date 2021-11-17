package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel()
public class AdminClassroomRes implements Serializable {
    @ApiModelProperty("课程表编号")
    private String resourceId;

    private Date startTime;

    private Date endTime;

    private String courseId;

    private String courseName;

    private String lessonId;

    private String organizationId;

    private String organizationIds;


}
