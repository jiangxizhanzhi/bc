package com.cyberpeace.assess.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AssessTagAddReq {

	@ApiModelProperty(value = "名称", required = true)
	private String content;

}