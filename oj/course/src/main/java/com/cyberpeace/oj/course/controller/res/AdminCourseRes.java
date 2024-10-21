package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AdminCourseRes implements Serializable {

	@ApiModelProperty("课程资源编号")
	private String resourceId;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("课程简介")
	private String description;

	@ApiModelProperty("课程封面")
	private String logo;

	@ApiModelProperty("学分")
	private Integer score;

	@ApiModelProperty("课程分类")
	private String categoryId;

	@ApiModelProperty("课程大纲")
	private String outline;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课程类型")
	private Integer courseType;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("课程讲师")
	private String lecturer;

	@ApiModelProperty("课程编号")
	private String courseNum;

	@ApiModelProperty("岗位")
	private Integer role;
}
