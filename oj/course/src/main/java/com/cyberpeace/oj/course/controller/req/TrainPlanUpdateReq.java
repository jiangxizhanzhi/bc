package com.cyberpeace.oj.course.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanUpdateReq implements Serializable {

	@ApiModelProperty("资源Id")
	private String resourceId;

	@ApiModelProperty("资源名称")
	private String name;
}
