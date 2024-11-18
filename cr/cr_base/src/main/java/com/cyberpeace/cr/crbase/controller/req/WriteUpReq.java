package com.cyberpeace.cr.crbase.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 写入向上请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class WriteUpReq {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "房间id", required = true)
	private String roomId;

	/**
	 * 任务资源id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "任务id", required = true)
	private String missionResourceId;

	/**
	 * 写入向上显示
	 *
	 * @see Boolean
	 */
	@ApiModelProperty(value = "是否显示题解", required = true)
	private Boolean writeUpShow;

}
