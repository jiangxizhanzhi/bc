package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB装置请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbDeviceReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "红蓝房间id")
	@NotEmpty
	private String roomId;

	/**
	 * 装置id
	 */
	@ApiModelProperty(value = "设备id")
	@NotEmpty
	private String deviceId;
}
