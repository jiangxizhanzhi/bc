package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenGuideHistoryReq extends ScreenGuideHistoryTimeLineReq {

	@ApiModelProperty("开始时间")
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty("速率")
	private long rate;

}
