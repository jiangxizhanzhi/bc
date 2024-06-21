package com.cyberpeace.oj.practice.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeSetResetReq {

	@ApiModelProperty(value = "习题集编号")
	private String resourceId;

	@ApiModelProperty(value = "习题集类型")
	private Integer setType;
}
