package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 海康摄像头请求体
 *
 * @Author xuxz
 * @Date 24/2/2023 上午9:26
 */

@Data
@ApiModel("海康摄像头请求体")
public class HikVisionReq implements Serializable {

	/**
	 * 设备IP
	 */
	@ApiModelProperty("设备IP")
	private String deviceIp;

	/**
	 * 设备端口
	 */
	@ApiModelProperty("设备端口")
	private String devicePort;

	/**
	 * 设备用户名
	 */
	@ApiModelProperty("设备用户名")
	private String username;

	/**
	 * 设备密码
	 */
	@ApiModelProperty("设备密码")
	private String password;

	/**
	 * 录像起始时间
	 */
	@ApiModelProperty("录像起始时间")
	private String startTime;

	/**
	 * 录像结束时间
	 */
	@ApiModelProperty("录像结束时间")
	private String endTime;
}
