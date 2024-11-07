package com.cyberpeace.cr.redvsblue.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间数据响应
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class TimeDataRsp {

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	private Integer status;

	/**
	 * 时间戳
	 */
	@ApiModelProperty(value = "时间戳")
	private Integer timeStamp;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Integer startTime;

	/**
	 * 运行
	 */
	@ApiModelProperty(value = "运行时间")
	private Integer running;
}
