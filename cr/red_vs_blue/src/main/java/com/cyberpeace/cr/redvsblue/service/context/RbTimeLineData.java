package com.cyberpeace.cr.redvsblue.service.context;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 红蓝时间线数据
 */
@Data
public class RbTimeLineData {

	@ApiModelProperty(value = "时间线事件")
	private List<RbTimeLineEvent> data;

	@ApiModelProperty(value = "当前时间")
	private Date currentTime;

	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	private Date endTime;
}
