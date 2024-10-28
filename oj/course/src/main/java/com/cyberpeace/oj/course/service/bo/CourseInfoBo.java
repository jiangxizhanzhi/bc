package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("课程基本信息")
public class CourseInfoBo implements Serializable {

	public final static Integer DEFAULT_WEIGHT = 1;

	@ApiModelProperty("课程编号")
	private String resourceId;

	@ApiModelProperty("课程名称")
	private String name;

	@ApiModelProperty("是否公开")
	private Boolean isPublic;

	@ApiModelProperty("课程简介")
	private String description;

	@ApiModelProperty("课程方向")
	private String categoryId;

	@ApiModelProperty("课程海报")
	private String logo;

	@ApiModelProperty("课程大纲")
	private String outline;

	@ApiModelProperty("课程难度")
	private Integer difficulty;

	@ApiModelProperty("课程创建人")
	private String createUserId;

	@ApiModelProperty("课程最后编辑人")
	private String modifyUserId;

	@ApiModelProperty("课程类型 0=")
	private Integer courseType;

	@ApiModelProperty("课程热度")
	private Integer heat;

	@ApiModelProperty("课程讲师")
	private String lecturer;

	@ApiModelProperty("课程学分")
	private Integer score;

	@ApiModelProperty("课程编号")
	private String courseNum;

	@ApiModelProperty("课程方向名称")
	private String directionName;

	@ApiModelProperty("课程课时数量")
	private Integer lessonCount;

	@ApiModelProperty("课程课时时长")
	private Integer courseDuration;

	@ApiModelProperty("理论课时数量")
	private Integer theoryCount;

	@ApiModelProperty("课程实验课时数量")
	private Integer exerciseCount;

	@ApiModelProperty("建议学习时长")
	private Integer suggestDuration;

	@ApiModelProperty("是否收藏")
	private Boolean isCollection;

	@ApiModelProperty("课程权重")
	private Integer weight = DEFAULT_WEIGHT;

	@ApiModelProperty("是否具有访问权限")
	private Boolean interview;

	private Integer role;
}
