package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 屏幕导调历史请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ScreenGuideHistoryReq extends ScreenGuideHistoryTimeLineReq {

	/**
	 * 开始时间
	 */
	@ApiModelProperty("开始时间")
	private String startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty("结束时间")
	private String endTime;

	/**
	 * 率
	 */
	@ApiModelProperty("速率")
	private long rate;

}
