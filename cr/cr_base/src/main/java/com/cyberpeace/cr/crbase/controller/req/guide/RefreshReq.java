package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RefreshReq extends ScreenGuideHistoryReq {

	@ApiModelProperty("开始时间")
	private String eventName;

}
