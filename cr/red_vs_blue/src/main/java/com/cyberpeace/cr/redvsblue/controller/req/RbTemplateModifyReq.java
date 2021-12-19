package com.cyberpeace.cr.redvsblue.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateModifyReq extends RbTemplateAddReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;
}
