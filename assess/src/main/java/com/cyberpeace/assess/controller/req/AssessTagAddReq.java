package com.cyberpeace.assess.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AssessTagAddReq implements Serializable {

	@ApiModelProperty(value = "名称", required = true)
	private String content;

}