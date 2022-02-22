package com.cyberpeace.cr.crbase.controller.req;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubmitMissionsReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private String missionHash;

	@ApiModelProperty(required = true)
	@NotEmpty
	private String answer;
}
