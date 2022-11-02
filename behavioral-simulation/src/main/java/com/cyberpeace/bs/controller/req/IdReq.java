package com.cyberpeace.bs.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IdReq implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;
}
