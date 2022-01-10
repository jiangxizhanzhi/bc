package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbDeviceReq implements Serializable {

	@ApiModelProperty
	@NotEmpty
	private String roomId;

	@ApiModelProperty
	@NotEmpty
	private String deviceId;
}
