package com.cyberpeace.gateway.controller.res;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BooleanRes implements Serializable {

	@ApiModelProperty(value = "响应结果")
	private Boolean result;
}
