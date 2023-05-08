package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 题解请求
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class WriteUpReq {

	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	@ApiModelProperty(value = "是否显示题解", required = true)
	private Boolean writeUpShow;

}
