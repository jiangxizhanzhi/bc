package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝对抗装置请求体
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RbDeviceReq implements Serializable {

	/**
	 * 红蓝房间id
	 */
	@ApiModelProperty(value = "红蓝房间id")
	@NotEmpty
	private String roomId;

	/**
	 * 设备id
	 */
	@ApiModelProperty(value = "设备id")
	@NotEmpty
	private String deviceId;
}
