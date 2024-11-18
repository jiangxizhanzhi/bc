package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RB装置请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbDeviceReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "红蓝房间id")
	@NotEmpty
	private String roomId;

	/**
	 * 装置id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "设备id")
	@NotEmpty
	private String deviceId;
}
