package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbDeviceReq implements Serializable {

	@ApiModelProperty(value = "红蓝房间id")
	@NotEmpty
	private String roomId;

	@ApiModelProperty(value = "设备id")
	@NotEmpty
	private String deviceId;
}
