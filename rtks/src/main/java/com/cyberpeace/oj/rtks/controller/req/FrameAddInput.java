package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameAddInput implements Serializable {

	@ApiModelProperty(value = "框架名称")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "框架描述")
	private String description;

}
