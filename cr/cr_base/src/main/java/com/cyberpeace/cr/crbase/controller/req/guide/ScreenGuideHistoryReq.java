package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScreenGuideHistoryReq extends ScreenGuideHistoryTimeLineReq {

	@ApiModelProperty(value = "开始时间",required = true)
	private String startTime;

	@ApiModelProperty("结束时间")
	private String endTime;

	@ApiModelProperty(value = "速率", required = true)
	private long rate;

	@ApiModelProperty(value = "是否触发动效", required = true)
	private Boolean pushMessage;

}
