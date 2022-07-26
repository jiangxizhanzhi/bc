package com.cyberpeace.oj.course.controller.res;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel
public class ClassroomInfoRes implements Serializable {

	private Date startTime;

	private Date endTime;

	private String courseId;

	private String lessonId;

	private String organizationId;

	private String userIds;

	private String courseTitle;

	private String lessonTitle;
}
