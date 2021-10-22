package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("后台参数接收")
public class CourseAdminListReq extends CourseListReq {

	@ApiModelProperty("我的课程")
	private Boolean myCourse = true;

	@ApiModelProperty("系统课程")
	private Boolean systemCourse = true;

	@ApiModelProperty("共享课程")
	private Boolean shareCourse = true;

}
