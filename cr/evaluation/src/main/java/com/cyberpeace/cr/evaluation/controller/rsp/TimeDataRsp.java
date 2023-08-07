package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TimeDataRsp {

	@ApiModelProperty(value = "状态")
	private Integer status;

	@ApiModelProperty(value = "时间戳")
	private Integer timeStamp;

	@ApiModelProperty(value = "开始时间")
	private Integer startTime;

	@ApiModelProperty(value = "运行时间")
	private Integer running;
}
