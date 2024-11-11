package com.cyberpeace.oj.course.controller.req;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 列表课请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel("课时列表查询")
public class ListLessonReq extends PageReqBase {

	/**
	 * 类型
	 */
	@ApiModelProperty("课时类型 0=理论课 1=实验课 用于课程下课时列表筛选")
	private Integer type;

	/**
	 * 课程id
	 */
	@ApiModelProperty(name = "course_id", value = "课程编号")
	private String courseId;

	/**
	 * 除了课程
	 */
	@ApiModelProperty(name = "except_course", value = "要排除的课程编号 用于课时导入筛选")
	private String exceptCourse;

	/**
	 * 类别id
	 */
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
