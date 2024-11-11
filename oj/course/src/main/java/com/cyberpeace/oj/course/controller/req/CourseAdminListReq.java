package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 课程管理列表请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("后台参数接收")
public class CourseAdminListReq extends CourseListReq {

	/**
	 * 我课程
	 */
	@ApiModelProperty(name = "my_course", value = "我的课程")
	private Boolean myCourse;

	/**
	 * 系统课程
	 */
	@ApiModelProperty(name = "system_course", value = "系统课程")
	private Boolean systemCourse;

	/**
	 * 分享课程
	 */
	@ApiModelProperty(name = "share_course", value = "共享课程")
	private Boolean shareCourse;

	public void setMy_course(Boolean myCourse) {
		this.myCourse = myCourse;
	}

	public void setSystem_course(Boolean systemCourse) {
		this.systemCourse = systemCourse;
	}

	public void setShare_course(Boolean shareCourse) {
		this.shareCourse = shareCourse;
	}
}
