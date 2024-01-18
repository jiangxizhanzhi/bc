package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleStudyBo implements Serializable {

	@ApiModelProperty("总课程数")
	private int totalCourses;

	@ApiModelProperty("完成课程数")
	private int finishedCourses;

	@ApiModelProperty("课程进度")
	private int courseProgress;
}