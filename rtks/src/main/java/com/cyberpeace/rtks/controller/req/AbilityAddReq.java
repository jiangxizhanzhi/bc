package com.cyberpeace.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class AbilityAddReq implements Serializable {

	@ApiModelProperty("能力id")
	private String resourceId;

	@NotBlank
	@ApiModelProperty("能力名称")
	private String content;
}
