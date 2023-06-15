package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TrainManageTreeBo {

	@ApiModelProperty(value = "框架/父级id")
	private String parent;

	@ApiModelProperty(value = "框架/层级id")
	private String resourceId;

	@ApiModelProperty(value = "框架/层级名称")
	private String name;

	@ApiModelProperty(value = "子层级列表，不存在则为null")
	private List<TrainManageTreeBo> childrenList;

	@ApiModelProperty(value = "课程列表，不存在则为null")
	private List<TrainCourseBo> courseList;
}
