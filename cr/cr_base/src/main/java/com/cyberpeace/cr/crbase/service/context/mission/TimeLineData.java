package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 时间轴数据
 * 兼容不同训练类型的前端事件列表字段
 *
 * @author SN
 * @date 2023/05/31
 */
@Getter
@Setter
public class TimeLineData {

	/**
	 * 事件
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> events;

	/**
	 * 数据
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> data;

	/**
	 * 用户时间线配置
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> userTimeLineConfig;

	/**
	 * 当前时间
	 */
	@ApiModelProperty(value = "当前时间")
	private Date currentTime;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
}
