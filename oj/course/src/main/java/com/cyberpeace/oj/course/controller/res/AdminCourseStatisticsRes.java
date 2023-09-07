package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AdminCourseStatisticsRes implements Serializable {

	@ApiModelProperty("课程资源编号")
	private String resourceId;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("课程分类")
	private String categoryName;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课时数")
	private Integer lessonCount;

	@ApiModelProperty("学习人数")
	private Integer heat;

	@ApiModelProperty("授课人数")
	private Integer lecturee;

	@ApiModelProperty("提交报告数")
	private String finishReportCount;

	@ApiModelProperty("创建者")
	private String createUser;
}
