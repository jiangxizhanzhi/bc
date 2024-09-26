package com.cyberpeace.oj.course.service.bo.classroom;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PlanProgressBo {

	@ApiModelProperty(value = "")
	private String studentId;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "学号")
	private String username;

	@ApiModelProperty(value = "组织id")
	private String orgId;

	@ApiModelProperty(value = "所在小组")
	private String orgName;

	@ApiModelProperty(value = "课程进度")
	private Integer progress;

	@ApiModelProperty(value = "花费时间")
	private Integer timeCost;
}
