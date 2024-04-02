package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class LoadSetReq implements Serializable {

	@ApiModelProperty(value = "载荷id", required = true)
	private String loadId;

	//可能后续载荷会有参数设置
}
