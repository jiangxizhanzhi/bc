package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("课时列表查询")
public class ListLessonReq extends PageReqBase {

	@ApiModelProperty("课时类型 0=理论课 1=实验课 用于课程下课时列表筛选")
	private Integer type;

	@ApiModelProperty(name = "course_id", value = "课程编号")
	private String courseId;

	@ApiModelProperty(name = "except_course", value = "要排除的课程编号 用于课时导入筛选")
	private String exceptCourse;

	@ApiModelProperty(name = "category_id", value = "课程方向筛选条件 用于课时导入筛选")
	private String categoryId;

	public void setCourse_id(String courseId) {
		this.courseId = courseId;
	}

	public void setExcept_course(String exceptCourse) {
		this.exceptCourse = exceptCourse;
	}

	public void setCategory_id(String categoryId) {
		this.categoryId = categoryId;
	}
}
