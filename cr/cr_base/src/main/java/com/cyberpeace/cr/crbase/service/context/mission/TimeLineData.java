package com.cyberpeace.cr.crbase.service.context.mission;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 时间轴数据
 * <p>
 * <p>
 * 兼容不同训练类型的前端事件列表字段
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/31
 * @since 1.0.0
 */
@Data
public class TimeLineData {

	/**
	 * 事件
	 *
	 * @see List<TimeLineEvent>
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> events;

	/**
	 * 数据
	 *
	 * @see List<TimeLineEvent>
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> data;

	/**
	 * 用户时间线配置
	 *
	 * @see List<TimeLineEvent>
	 */
	@ApiModelProperty(value = "时间轴事件")
	private List<TimeLineEvent> userTimeLineConfig;

	/**
	 * 当前时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "当前时间")
	private Date currentTime;

	/**
	 * 开始时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "开始时间")
	private Date startTime;

	/**
	 * 结束时间
	 *
	 * @see Date
	 */
	@ApiModelProperty(value = "结束时间")
	private Date endTime;
}
