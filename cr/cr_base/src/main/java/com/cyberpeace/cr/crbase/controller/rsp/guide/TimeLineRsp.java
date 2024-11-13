package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 时间线响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
public class TimeLineRsp implements Serializable {

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
	 * 活动时间列表
	 */
	@ApiModelProperty("事件节点时间")
	private List<String> eventTimeList;
}
