package com.cyberpeace.common.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdAndBool {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "布尔值")
	private Boolean bool;
}
