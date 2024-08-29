package com.cyberpeace.oj.course.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TrainPlanUpdateReq extends TrainPlanAddReq {

	@ApiModelProperty("资源Id")
	private String resourceId;
}
