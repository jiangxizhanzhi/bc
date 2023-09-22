package com.cyberpeace.rtks.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RelatedCourse implements Serializable {

	@ApiModelProperty("课程编号")
	private String resourceId;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("课程权重")
	private Integer weight;

	@ApiModelProperty("课程简介")
	private String description;

	@ApiModelProperty("之前使用的课程方向")
	private String direction;

	@ApiModelProperty("课程方向")
	private String categoryId;

	@ApiModelProperty("课程海报")
	private String logo;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课程创建人")
	private String createUser;

	@ApiModelProperty("课程最后编辑人")
	private String lastEditUser;

	@ApiModelProperty("课程类型")
	private Integer eBook;

	@ApiModelProperty("课程热度")
	private Integer heat;

	@ApiModelProperty("课程讲师")
	private String lecturer;

	@ApiModelProperty("课程成绩")
	private Integer score;

	@ApiModelProperty("课程编号")
	private String courseNum;

	@ApiModelProperty("课程方向名称")
	private String directionName;

	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	@ApiModelProperty("课程课时时长")
	private Integer courseDuration = 0;

	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration = 0;

	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;
}