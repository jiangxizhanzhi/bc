package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 写入向上请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class WriteUpReq {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 任务资源id
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 写入向上显示
	 */
	@ApiModelProperty(value = "是否显示题解", required = true)
	private Boolean writeUpShow;

}
