package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WriteUpReq {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	@ApiModelProperty(value = "是否显示题解", required = true)
	private Boolean writeUpShow;

}
