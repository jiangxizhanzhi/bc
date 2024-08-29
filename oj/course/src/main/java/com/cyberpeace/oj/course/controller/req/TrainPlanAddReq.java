package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanAddReq implements Serializable {

	@ApiModelProperty("资源名称")
	private String name;

	@ApiModelProperty("类型")
	private Integer type;
}
