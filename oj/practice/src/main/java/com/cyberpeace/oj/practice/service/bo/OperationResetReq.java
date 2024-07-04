package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class OperationResetReq {

	@ApiModelProperty(value = "实操题id")
	private String resourceId;
}
